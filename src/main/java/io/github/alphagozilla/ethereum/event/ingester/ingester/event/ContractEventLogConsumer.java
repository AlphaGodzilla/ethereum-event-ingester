package io.github.alphagozilla.ethereum.event.ingester.ingester.event;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 17:54
 */
public interface ContractEventLogConsumer {
    /**
     * 处理爬取的事件
     *
     * @param rawLog    原始事件日志
     * @param event     事件模型
     * @param eventJson 事件日志按照模型解析后的json字符串
     */
    void consume(ContractRawEventLog rawLog, ContractEvent event, String eventJson);
}
