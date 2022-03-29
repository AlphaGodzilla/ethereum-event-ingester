package io.github.alphagozilla.ethereum.event.ingester.ingester.contract;

import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEvent;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractRawEventLog;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author AlphaGodzilla
 * @date 2022/1/19 10:44
 */
public interface FlowableEventContract {
    /**
     * 监听合约的名字（唯一标识）
     * @return 合约自定义名
     */
    String name();

    /**
     * 合约地址
     * @return 地址
     */
    Collection<Address> addresses();

    /**
     * 开始区块
     * @return 开始区块
     */
    BigInteger startBlock();

    /**
     * 需要监听的事件
     * @return 事件
     */
    Set<ContractEvent> needFlowableEvents();

    /**
     * 选择匹配的事件处理器并消费事件
     * @param allLogs 当前批次所有的事件
     * @param log 匹配的事件日志
     */
    void chooseHandleAndConsumeLog(List<ContractRawEventLog> allLogs, ContractRawEventLog log);

    /**
     * 消费返回的事件
     * @param allEventLogs 当前批次所有的事件
     * @param eventLog 事件
     */
    void consumeLog(List<ContractRawEventLog> allEventLogs, ContractRawEventLog eventLog, ContractEvent event);
}
