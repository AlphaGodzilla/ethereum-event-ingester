package io.github.alphagozilla.ethereum.event.ingester.ingester.infra.persistent;

import io.github.alphagozilla.ethereum.event.ingester.ingester.progress.ContractSyncProgress;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 15:03
 */
@Mapper
public interface ContractSyncProgressConverter {
    ContractSyncProgressConverter INSTANT = Mappers.getMapper(ContractSyncProgressConverter.class);

    ContractSyncProgressDO toDataObject(ContractSyncProgress progress);

    ContractSyncProgress toDomain(ContractSyncProgressDO contractSyncProgressDO);
}
