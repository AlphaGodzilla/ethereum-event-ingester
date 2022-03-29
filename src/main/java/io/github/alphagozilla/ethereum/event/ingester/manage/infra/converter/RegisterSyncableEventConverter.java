package io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter;

import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.RegisterSyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.RegisterSyncableEventDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 11:08
 */
@Mapper
public interface RegisterSyncableEventConverter {
    RegisterSyncableEventConverter INSTANCE = Mappers.getMapper(RegisterSyncableEventConverter.class);

    List<RegisterSyncableEventDO> toDataObjects(List<RegisterSyncableEvent> registerSyncableEvent);

    List<RegisterSyncableEvent> toDomains(List<RegisterSyncableEventDO> registerSyncableEventDOS);
}
