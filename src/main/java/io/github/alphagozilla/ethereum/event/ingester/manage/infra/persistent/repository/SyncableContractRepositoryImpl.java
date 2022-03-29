package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.SyncableContractRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.SyncableContractConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.SyncableContractDO;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.SyncableContractDOMapper;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:36
 */
@Component
public class SyncableContractRepositoryImpl extends ServiceImpl<SyncableContractDOMapper, SyncableContractDO>
        implements SyncableContractRepository
{
    @Override
    public void deleteAndSave(SyncableContract syncableContract) {
        SyncableContractDO syncableContractDO = SyncableContractConverter.INSTANCE.toDataObject(syncableContract);
        // 删除
        super.removeById(syncableContractDO.getAddress().getValue());
        // 保存
        super.save(syncableContractDO);
    }

    @Override
    public SyncableContract findOfId(Address address) {
        SyncableContractDO syncableContractDO = super.getById(address.getValue());
        return SyncableContractConverter.INSTANCE.toDomain(syncableContractDO);
    }
}
