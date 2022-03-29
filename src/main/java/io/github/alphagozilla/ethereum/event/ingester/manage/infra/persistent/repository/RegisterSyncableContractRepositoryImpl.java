package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.RegisterSyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.RegisterSyncableContractRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterSyncableContractConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.RegisterSyncableContractDO;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.RegisterSyncableContractDOMapper;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:36
 */
@Component
public class RegisterSyncableContractRepositoryImpl extends ServiceImpl<RegisterSyncableContractDOMapper, RegisterSyncableContractDO>
        implements RegisterSyncableContractRepository
{
    @Override
    public void deleteAndSave(RegisterSyncableContract registerSyncableContract) {
        RegisterSyncableContractDO registerSyncableContractDO = RegisterSyncableContractConverter.INSTANCE.toDataObject(registerSyncableContract);
        // 删除
        super.removeById(registerSyncableContractDO.getAddress().getValue());
        // 保存
        super.save(registerSyncableContractDO);
    }

    @Override
    public RegisterSyncableContract findOfId(Address address) {
        RegisterSyncableContractDO registerSyncableContractDO = super.getById(address.getValue());
        return RegisterSyncableContractConverter.INSTANCE.toDomain(registerSyncableContractDO);
    }
}
