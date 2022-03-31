package io.github.alphagozilla.ethereum.event.ingester.manage.domain.event;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 16:08
 */
public interface SyncableEventQueryRepository {
    List<SyncableEvent> listOfContract(Address contract);
}
