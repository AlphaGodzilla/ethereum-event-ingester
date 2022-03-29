package io.github.alphagozilla.ethereum.event.ingester.ingester.event;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:40
 */
public interface ContractEventDecoder {
    /**
     * 将合约的原始事件日志解析为json字符串
     * @param abi 事件abi描述
     * @param eventLog 原始日志
     * @return json字符串事件
     */
    String parseToJsonStr(ContractEventAbi abi, ContractRawEventLog eventLog);
}
