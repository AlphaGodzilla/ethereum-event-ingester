package io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 16:17
 */
public interface SyncableContractQueryRepository {
    List<SyncableContract> list(Boolean syncable);
}
