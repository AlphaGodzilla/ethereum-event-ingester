package io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter;

import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventAbi;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.typehandler.ContractEventAbiTypeHandler;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 14:28
 */
@Mapper
public interface ContractEventAbiConverter {
    ContractEventAbiConverter INSTANCE = Mappers.getMapper(ContractEventAbiConverter.class);

    ContractEventAbi toContractEventAbi(ContractEventAbiTypeHandler.ContractEventAbiSerializable serializableObject);
}
