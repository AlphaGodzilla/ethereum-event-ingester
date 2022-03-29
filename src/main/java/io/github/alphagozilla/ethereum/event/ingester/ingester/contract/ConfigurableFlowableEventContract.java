package io.github.alphagozilla.ethereum.event.ingester.ingester.contract;

import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEvent;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventLogConsumer;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractRawEventLog;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.EventParser;
import io.github.alphagozilla.ethereum.event.ingester.ingester.progress.SyncProgressManager;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 17:25
 */
@Slf4j
public class ConfigurableFlowableEventContract implements FlowableEventContract {
    private final FlowableEventContractConfig config;
    private final SyncProgressManager syncProgressManager;
    private final EventParser eventParser;

    public ConfigurableFlowableEventContract(SyncProgressManager syncProgressManager,
                                             EventParser eventParser,
                                             FlowableEventContractConfig config) {
        this.syncProgressManager = syncProgressManager;
        this.eventParser = eventParser;
        this.config = config;
        init();
    }

    private final Map<String, ContractEvent> eventTopic0Map = new HashMap<>();

    private void init() {
        for (final ContractEvent event : config.getEvents()) {
            String topic0 = event.getTopics0();
            eventTopic0Map.put(topic0, event);
        }
    }

    @Override
    public String name() {
        return config.getName();
    }

    @Override
    public Collection<Address> addresses() {
        return config.getAddresses();
    }

    @Override
    public BigInteger startBlock() {
        Set<BigInteger> startBlockNums = addresses().stream()
                .distinct()
                .map(i -> syncProgressManager.getProgress(i).orElse(config.getInitBlock()))
                .collect(Collectors.toSet());
        if (startBlockNums.isEmpty()) {
            return config.getInitBlock();
        }
        return Collections.min(startBlockNums);
    }

    @Override
    public Set<ContractEvent> needFlowableEvents() {
        return config.getEvents();
    }

    @Override
    public void chooseHandleAndConsumeLog(List<ContractRawEventLog> allLogs, ContractRawEventLog log) {
        String topic0 = log.getTopics().get(0);
        ContractEvent event = eventTopic0Map.get(topic0);
        if (event == null) {
            return;
        }
        consumeLog(allLogs, log, event);
    }

    @Override
    public void consumeLog(List<ContractRawEventLog> allEventLogs, ContractRawEventLog eventLog, ContractEvent event) {
        String eventJson = eventParser.toJson(eventLog, event.getAbi());
        ContractEventLogConsumer eventLogConsumer = config.getEventLogConsumer();
        if (eventLogConsumer == null) {
            log.warn("合约: {}, 事件: {} 没有注册渠道", eventLog.getAddress().getValue(), event.getName());
            return;
        }
        eventLogConsumer.consume(eventLog, eventJson);
    }
}
