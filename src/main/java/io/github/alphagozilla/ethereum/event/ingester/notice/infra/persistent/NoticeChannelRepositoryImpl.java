package io.github.alphagozilla.ethereum.event.ingester.notice.infra.persistent;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannelRepository;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostman;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostmanDispatcher;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 11:03
 */
@Component
public class NoticeChannelRepositoryImpl extends ServiceImpl<NoticeChannelDOMapper, NoticeChannelDO>
        implements NoticeChannelRepository {
    private final NoticePostmanDispatcher noticePostmanDispatcher;

    public NoticeChannelRepositoryImpl(NoticePostmanDispatcher noticePostmanDispatcher) {
        this.noticePostmanDispatcher = noticePostmanDispatcher;
    }

    @Override
    public NoticeChannel findOfId(long id) {
        NoticeChannelDO noticeChannelDO = super.getById(id);
        if (noticeChannelDO == null) {
            return null;
        }
        Optional<NoticePostman> noticePostmanOptional = noticePostmanDispatcher.dispatchPostman(noticeChannelDO.getType());
        return NoticeChannel.builder()
                .id(noticeChannelDO.getId())
                .name(noticeChannelDO.getName())
                .type(noticeChannelDO.getType())
                .value(noticeChannelDO.getValue())
                .noticePostman(noticePostmanOptional.orElseThrow())
                .build();
    }

    @Override
    public boolean saveOrUpdate(NoticeChannel noticeChannel) {
        return super.save(new NoticeChannelDO(noticeChannel));
    }

    @Override
    public boolean remove(long id) {
        return super.removeById(id);
    }
}
