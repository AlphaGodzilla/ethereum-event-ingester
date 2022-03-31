package io.github.alphagozilla.ethereum.event.ingester.manage.domain.aggregation;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 16:28
 */
@Component
public class SyncableTaskEntry {
    private final SyncableTaskQueryRepository syncableTaskQueryRepository;

    public SyncableTaskEntry(SyncableTaskQueryRepository syncableTaskQueryRepository) {
        this.syncableTaskQueryRepository = syncableTaskQueryRepository;
    }

    public List<SyncableTask> list(Boolean syncable) {
        return syncableTaskQueryRepository.list(syncable);
    }
}
