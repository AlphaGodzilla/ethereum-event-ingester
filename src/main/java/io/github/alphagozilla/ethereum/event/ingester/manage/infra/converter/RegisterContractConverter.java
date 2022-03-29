package io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter;

import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.RegisterNoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.RegisterSyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.RegisterSyncableEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 10:22
 */
@Mapper
public interface RegisterContractConverter {
    RegisterContractConverter INSTANCE = Mappers.getMapper(RegisterContractConverter.class);

    @Mapping(source = "registerSyncableContract.address", target = "address")
    @Mapping(source = "registerSyncableContract.name", target = "name")
    @Mapping(source = "registerSyncableContract.initBlock", target = "initBlock")
    @Mapping(source = "registerSyncableContract.enable", target = "enable")
    @Mapping(source = "registerSyncableContract.lastRegisterAt", target = "lastRegisterAt")
    @Mapping(source = "events", target = "events")
    @Mapping(source = "registerNoticeChannel", target = "registerNoticeChannel")
    RegisterContract toDomain(RegisterSyncableContract registerSyncableContract, List<RegisterSyncableEvent> events, RegisterNoticeChannel registerNoticeChannel);
}
