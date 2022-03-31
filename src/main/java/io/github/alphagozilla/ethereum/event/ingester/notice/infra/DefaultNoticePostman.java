package io.github.alphagozilla.ethereum.event.ingester.notice.infra;

import io.github.alphagozilla.ethereum.event.ingester.notice.domain.ChannelType;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeMessage;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostman;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/30 10:14
 */
@Component
public class DefaultNoticePostman implements NoticePostman {
    private final ConsoleLogNoticePostman consoleLogNoticePostman;

    public DefaultNoticePostman(ConsoleLogNoticePostman consoleLogNoticePostman) {
        this.consoleLogNoticePostman = consoleLogNoticePostman;
    }

    @Override
    public ChannelType channelType() {
        return ChannelType.DEFAULT;
    }

    @Override
    public void postNotice(NoticeMessage message) {
        consoleLogNoticePostman.postNotice(message);
    }
}
