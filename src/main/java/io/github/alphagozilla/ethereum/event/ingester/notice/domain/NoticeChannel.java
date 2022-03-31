package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author AlphaGodzilla
 * @date 2022/3/30 17:59
 */
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class NoticeChannel {
    private final Long id;
    /**
     * 渠道名
     */
    private final String name;
    /**
     * 渠道类型
     */
    private ChannelType type;
    /**
     * 渠道值
     */
    private String value;
    /**
     * 发送通知的邮递员
     */
    private final NoticePostman noticePostman;

    public void notice(NoticePayload payload) {
        NoticeMessage message = NoticeMessage.builder()
                .channelType(getType())
                .channelValue(getValue())
                .payload(payload)
                .build();
        noticePostman.postNotice(message);
    }
}
