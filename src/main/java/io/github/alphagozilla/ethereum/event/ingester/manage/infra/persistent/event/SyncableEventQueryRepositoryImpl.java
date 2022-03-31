package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.event.SyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.event.SyncableEventQueryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 16:09
 */
@Component
public class SyncableEventQueryRepositoryImpl extends ServiceImpl<SyncableEventDOMapper, SyncableEventDO>
        implements SyncableEventQueryRepository {
    @Override
    public List<SyncableEvent> listOfContract(Address contract) {
        LambdaQueryWrapper<SyncableEventDO> queryWrapper = Wrappers.lambdaQuery(SyncableEventDO.class)
                .eq(SyncableEventDO::getContract, contract.getValue());
        List<SyncableEventDO> syncableEventDOS = super.list(queryWrapper);
        return syncableEventDOS.stream().map(SyncableEventDO::toDomain).collect(Collectors.toList());
    }
}
