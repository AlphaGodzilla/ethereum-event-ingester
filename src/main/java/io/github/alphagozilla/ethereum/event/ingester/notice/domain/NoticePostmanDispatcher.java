package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventLogConsumer;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractRawEventLog;
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

    public Optional<NoticePostman> dispatchPostman(NoticeChannelType channelType) {
        for (final NoticePostman noticePostman : noticePostmen) {
            NoticeChannelType noticeChannelType = noticePostman.channelType();
            if (noticeChannelType.equals(channelType)) {
                return Optional.of(noticePostman);
            }
        }
        return Optional.empty();
    }
}
