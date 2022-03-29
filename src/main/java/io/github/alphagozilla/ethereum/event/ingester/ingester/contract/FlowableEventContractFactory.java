package io.github.alphagozilla.ethereum.event.ingester.ingester.contract;

import io.github.alphagozilla.ethereum.event.ingester.ingester.event.EventParser;
import io.github.alphagozilla.ethereum.event.ingester.ingester.progress.SyncProgressManager;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 10:26
 */
public class FlowableEventContractFactory {
    private final SyncProgressManager syncProgressManager;
    private final EventParser eventParser;

    public FlowableEventContractFactory(SyncProgressManager syncProgressManager, EventParser eventParser) {
        this.syncProgressManager = syncProgressManager;
        this.eventParser = eventParser;
    }

    public FlowableEventContract factoryNew(FlowableEventContractConfig config) {
        return new ConfigurableFlowableEventContract(syncProgressManager, eventParser, config);
    }
}
