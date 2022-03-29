package io.github.alphagozilla.ethereum.event.ingester.ingester.event;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 17:54
 */
public interface ContractEventLogConsumer {
    void consume(ContractRawEventLog eventLog,  String eventJson);
}
