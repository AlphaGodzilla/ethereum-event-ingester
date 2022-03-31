package io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 13:39
 */
public interface SyncableContractRepository {
    boolean saveOrUpdate(SyncableContract syncableContract);

    SyncableContract findOfId(Address id);
}
