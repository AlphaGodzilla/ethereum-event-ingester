package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.NoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.NoticeChannelRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.NoticeChannelDOConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.NoticeChannelDO;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.NoticeChannelDOMapper;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostmanDispatcher;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 13:34
 */
@Component
public class NoticeChannelRepositoryImpl
        extends ServiceImpl<NoticeChannelDOMapper, NoticeChannelDO> implements NoticeChannelRepository {
    private final NoticePostmanDispatcher noticePostmanDispatcher;

    public NoticeChannelRepositoryImpl(NoticePostmanDispatcher noticePostmanDispatcher) {
        this.noticePostmanDispatcher = noticePostmanDispatcher;
    }

    @Override
    public boolean saveOrUpdate(NoticeChannel noticeChannel) {
        NoticeChannelDO noticeChannelDO = NoticeChannelDOConverter.INSTANCE.toDataObject(noticeChannel);
        return saveOrUpdate(noticeChannelDO);
    }

    @Override
    public NoticeChannel findOfId(Address address) {
        NoticeChannelDO noticeChannelDO = super.getById(address.getValue());
        return NoticeChannelDOConverter.INSTANCE.toDomain(noticeChannelDO, noticePostmanDispatcher);
    }
}
