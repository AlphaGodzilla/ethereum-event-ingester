package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.RegisterNoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.RegisterNoticeChannelRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.RegisterNoticeChannelDOConverter;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.RegisterNoticeChannelDO;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper.RegisterNoticeChannelDOMapper;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostmanDispatcher;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 13:34
 */
@Component
public class RegisterNoticeChannelRepositoryImpl
        extends ServiceImpl<RegisterNoticeChannelDOMapper, RegisterNoticeChannelDO> implements RegisterNoticeChannelRepository {
    private final NoticePostmanDispatcher noticePostmanDispatcher;

    public RegisterNoticeChannelRepositoryImpl(NoticePostmanDispatcher noticePostmanDispatcher) {
        this.noticePostmanDispatcher = noticePostmanDispatcher;
    }

    @Override
    public boolean saveOrUpdate(RegisterNoticeChannel registerNoticeChannel) {
        RegisterNoticeChannelDO registerNoticeChannelDO = RegisterNoticeChannelDOConverter.INSTANCE.toDataObject(registerNoticeChannel);
        return saveOrUpdate(registerNoticeChannelDO);
    }

    @Override
    public RegisterNoticeChannel findOfId(Address address) {
        RegisterNoticeChannelDO registerNoticeChannelDO = super.getById(address.getValue());
        return RegisterNoticeChannelDOConverter.INSTANCE.toDomain(registerNoticeChannelDO, noticePostmanDispatcher);
    }
}
