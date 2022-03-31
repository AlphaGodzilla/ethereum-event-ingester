package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.aggregation.SyncableTask;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.aggregation.SyncableTaskQueryRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.bind.ContractChannelBind;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.bind.ContractChannelBindRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract.SyncableContractQueryRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.event.SyncableEventQueryRepository;
import io.github.alphagozilla.ethereum.event.ingester.notice.application.NoticeChannelAppService;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 16:16
 */
@Component
public class SyncableTaskQueryRepositoryImpl implements SyncableTaskQueryRepository {
    private final SyncableContractQueryRepository syncableContractQueryRepository;
    private final SyncableEventQueryRepository syncableEventQueryRepository;
    private final ContractChannelBindRepository contractChannelBindRepository;
    private final NoticeChannelAppService noticeChannelAppService;

    public SyncableTaskQueryRepositoryImpl(SyncableContractQueryRepository syncableContractQueryRepository,
                                           SyncableEventQueryRepository syncableEventQueryRepository,
                                           ContractChannelBindRepository contractChannelBindRepository,
                                           NoticeChannelAppService noticeChannelAppService
    ) {
        this.syncableContractQueryRepository = syncableContractQueryRepository;
        this.syncableEventQueryRepository = syncableEventQueryRepository;
        this.contractChannelBindRepository = contractChannelBindRepository;
        this.noticeChannelAppService = noticeChannelAppService;
    }

    @Override
    public List<SyncableTask> list(Boolean syncable) {
        List<SyncableContract> syncableContracts = syncableContractQueryRepository.list(syncable);
        return syncableContracts.stream()
                .map(i -> SyncableTask.builder()
                        .contract(i)
                        .events(syncableEventQueryRepository.listOfContract(i.getId()))
                        .channels(loadChannels(i.getId()))
                        .build()
                ).collect(Collectors.toList());
    }

    private List<NoticeChannel> loadChannels(Address contract) {
        List<ContractChannelBind> contractChannelBinds = contractChannelBindRepository.listByContract(contract);
        Set<Long> channelIdSet = contractChannelBinds.stream()
                .map(ContractChannelBind::getChannel)
                .collect(Collectors.toSet());
        return noticeChannelAppService.channels(channelIdSet);
    }
}
