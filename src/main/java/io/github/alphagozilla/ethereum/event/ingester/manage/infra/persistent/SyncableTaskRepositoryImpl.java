package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.aggregation.SyncableTask;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.aggregation.SyncableTaskRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.bind.ContractChannelBind;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.bind.ContractChannelBindRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract.SyncableContractRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.event.SyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.event.SyncableEventQueryRepository;
import io.github.alphagozilla.ethereum.event.ingester.notice.application.NoticeChannelAppService;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 16:01
 */
@Component
public class SyncableTaskRepositoryImpl implements SyncableTaskRepository {
    private final SyncableContractRepository syncableContractRepository;
    private final SyncableEventQueryRepository syncableEventQueryRepository;
    private final ContractChannelBindRepository contractChannelBindRepository;
    private final NoticeChannelAppService noticeChannelAppService;

    public SyncableTaskRepositoryImpl(SyncableContractRepository syncableContractRepository,
                                      SyncableEventQueryRepository syncableEventQueryRepository,
                                      ContractChannelBindRepository contractChannelBindRepository,
                                      NoticeChannelAppService noticeChannelAppService
    ) {
        this.syncableContractRepository = syncableContractRepository;
        this.syncableEventQueryRepository = syncableEventQueryRepository;
        this.contractChannelBindRepository = contractChannelBindRepository;
        this.noticeChannelAppService = noticeChannelAppService;
    }

    @Override
    public SyncableTask findOfId(Address id) {
        SyncableContract contract = syncableContractRepository.findOfId(id);
        List<SyncableEvent> syncableEvents = syncableEventQueryRepository.listOfContract(contract.getId());
        List<ContractChannelBind> contractChannelBinds = contractChannelBindRepository.listByContract(contract.getId());
        Set<Long> channelIdSet = contractChannelBinds.stream()
                .map(ContractChannelBind::getChannel)
                .collect(Collectors.toSet());
        List<NoticeChannel> channels = noticeChannelAppService.channels(channelIdSet);
        return SyncableTask.builder()
                .contract(contract)
                .events(syncableEvents)
                .channels(channels)
                .build();
    }
}
