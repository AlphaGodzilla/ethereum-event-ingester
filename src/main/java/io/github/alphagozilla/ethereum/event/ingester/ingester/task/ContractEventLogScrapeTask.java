package io.github.alphagozilla.ethereum.event.ingester.ingester.task;

import io.github.alphagozilla.ethereum.event.ingester.ingester.BlockChainHeightQuery;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContract;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractRawEventLog;
import io.github.alphagozilla.ethereum.event.ingester.ingester.progress.SyncProgressManager;
import io.github.alphagozilla.ethereum.event.ingester.ingester.scraper.ContractEventLogScraper;
import io.github.alphagozilla.ethereum.event.ingester.ingester.scraper.ScrapeLogsResult;
import io.github.alphagozilla.ethereum.event.ingester.ingester.util.CompareUtil;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:03
 */
public class ContractEventLogScrapeTask implements Callable<Boolean> {
    private final ContractEventLogScraper scraper;
    private final SyncProgressManager syncProgressManager;
    private final BlockChainHeightQuery blockChainHeightQuery;
    private final List<FlowableEventContract> flowableEventContracts;

    public ContractEventLogScrapeTask(ContractEventLogScraper scraper,
                                      SyncProgressManager syncProgressManager,
                                      BlockChainHeightQuery blockChainHeightQuery,
                                      List<FlowableEventContract> flowableEventContracts) {
        this.scraper = scraper;
        this.syncProgressManager = syncProgressManager;
        this.blockChainHeightQuery = blockChainHeightQuery;
        this.flowableEventContracts = flowableEventContracts;
    }

    @Override
    public Boolean call() throws Exception {
        ScrapeLogsResult logsResult = scraper.getLogs(flowableEventContracts);
        // 消费抓取的logs
        matchAndConsumeLogs(logsResult);
        // 保存同步进度
        saveProgress(logsResult);
        // 告诉调用者当次调用是否抓取到了日志
        return hasLogs(logsResult);
    }

    private void matchAndConsumeLogs(ScrapeLogsResult result) {
        List<ContractRawEventLog> logs = result.getLogs();
        for (final ContractRawEventLog log : logs) {
            for (final FlowableEventContract contract : flowableEventContracts) {
                boolean match = contract.addresses().contains(log.getAddress());
                if (match) {
                    // 处理匹配到的事件日志
                    contract.chooseHandleAndConsumeLog(logs, log);
                }
            }
        }
    }

    private void saveProgress(ScrapeLogsResult result) {
        // 保存进度。结束区块作为下次任务的开始区块
        BigInteger endBlock = result.getEndBlock();
        for (final FlowableEventContract contract : flowableEventContracts) {
            BigInteger startBlock = contract.startBlock();
            if (CompareUtil.isLessThan(endBlock, startBlock)) {
                // 忽略还未到达合约
                continue;
            }
            Collection<Address> addresses = contract.addresses();
            for (final Address address : addresses) {
                syncProgressManager.sync(address, endBlock);
            }
        }
    }

    private boolean hasLogs(ScrapeLogsResult result) {
        BigInteger blockHeight = blockChainHeightQuery.blockHeight();
        BigInteger endBlock = result.getEndBlock();
        return result.getLogs().size() > 0 ||
                CompareUtil.isGreaterThan(blockHeight.subtract(endBlock), BigInteger.valueOf(1000));
    }
}
