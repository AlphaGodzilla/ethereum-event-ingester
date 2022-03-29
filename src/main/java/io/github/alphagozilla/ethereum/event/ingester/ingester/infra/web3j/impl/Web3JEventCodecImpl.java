package io.github.alphagozilla.ethereum.event.ingester.ingester.infra.web3j.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.*;
import io.github.alphagozilla.ethereum.event.ingester.ingester.infra.converter.ContractRawEventLogConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.AbiTypes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.tx.Contract;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 18:11
 */
@Slf4j
@Component
public class Web3JEventCodecImpl implements EventParser, ContractEventTopic0Encoder, ContractEventDecoder {
    private final ObjectMapper objectMapper;

    public Web3JEventCodecImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String toJson(ContractRawEventLog eventLog, ContractEventAbi abi) {
        Event event = buildEvent(abi);
        Log web3jLog = ContractRawEventLogConverter.INSTANT.toWeb3jLog(eventLog);
        EventValues eventValues = Contract.staticExtractEventParameters(event, web3jLog);
        if (eventValues == null) {
            return "";
        }
        List<Type> indexedValues = eventValues.getIndexedValues();
        List<Type> nonIndexedValues = eventValues.getNonIndexedValues();
        LinkedHashMap<String, String> jsonMap = new LinkedHashMap<>(indexedValues.size() + nonIndexedValues.size());
        List<ContractEventAbi.Input> indexedInputs = abi.indexedInputs();
        List<ContractEventAbi.Input> nonIndexedInputs = abi.nonIndexedInputs();
        for (int i = 0; i < indexedValues.size(); i++) {
            ContractEventAbi.Input indexInput = indexedInputs.get(i);
            Type type = indexedValues.get(i);
            jsonMap.put(indexInput.getName(), type.getValue().toString());
        }
        for (int i = 0; i < nonIndexedValues.size(); i++) {
            ContractEventAbi.Input nonIndexedInput = nonIndexedInputs.get(i);
            Type type = nonIndexedValues.get(i);
            jsonMap.put(nonIndexedInput.getName(), type.getValue().toString());
        }
        try {
            return objectMapper.writeValueAsString(jsonMap);
        }catch (Exception exception) {
            log.error("序列化事件为json字符串异常", exception);
            return "";
        }
    }

    @Override
    public String encodeEventToTopic0(ContractEventAbi abi) {
        return EventEncoder.encode(buildEvent(abi));
    }

    private Event buildEvent(ContractEventAbi abi) {
        String eventName = abi.getName();
        List<ContractEventAbi.Input> inputs = abi.getInputs();
        List<TypeReference<?>> parameters = inputs.stream().map(i -> {
            Class<? extends Type> type = AbiTypes.getType(i.getType());
            return TypeReference.create(type, i.isIndexed());
        }).collect(Collectors.toList());
        return new Event(eventName, parameters);
    }

    @Override
    public String parseToJsonStr(ContractEventAbi abi, ContractRawEventLog eventLog) {
        return toJson(eventLog, abi);
    }

    @Override
    public String encodeToTopic0(ContractEventAbi eventAbi) {
        return encodeEventToTopic0(eventAbi);
    }
}
