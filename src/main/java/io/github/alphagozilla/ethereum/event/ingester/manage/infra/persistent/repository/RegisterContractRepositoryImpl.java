package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.repository;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContractRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.RegisterNoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.RegisterNoticeChannelRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.RegisterSyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.RegisterSyncableContractRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.RegisterSyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterContractConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterSyncableEventConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.RegisterSyncableEventDO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 10:20
 */
@Component
public class RegisterContractRepositoryImpl implements RegisterContractRepository {

    private final SyncableEventRepository syncableEventRepository;
    private final RegisterSyncableContractRepository registerSyncableContractRepository;
    private final RegisterNoticeChannelRepository registerNoticeChannelRepository;

    public RegisterContractRepositoryImpl(SyncableEventRepository syncableEventRepository,
                                          RegisterSyncableContractRepository registerSyncableContractRepository,
                                          RegisterNoticeChannelRepository registerNoticeChannelRepository) {
        this.syncableEventRepository = syncableEventRepository;
        this.registerSyncableContractRepository = registerSyncableContractRepository;
        this.registerNoticeChannelRepository = registerNoticeChannelRepository;
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
        List<RegisterSyncableEvent> events = contract.getEvents();
        List<RegisterSyncableEventDO> syncableEvents = RegisterSyncableEventConverter.INSTANCE.toDataObjects(events);
        syncableEventRepository.saveBatch(syncableEvents);
    }

    private void registerContract(RegisterContract contract) {
        RegisterSyncableContract registerSyncableContract = contract.toSyncableContract();
        registerSyncableContractRepository.deleteAndSave(registerSyncableContract);
    }

    private void registerNoticeChannel(RegisterContract contract) {
        registerNoticeChannelRepository.saveOrUpdate(contract.getRegisterNoticeChannel());
    }

    @Override
    public RegisterContract findOfId(Address contractAddress) {
        RegisterSyncableContract registerSyncableContract = registerSyncableContractRepository.findOfId(contractAddress);
        List<RegisterSyncableEvent> registerSyncableEvents = syncableEventRepository.listByContract(contractAddress);
        RegisterNoticeChannel registerNoticeChannel = registerNoticeChannelRepository.findOfId(contractAddress);
        return RegisterContractConverter.INSTANCE.toDomain(registerSyncableContract, registerSyncableEvents, registerNoticeChannel);
    }
}
