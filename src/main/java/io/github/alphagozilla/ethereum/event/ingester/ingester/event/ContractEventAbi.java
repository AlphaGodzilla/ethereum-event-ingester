package io.github.alphagozilla.ethereum.event.ingester.ingester.event;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:32
 */
@Value
@Builder
public class ContractEventAbi {
    String name;
    List<Input> inputs;

    public List<Input> indexedInputs() {
        return inputs.stream().filter(Input::isIndexed).collect(Collectors.toList());
    }

    public List<Input> nonIndexedInputs() {
        return inputs.stream().filter(i -> !i.isIndexed()).collect(Collectors.toList());
    }

    @Value
    @Builder
    public static class Input {
        boolean indexed;
        String type;
        String name;
    }
}
