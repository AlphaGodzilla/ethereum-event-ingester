package io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventAbi;
import lombok.Builder;
import lombok.Value;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 10:33
 */
@Builder
@Value
public class SyncableEvent {
    Long id;

    Address contract;

    String eventName;

    ContractEventAbi abi;
}
