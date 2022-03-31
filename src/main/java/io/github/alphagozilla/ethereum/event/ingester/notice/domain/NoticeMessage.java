package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

import lombok.Builder;
import lombok.Value;

/**
 * @author AlphaGodzilla
 * @date 2022/3/30 17:55
 */
@Value
@Builder
public class NoticeMessage {
    ChannelType channelType;
    String channelValue;
    NoticePayload payload;
}
