package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 14:17
 */
public interface NoticePostman {
    /**
     * 投递员的投递渠道类型
     * @return 渠道类型
     */
    NoticeChannelType channelType();

    /**
     * 发送通知
     * @param envelope 消息信封
     * @param payload 消息体
     */
    void postNotice(NoticeEnvelope envelope, NoticePayload payload);
}
