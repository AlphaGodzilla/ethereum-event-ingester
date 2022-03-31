package io.github.alphagozilla.ethereum.event.ingester.manage.domain.event;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 14:22
 */
public interface SyncableEventRepository {
    boolean save(SyncableEvent event);

    boolean removeById(long id);

    boolean removeByContract(Address contract);

    SyncableEvent findOfId(long id);
}
