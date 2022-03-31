package io.github.alphagozilla.ethereum.event.ingester.notice.infra;

import io.github.alphagozilla.ethereum.event.ingester.notice.domain.ChannelType;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeMessage;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePostman;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 14:12
 */
@Component
public class HttpNoticePostman implements NoticePostman {
    @Override
    public ChannelType channelType() {
        return ChannelType.HTTP;
    }


    @Override
    public void postNotice(NoticeMessage message) {
        // TODO: 发送消息
    }
}
