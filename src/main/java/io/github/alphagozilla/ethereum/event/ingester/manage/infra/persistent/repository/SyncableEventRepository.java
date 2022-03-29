package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.RegisterSyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterSyncableEventConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.RegisterSyncableEventDO;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.RegisterSyncableEventDOMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 11:06
 */
@Component
public class SyncableEventRepository extends ServiceImpl<RegisterSyncableEventDOMapper, RegisterSyncableEventDO> {
    void removeByContract(Address contractAddress) {
        LambdaQueryWrapper<RegisterSyncableEventDO> queryWrapper = Wrappers.lambdaQuery(RegisterSyncableEventDO.class)
                .eq(RegisterSyncableEventDO::getContract, contractAddress.getValue());
        super.remove(queryWrapper);
    }

    List<RegisterSyncableEvent> listByContract(Address contract) {
        LambdaQueryWrapper<RegisterSyncableEventDO> queryWrapper = Wrappers.lambdaQuery(RegisterSyncableEventDO.class)
                .eq(RegisterSyncableEventDO::getContract, contract.getValue());
        List<RegisterSyncableEventDO> registerSyncableEventDOS = super.list(queryWrapper);
        return RegisterSyncableEventConverter.INSTANCE.toDomains(registerSyncableEventDOS);
    }
}
