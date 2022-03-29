package io.github.alphagozilla.ethereum.event.ingester.ingester.scraper;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContract;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:44
 */
public interface ContractEventLogScraper {
    /**
     * 抓取合约的事件
     * @param contracts 合约集合
     * @return 抓取结果
     */
    ScrapeLogsResult getLogs(List<FlowableEventContract> contracts);
}
