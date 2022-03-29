package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.RegisterNoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.RegisterSyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.RegisterSyncContractQueryRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.RegisterSyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterContractConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterNoticeChannelDOConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterSyncableContractConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterSyncableEventConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.*;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostmanDispatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 14:02
 */
@Component
public class RegisterSyncContractQueryRepositoryImpl implements RegisterSyncContractQueryRepository {
    private final RegisterSyncableContractDOMapper registerSyncableContractDOMapper;
    private final RegisterSyncableEventDOMapper registerSyncableEventDOMapper;
    private final RegisterNoticeChannelDOMapper registerNoticeChannelDOMapper;
    private final NoticePostmanDispatcher noticePostmanDispatcher;

    public RegisterSyncContractQueryRepositoryImpl(RegisterSyncableContractDOMapper registerSyncableContractDOMapper,
                                                   RegisterSyncableEventDOMapper registerSyncableEventDOMapper,
                                                   RegisterNoticeChannelDOMapper registerNoticeChannelDOMapper,
                                                   NoticePostmanDispatcher noticePostmanDispatcher) {
        this.registerSyncableContractDOMapper = registerSyncableContractDOMapper;
        this.registerSyncableEventDOMapper = registerSyncableEventDOMapper;
        this.registerNoticeChannelDOMapper = registerNoticeChannelDOMapper;
        this.noticePostmanDispatcher = noticePostmanDispatcher;
    }


    @Override
    public List<RegisterContract> list(Boolean enable, Address contractAddress) {
        List<RegisterSyncableContractDO> contractDOS = listEnabledContracts(enable, contractAddress);
        // 声明结果容器数组变量
        List<RegisterContract> finalContractList = new ArrayList<>(contractDOS.size());
        // 组装合约事件
        for (final RegisterSyncableContractDO contractDO : contractDOS) {
            RegisterContract contract = assembleSyncableContract(contractDO);
            finalContractList.add(contract);
        }
        return finalContractList;
    }

    private List<RegisterSyncableContractDO> listEnabledContracts(Boolean enable, Address contractAddress) {
        LambdaQueryWrapper<RegisterSyncableContractDO> contactQueryWrapper = Wrappers.lambdaQuery(RegisterSyncableContractDO.class)
                .eq(enable != null, RegisterSyncableContractDO::getEnable, true)
                .eq(contractAddress != null, RegisterSyncableContractDO::getAddress,
                        contractAddress != null ? contractAddress.getValue() : null
                );
        return registerSyncableContractDOMapper.selectList(contactQueryWrapper);
    }

    private RegisterContract assembleSyncableContract(RegisterSyncableContractDO contractDO) {
        RegisterSyncableContract registerSyncableContract = RegisterSyncableContractConverter.INSTANCE.toDomain(contractDO);
        List<RegisterSyncableEvent> registerSyncableEvents = relationEvents(contractDO);
        RegisterNoticeChannel registerNoticeChannel = relationNoticeChannel(contractDO);
        return RegisterContractConverter.INSTANCE.toDomain(registerSyncableContract, registerSyncableEvents, registerNoticeChannel);
    }

    private List<RegisterSyncableEvent> relationEvents(RegisterSyncableContractDO contractDO) {
        LambdaQueryWrapper<RegisterSyncableEventDO> eventQueryWrapper = Wrappers.lambdaQuery(RegisterSyncableEventDO.class)
                .eq(RegisterSyncableEventDO::getContract, contractDO.getAddress().getValue());
        List<RegisterSyncableEventDO> eventDOS = registerSyncableEventDOMapper.selectList(eventQueryWrapper);
        return RegisterSyncableEventConverter.INSTANCE.toDomains(eventDOS);
    }

    private RegisterNoticeChannel relationNoticeChannel(RegisterSyncableContractDO contractDO) {
        RegisterNoticeChannelDO registerNoticeChannelDO = registerNoticeChannelDOMapper.selectById(contractDO.getAddress().getValue());
        return RegisterNoticeChannelDOConverter.INSTANCE.toDomain(registerNoticeChannelDO, noticePostmanDispatcher);
    }
}
