package io.github.alphagozilla.ethereum.event.ingester.ingester.task;

import io.github.alphagozilla.ethereum.event.ingester.ingester.BlockChainHeightQuery;
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
    private final BlockChainHeightQuery blockChainHeightQuery;

    public ContractEventLogScrapeTaskFactory(ContractEventLogScraper scraper,
                                             SyncProgressManager syncProgressManager,
                                             BlockChainHeightQuery blockChainHeightQuery
    ) {
        this.scraper = scraper;
        this.syncProgressManager = syncProgressManager;
        this.blockChainHeightQuery = blockChainHeightQuery;
    }

    public ContractEventLogScrapeTask factoryNew(List<FlowableEventContract> flowableEventContracts) {
        return new ContractEventLogScrapeTask(scraper, syncProgressManager, blockChainHeightQuery, flowableEventContracts);
    }
}
