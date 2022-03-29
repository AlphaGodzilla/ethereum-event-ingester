package io.github.alphagozilla.ethereum.event.ingester.ingester.infra.persistent;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.progress.ContractSyncProgress;
import io.github.alphagozilla.ethereum.event.ingester.ingester.progress.ContractSyncProgressRepository;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 15:06
 */
@Component
public class ContractSyncProgressRepositoryImpl
        extends ServiceImpl<ContractSyncProgressDOMapper, ContractSyncProgressDO>
        implements ContractSyncProgressRepository {

    @Override
    public boolean saveOrUpdate(ContractSyncProgress progress) {
        ContractSyncProgressDO contractSyncProgressDO = ContractSyncProgressConverter.INSTANT.toDataObject(progress);
        return super.saveOrUpdate(contractSyncProgressDO);
    }

    @Override
    public ContractSyncProgress find(Address contract) {
        ContractSyncProgressDO progressDO = super.getById(contract.getValue());
        return ContractSyncProgressConverter.INSTANT.toDomain(progressDO);
    }
}
