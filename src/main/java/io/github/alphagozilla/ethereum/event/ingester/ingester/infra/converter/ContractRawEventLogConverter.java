package io.github.alphagozilla.ethereum.event.ingester.ingester.infra.converter;

import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractRawEventLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.web3j.protocol.core.methods.response.Log;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 18:23
 */
@Mapper
public interface ContractRawEventLogConverter {
    ContractRawEventLogConverter INSTANT = Mappers.getMapper(ContractRawEventLogConverter.class);

    @Mapping(target = "blockNumber",
            expression = "java(web3jLog.getBlockNumber())"
    )
    @Mapping(target = "address",
            expression = "java(new io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address(web3jLog.getAddress()))"
    )
    ContractRawEventLog toContractRawEventLog(Log web3jLog);

    @Mapping(target = "blockNumber",
            expression = "java(contractRawEventLog.getBlockNumber().toString())"
    )
    @Mapping(target = "address",
            expression = "java(contractRawEventLog.getAddress().getValue())"
    )
    Log toWeb3jLog(ContractRawEventLog contractRawEventLog);

    List<ContractRawEventLog> toContractRawEventLogs(List<Log> web3jEthLogs);
}
