package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 14:17
 */
public interface NoticePostman {
    /**
     * 投递员的投递渠道类型
     *
     * @return 渠道类型
     */
    ChannelType channelType();

    /**
     * 发送通知
     *
     * @param message 需要发送的消息
     */
    void postNotice(NoticeMessage message);
}
