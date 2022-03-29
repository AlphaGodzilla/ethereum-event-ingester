package io.github.alphagozilla.ethereum.event.ingester.ingester.contract;

import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEvent;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventLogConsumer;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;
import java.util.Set;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 17:28
 */
@Value
@Builder
public class FlowableEventContractConfig {
    String name;
    Set<Address> addresses;
    BigInteger initBlock;
    Set<ContractEvent> events;
    ContractEventLogConsumer eventLogConsumer;
}
