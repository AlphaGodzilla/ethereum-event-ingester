package io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter;

import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.RegisterNoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.RegisterNoticeChannelDO;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostmanDispatcher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 13:35
 */
@Mapper
public interface RegisterNoticeChannelDOConverter {
    RegisterNoticeChannelDOConverter INSTANCE = Mappers.getMapper(RegisterNoticeChannelDOConverter.class);

    RegisterNoticeChannelDO toDataObject(RegisterNoticeChannel registerNoticeChannel);

    @Mapping(source = "noticePostmanDispatcher", target = "noticePostmanDispatcher")
    RegisterNoticeChannel toDomain(RegisterNoticeChannelDO registerNoticeChannelDO, NoticePostmanDispatcher noticePostmanDispatcher);
}
