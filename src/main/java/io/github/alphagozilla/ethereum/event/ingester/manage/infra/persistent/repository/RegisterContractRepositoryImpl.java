package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.repository;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContractRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.NoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.NoticeChannelRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.SyncableContractRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.SyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterContractConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.SyncableEventConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.SyncableEventDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 10:20
 */
@Component
public class RegisterContractRepositoryImpl implements RegisterContractRepository {

    private final SyncableEventRepository syncableEventRepository;
    private final SyncableContractRepository syncableContractRepository;
    private final NoticeChannelRepository noticeChannelRepository;

    public RegisterContractRepositoryImpl(SyncableEventRepository syncableEventRepository,
                                          SyncableContractRepository syncableContractRepository,
                                          NoticeChannelRepository noticeChannelRepository) {
        this.syncableEventRepository = syncableEventRepository;
        this.syncableContractRepository = syncableContractRepository;
        this.noticeChannelRepository = noticeChannelRepository;
    }

    @Override
    public void coverSave(RegisterContract contract) {
        // 注册事件
        registerEvents(contract);
        // 注册合约
        registerContract(contract);
        // 注册通知渠道
        registerNoticeChannel(contract);
    }

    private void registerEvents(RegisterContract contract) {
        // 删除所有注册事件
        syncableEventRepository.removeByContract(contract.getAddress());
        // 重新注册
        List<SyncableEvent> events = contract.getEvents();
        List<SyncableEventDO> syncableEvents = SyncableEventConverter.INSTANCE.toDataObjects(events);
        syncableEventRepository.saveBatch(syncableEvents);
    }

    private void registerContract(RegisterContract contract) {
        SyncableContract syncableContract = contract.toSyncableContract();
        syncableContractRepository.deleteAndSave(syncableContract);
    }

    private void registerNoticeChannel(RegisterContract contract) {
        noticeChannelRepository.saveOrUpdate(contract.getNoticeChannel());
    }

    @Override
    public RegisterContract findOfId(Address contractAddress) {
        SyncableContract syncableContract = syncableContractRepository.findOfId(contractAddress);
        List<SyncableEvent> syncableEvents = syncableEventRepository.listByContract(contractAddress);
        NoticeChannel noticeChannel = noticeChannelRepository.findOfId(contractAddress);
        return RegisterContractConverter.INSTANCE.toDomain(syncableContract, syncableEvents, noticeChannel);
    }
}
