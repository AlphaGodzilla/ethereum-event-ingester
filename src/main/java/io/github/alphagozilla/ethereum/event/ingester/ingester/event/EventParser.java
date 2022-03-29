package io.github.alphagozilla.ethereum.event.ingester.ingester.event;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 18:01
 */
public interface EventParser {
    /**
     * 将原始的事件日志解析为json字符串
     * @param eventLog 原始事件日志
     * @param abi abi描述
     * @return json字符串
     */
    String toJson(ContractRawEventLog eventLog, ContractEventAbi abi);

    /**
     * 生成事件签名
     * @param abi abi描述
     * @return topic0签名
     */
    String encodeEventToTopic0(ContractEventAbi abi);
}
