package io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter;

import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.SyncableContractDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:38
 */
@Mapper
public interface SyncableContractConverter {
    SyncableContractConverter INSTANCE = Mappers.getMapper(SyncableContractConverter.class);

    SyncableContractDO toDataObject(SyncableContract syncableContract);

    SyncableContract toDomain(SyncableContractDO syncableContractDO);
}
