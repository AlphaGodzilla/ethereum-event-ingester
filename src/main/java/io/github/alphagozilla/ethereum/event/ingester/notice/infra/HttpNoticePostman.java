package io.github.alphagozilla.ethereum.event.ingester.notice.infra;

import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannelType;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeEnvelope;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePayload;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostman;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 14:12
 */
@Component
public class HttpNoticePostman implements NoticePostman {
    @Override
    public NoticeChannelType channelType() {
        return NoticeChannelType.HTTP;
    }

    @Override
    public void postNotice(NoticeEnvelope envelope, NoticePayload payload) {
        // TODO: 发送消息
    }
}
