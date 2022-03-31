package io.github.alphagozilla.ethereum.event.ingester.ingester.event;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:12
 */
public class ContractEventFactory {
    private final EventParser eventParser;

    public ContractEventFactory(EventParser eventParser) {
        this.eventParser = eventParser;
    }

    public ContractEvent factoryNew(String name, ContractEventAbi abi) {
        return ContractEvent.builder()
                .name(name)
                .topics0(eventParser.encodeEventToTopic0(abi))
                .abi(abi)
                .build();
    }
}
