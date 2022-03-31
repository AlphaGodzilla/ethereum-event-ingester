package io.github.alphagozilla.ethereum.event.ingester.manage.application;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContract;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContractConfig;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContractFactory;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventFactory;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.aggregation.SyncableTask;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.aggregation.SyncableTaskEntry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 13:35
 */
@Component
public class SyncableContractAppService {
    private final SyncableTaskEntry syncableTaskEntry;
    private final FlowableEventContractFactory flowableEventContractFactory;
    private final ContractEventFactory contractEventFactory;

    public SyncableContractAppService(SyncableTaskEntry syncableTaskEntry,
                                      FlowableEventContractFactory flowableEventContractFactory,
                                      ContractEventFactory contractEventFactory
    ) {
        this.syncableTaskEntry = syncableTaskEntry;
        this.flowableEventContractFactory = flowableEventContractFactory;
        this.contractEventFactory = contractEventFactory;
    }

    /**
     * 列出所有可同步的已注册合约
     *
     * @return 合约列表
     */
    public List<FlowableEventContract> listAllEnableContracts() {
        List<SyncableTask> syncableTasks = syncableTaskEntry.list(true);
        return syncableTasks.stream()
                .map(i -> FlowableEventContractConfig.builder()
                        .name(i.getContract().getName())
                        .addresses(Set.of(i.getContract().getId()))
                        .initBlock(i.getContract().getInitBlock())
                        .events(i.getEvents().stream()
                                .map(j -> contractEventFactory.factoryNew(j.getName(), j.getAbi()))
                                .collect(Collectors.toSet())
                        )
                        .eventLogConsumer(i)
                        .build()
                )
                .map(flowableEventContractFactory::factoryNew)
                .collect(Collectors.toList());
    }
}
