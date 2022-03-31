package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.contract;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract.SyncableContractQueryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 16:17
 */
@Component
public class SyncableContractQueryRepositoryImpl extends ServiceImpl<SyncableContractDOMapper, SyncableContractDO>
        implements SyncableContractQueryRepository {
    @Override
    public List<SyncableContract> list(Boolean syncable) {
        LambdaQueryWrapper<SyncableContractDO> queryWrapper = Wrappers.lambdaQuery(SyncableContractDO.class)
                .eq(syncable != null, SyncableContractDO::getSyncable, syncable);
        List<SyncableContractDO> syncableContractDOS = super.list(queryWrapper);
        return syncableContractDOS.stream().map(SyncableContractDO::toDomain).collect(Collectors.toList());
    }
}
