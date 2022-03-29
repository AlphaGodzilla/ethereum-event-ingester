package io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter;

import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.SyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.SyncableEventDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 11:08
 */
@Mapper
public interface SyncableEventConverter {
    SyncableEventConverter INSTANCE = Mappers.getMapper(SyncableEventConverter.class);

    List<SyncableEventDO> toDataObjects(List<SyncableEvent> syncableEvent);

    List<SyncableEvent> toDomains(List<SyncableEventDO> syncableEventDOS);
}
