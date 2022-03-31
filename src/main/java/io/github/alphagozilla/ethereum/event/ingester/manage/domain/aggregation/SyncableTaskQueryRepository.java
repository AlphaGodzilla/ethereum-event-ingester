package io.github.alphagozilla.ethereum.event.ingester.manage.domain.aggregation;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 16:15
 */
public interface SyncableTaskQueryRepository {
    List<SyncableTask> list(Boolean synable);
}
