package io.github.alphagozilla.ethereum.event.ingester.ingester.event;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:36
 */
public interface ContractEventTopic0Encoder {
    /**
     * 编码合约事件为topic0
     * @param eventAbi 事件abi
     * @return topic0
     */
    String encodeToTopic0(ContractEventAbi eventAbi);
}
