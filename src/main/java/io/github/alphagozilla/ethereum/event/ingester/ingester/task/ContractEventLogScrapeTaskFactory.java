package io.github.alphagozilla.ethereum.event.ingester.ingester.task;

import io.github.alphagozilla.ethereum.event.ingester.ingester.BlockChainInfoQuery;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContract;
import io.github.alphagozilla.ethereum.event.ingester.ingester.progress.SyncProgressManager;
import io.github.alphagozilla.ethereum.event.ingester.ingester.scraper.ContractEventLogScraper;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 17:19
 */
public class ContractEventLogScrapeTaskFactory {
    private final ContractEventLogScraper scraper;
    private final SyncProgressManager syncProgressManager;
    private final BlockChainInfoQuery blockChainInfoQuery;

    public ContractEventLogScrapeTaskFactory(ContractEventLogScraper scraper,
                                             SyncProgressManager syncProgressManager,
                                             BlockChainInfoQuery blockChainInfoQuery
    ) {
        this.scraper = scraper;
        this.syncProgressManager = syncProgressManager;
        this.blockChainInfoQuery = blockChainInfoQuery;
    }

    public ContractEventLogScrapeTask factoryNew(List<FlowableEventContract> flowableEventContracts) {
        return new ContractEventLogScrapeTask(scraper, syncProgressManager, blockChainInfoQuery, flowableEventContracts);
    }
}
