package io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter;

import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.NoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.SyncableEvent;
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

    @Mapping(source = "syncableContract.address", target = "address")
    @Mapping(source = "syncableContract.name", target = "name")
    @Mapping(source = "syncableContract.initBlock", target = "initBlock")
    @Mapping(source = "syncableContract.enable", target = "enable")
    @Mapping(source = "syncableContract.lastRegisterAt", target = "lastRegisterAt")
    @Mapping(source = "events", target = "events")
    @Mapping(source = "noticeChannel", target = "noticeChannel")
    RegisterContract toDomain(SyncableContract syncableContract, List<SyncableEvent> events, NoticeChannel noticeChannel);
}
