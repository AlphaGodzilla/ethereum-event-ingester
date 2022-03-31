package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:25
 */
public enum ChannelType {
    /**
     * 默认通知渠道
     */
    DEFAULT,
    /**
     * HTTP通知渠道
     */
    HTTP,
    /**
     * 控制台通知渠道
     */
    CONSOLE;
}
