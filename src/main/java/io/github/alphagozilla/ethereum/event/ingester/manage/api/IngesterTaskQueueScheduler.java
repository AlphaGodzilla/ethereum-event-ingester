package io.github.alphagozilla.ethereum.event.ingester.manage.api;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContract;
import io.github.alphagozilla.ethereum.event.ingester.ingester.task.ContractEventLogScrapeTaskFactory;
import io.github.alphagozilla.ethereum.event.ingester.ingester.task.TaskQueue;
import io.github.alphagozilla.ethereum.event.ingester.manage.application.SyncableContractAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 14:44
 */
@Slf4j
@Component
public class IngesterTaskQueueScheduler {
    private final SyncableContractAppService syncableContractAppService;
    private final TaskQueue taskQueue;
    private final ContractEventLogScrapeTaskFactory taskFactory;

    public IngesterTaskQueueScheduler(SyncableContractAppService syncableContractAppService,
                                      TaskQueue taskQueue,
                                      ContractEventLogScrapeTaskFactory taskFactory) {
        this.syncableContractAppService = syncableContractAppService;
        this.taskQueue = taskQueue;
        this.taskFactory = taskFactory;
    }

    @Scheduled(initialDelay = 30, fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void feedTask() {
        log.debug("触发同步任务");
        List<FlowableEventContract> flowableEventContracts = syncableContractAppService.listAllEnableContracts();
        log.info("需同步合约: [{}], 数量: {}", flowableEventContracts.stream()
                        .map(FlowableEventContract::name)
                        .collect(Collectors.toSet()),
                flowableEventContracts.size()
        );
        if (flowableEventContracts.isEmpty()) {
            return;
        }
        int count = taskQueue.offerUntilArriveLoadFactor(() -> taskFactory.factoryNew(flowableEventContracts));
        log.info("补充队列任务数: {}", count);
    }
}
