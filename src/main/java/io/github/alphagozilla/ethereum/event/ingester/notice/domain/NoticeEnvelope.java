package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

import lombok.Builder;
import lombok.Value;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 14:19
 */
@Value
@Builder
public class NoticeEnvelope {
    NoticeChannelType noticeChannelType;

    String channel;
}
