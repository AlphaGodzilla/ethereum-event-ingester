package io.github.alphagozilla.ethereum.event.ingester.manage.domain.aggregation;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 16:01
 */
public interface SyncableTaskRepository {
    SyncableTask findOfId(Address id);
}
