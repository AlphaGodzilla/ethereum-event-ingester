package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.SyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.SyncableEventConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.SyncableEventDO;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.SyncableEventDOMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 11:06
 */
@Component
public class SyncableEventRepository extends ServiceImpl<SyncableEventDOMapper, SyncableEventDO> {
    void removeByContract(Address contractAddress) {
        LambdaQueryWrapper<SyncableEventDO> queryWrapper = Wrappers.lambdaQuery(SyncableEventDO.class)
                .eq(SyncableEventDO::getContract, contractAddress.getValue());
        super.remove(queryWrapper);
    }

    List<SyncableEvent> listByContract(Address contract) {
        LambdaQueryWrapper<SyncableEventDO> queryWrapper = Wrappers.lambdaQuery(SyncableEventDO.class)
                .eq(SyncableEventDO::getContract, contract.getValue());
        List<SyncableEventDO> syncableEventDOS = super.list(queryWrapper);
        return SyncableEventConverter.INSTANCE.toDomains(syncableEventDOS);
    }
}
