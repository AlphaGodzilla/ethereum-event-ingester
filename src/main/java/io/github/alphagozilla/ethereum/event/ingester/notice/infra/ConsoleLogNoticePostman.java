package io.github.alphagozilla.ethereum.event.ingester.notice.infra;

import io.github.alphagozilla.ethereum.event.ingester.notice.domain.ChannelType;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeMessage;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostman;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/30 10:08
 */
@Component
@Slf4j
public class ConsoleLogNoticePostman implements NoticePostman {
    @Override
    public ChannelType channelType() {
        return ChannelType.CONSOLE;
    }

    @Override
    public void postNotice(NoticeMessage message) {
        log.info("ConsoleLogNoticePostman --> {}", message.getPayload());
    }
}
