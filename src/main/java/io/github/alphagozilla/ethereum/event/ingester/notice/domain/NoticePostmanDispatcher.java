package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 14:36
 */
@Component
public class NoticePostmanDispatcher {
    private final List<NoticePostman> noticePostmen;

    public NoticePostmanDispatcher(List<NoticePostman> noticePostmen) {
        this.noticePostmen = noticePostmen;
    }

    public Optional<NoticePostman> dispatchPostman(ChannelType channelType) {
        if (channelType == null) {
            channelType = ChannelType.DEFAULT;
        }
        for (final NoticePostman noticePostman : noticePostmen) {
            ChannelType noticeChannelType = noticePostman.channelType();
            if (noticeChannelType.equals(channelType)) {
                return Optional.of(noticePostman);
            }
        }
        return Optional.empty();
    }
}
