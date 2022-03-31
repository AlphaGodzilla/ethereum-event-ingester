package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.contract;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract.SyncableContractRepository;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 13:45
 */
@Component
public class SyncableContractRepositoryImpl extends ServiceImpl<SyncableContractDOMapper, SyncableContractDO>
        implements SyncableContractRepository {

    @Override
    public boolean saveOrUpdate(SyncableContract syncableContract) {
        return super.saveOrUpdate(new SyncableContractDO(syncableContract));
    }

    @Override
    public SyncableContract findOfId(Address id) {
        SyncableContractDO syncableContractDO = super.getById(id.getValue());
        if (syncableContractDO == null) {
            return null;
        }
        return syncableContractDO.toDomain();
    }
}
