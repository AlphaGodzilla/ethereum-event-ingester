package io.github.alphagozilla.ethereum.event.ingester.manage.domain.event;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventAbi;
import lombok.Builder;
import lombok.Value;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 14:17
 */
@Value
@Builder
public class SyncableEvent {
    Long id;

    Address contract;

    String name;

    ContractEventAbi abi;
}
