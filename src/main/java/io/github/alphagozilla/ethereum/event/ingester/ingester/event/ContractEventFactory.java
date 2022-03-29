package io.github.alphagozilla.ethereum.event.ingester.ingester.event;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:12
 */
public class ContractEventFactory {
    private final ContractEventTopic0Encoder contractEventTopic0Encoder;

    public ContractEventFactory(ContractEventTopic0Encoder contractEventTopic0Encoder) {
        this.contractEventTopic0Encoder = contractEventTopic0Encoder;
    }

    public ContractEvent factoryNew(String name, ContractEventAbi abi) {
        return ContractEvent.builder()
                .name(name)
                .topics0(contractEventTopic0Encoder.encodeToTopic0(abi))
                .abi(abi)
                .build();
    }
}
