package io.github.alphagozilla.ethereum.event.ingester.notice.infra.persistent;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannelQueryRepository;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostmanDispatcher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 11:52
 */
@Component
public class NoticeChannelQueryRepositoryImpl extends ServiceImpl<NoticeChannelDOMapper, NoticeChannelDO>
        implements NoticeChannelQueryRepository {
    private final NoticePostmanDispatcher noticePostmanDispatcher;

    public NoticeChannelQueryRepositoryImpl(NoticePostmanDispatcher noticePostmanDispatcher) {
        this.noticePostmanDispatcher = noticePostmanDispatcher;
    }

    @Override
    public List<NoticeChannel> list(Set<Long> idSet) {
        LambdaQueryWrapper<NoticeChannelDO> queryWrapper = Wrappers.lambdaQuery(NoticeChannelDO.class)
                .in(NoticeChannelDO::getId, idSet);
        List<NoticeChannelDO> noticeChannelDOS = super.list(queryWrapper);
        return noticeChannelDOS.stream().map(i -> NoticeChannel.builder()
                .id(i.getId())
                .name(i.getName())
                .type(i.getType())
                .value(i.getValue())
                .noticePostman(noticePostmanDispatcher.dispatchPostman(i.getType()).orElseThrow())
                .build()).collect(Collectors.toList());
    }
}
