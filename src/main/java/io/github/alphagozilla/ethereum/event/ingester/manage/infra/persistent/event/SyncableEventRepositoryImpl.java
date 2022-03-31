package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.event;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.event.SyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.event.SyncableEventRepository;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 14:59
 */
@Component
public class SyncableEventRepositoryImpl extends ServiceImpl<SyncableEventDOMapper, SyncableEventDO>
        implements SyncableEventRepository {
    @Override
    public boolean save(SyncableEvent event) {
        return super.save(new SyncableEventDO(event));
    }

    @Override
    public boolean removeById(long id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByContract(Address contract) {
        LambdaQueryWrapper<SyncableEventDO> queryWrapper = Wrappers.lambdaQuery(SyncableEventDO.class)
                .eq(SyncableEventDO::getContract, contract.getValue());
        return super.remove(queryWrapper);
    }

    @Override
    public SyncableEvent findOfId(long id) {
        SyncableEventDO syncableEventDO = super.getById(id);
        if (syncableEventDO == null) {
            return null;
        }
        return syncableEventDO.toDomain();
    }
}
