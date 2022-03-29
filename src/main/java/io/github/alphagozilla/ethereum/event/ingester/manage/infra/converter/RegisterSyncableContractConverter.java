package io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter;

import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.RegisterSyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.RegisterSyncableContractDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:38
 */
@Mapper
public interface RegisterSyncableContractConverter {
    RegisterSyncableContractConverter INSTANCE = Mappers.getMapper(RegisterSyncableContractConverter.class);

    RegisterSyncableContractDO toDataObject(RegisterSyncableContract registerSyncableContract);

    RegisterSyncableContract toDomain(RegisterSyncableContractDO registerSyncableContractDO);
}
