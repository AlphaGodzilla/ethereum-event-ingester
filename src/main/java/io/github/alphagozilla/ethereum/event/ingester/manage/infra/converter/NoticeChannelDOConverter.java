package io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter;

import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.NoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.NoticeChannelDO;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostmanDispatcher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 13:35
 */
@Mapper
public interface NoticeChannelDOConverter {
    NoticeChannelDOConverter INSTANCE = Mappers.getMapper(NoticeChannelDOConverter.class);

    NoticeChannelDO toDataObject(NoticeChannel noticeChannel);

    @Mapping(source = "noticePostmanDispatcher", target = "noticePostmanDispatcher")
    NoticeChannel toDomain(NoticeChannelDO noticeChannelDO, NoticePostmanDispatcher noticePostmanDispatcher);
}
