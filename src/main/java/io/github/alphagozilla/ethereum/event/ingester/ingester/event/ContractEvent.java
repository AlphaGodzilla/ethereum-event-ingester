package io.github.alphagozilla.ethereum.event.ingester.ingester.event;

import lombok.Builder;
import lombok.Value;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:09
 */
@Value
@Builder
public class ContractEvent {
    /**
     * 事件名
     */
    String name;

    /**
     * 事件topic0签名
     */
    String topics0;

    /**
     * 事件的abi描述
     */
    ContractEventAbi abi;
}
