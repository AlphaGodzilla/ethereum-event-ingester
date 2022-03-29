package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.NoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.SyncContractQueryRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.SyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.NoticeChannelDOConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterContractConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.SyncableContractConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.SyncableEventConverter;
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
public class SyncContractQueryRepositoryImpl implements SyncContractQueryRepository {
    private final SyncableContractDOMapper syncableContractDOMapper;
    private final SyncableEventDOMapper syncableEventDOMapper;
    private final NoticeChannelDOMapper noticeChannelDOMapper;
    private final NoticePostmanDispatcher noticePostmanDispatcher;

    public SyncContractQueryRepositoryImpl(SyncableContractDOMapper syncableContractDOMapper,
                                           SyncableEventDOMapper syncableEventDOMapper,
                                           NoticeChannelDOMapper noticeChannelDOMapper,
                                           NoticePostmanDispatcher noticePostmanDispatcher) {
        this.syncableContractDOMapper = syncableContractDOMapper;
        this.syncableEventDOMapper = syncableEventDOMapper;
        this.noticeChannelDOMapper = noticeChannelDOMapper;
        this.noticePostmanDispatcher = noticePostmanDispatcher;
    }


    @Override
    public List<RegisterContract> list(Boolean enable, Address contractAddress) {
        List<SyncableContractDO> contractDOS = listEnabledContracts(enable, contractAddress);
        // 声明结果容器数组变量
        List<RegisterContract> finalContractList = new ArrayList<>(contractDOS.size());
        // 组装合约事件
        for (final SyncableContractDO contractDO : contractDOS) {
            RegisterContract contract = assembleSyncableContract(contractDO);
            finalContractList.add(contract);
        }
        return finalContractList;
    }

    private List<SyncableContractDO> listEnabledContracts(Boolean enable, Address contractAddress) {
        LambdaQueryWrapper<SyncableContractDO> contactQueryWrapper = Wrappers.lambdaQuery(SyncableContractDO.class)
                .eq(enable != null, SyncableContractDO::getEnable, true)
                .eq(contractAddress != null, SyncableContractDO::getAddress,
                        contractAddress != null ? contractAddress.getValue() : null
                );
        return syncableContractDOMapper.selectList(contactQueryWrapper);
    }

    private RegisterContract assembleSyncableContract(SyncableContractDO contractDO) {
        SyncableContract syncableContract = SyncableContractConverter.INSTANCE.toDomain(contractDO);
        List<SyncableEvent> syncableEvents = relationEvents(contractDO);
        NoticeChannel noticeChannel = relationNoticeChannel(contractDO);
        return RegisterContractConverter.INSTANCE.toDomain(syncableContract, syncableEvents, noticeChannel);
    }

    private List<SyncableEvent> relationEvents(SyncableContractDO contractDO) {
        LambdaQueryWrapper<SyncableEventDO> eventQueryWrapper = Wrappers.lambdaQuery(SyncableEventDO.class)
                .eq(SyncableEventDO::getContract, contractDO.getAddress().getValue());
        List<SyncableEventDO> eventDOS = syncableEventDOMapper.selectList(eventQueryWrapper);
        return SyncableEventConverter.INSTANCE.toDomains(eventDOS);
    }

    private NoticeChannel relationNoticeChannel(SyncableContractDO contractDO) {
        NoticeChannelDO noticeChannelDO = noticeChannelDOMapper.selectById(contractDO.getAddress().getValue());
        return NoticeChannelDOConverter.INSTANCE.toDomain(noticeChannelDO, noticePostmanDispatcher);
    }
}
