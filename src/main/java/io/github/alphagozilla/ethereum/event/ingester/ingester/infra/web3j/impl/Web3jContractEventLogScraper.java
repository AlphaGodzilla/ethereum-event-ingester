package io.github.alphagozilla.ethereum.event.ingester.ingester.infra.web3j.impl;

import io.github.alphagozilla.ethereum.event.ingester.ingester.BlockChainHeightQuery;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContract;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractRawEventLog;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.EventParser;
import io.github.alphagozilla.ethereum.event.ingester.ingester.infra.converter.ContractRawEventLogConverter;
import io.github.alphagozilla.ethereum.event.ingester.ingester.infra.web3j.client.Web3jFactory;
import io.github.alphagozilla.ethereum.event.ingester.ingester.scraper.ContractEventLogScraper;
import io.github.alphagozilla.ethereum.event.ingester.ingester.scraper.ScrapeLogsResult;
import io.github.alphagozilla.ethereum.event.ingester.ingester.util.CompareUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;
import org.web3j.protocol.core.methods.response.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 17:49
 */
@Slf4j
@Component
public class Web3jContractEventLogScraper implements ContractEventLogScraper {
    private static final BigInteger DEFAULT_BLOCK_STEP = BigInteger.valueOf(5000);

    private final Web3jFactory web3jFactory;
    private final BlockChainHeightQuery blockChainHeightQuery;
    private final EventParser eventParser;

    public Web3jContractEventLogScraper(Web3jFactory web3jFactory,
                                        BlockChainHeightQuery blockChainHeightQuery,
                                        EventParser eventParser) {
        this.web3jFactory = web3jFactory;
        this.blockChainHeightQuery = blockChainHeightQuery;
        this.eventParser = eventParser;
    }

    @Override
    public ScrapeLogsResult getLogs(List<FlowableEventContract> contracts) {
        BigInteger startBlock = startBlock(contracts);
        BigInteger endBlock = endBlock(startBlock);
        DefaultBlockParameter startBlockParam = DefaultBlockParameter.valueOf(startBlock);
        DefaultBlockParameter endBlockParam = DefaultBlockParameter.valueOf(endBlock);

        EthFilter ethFilter = new EthFilter(startBlockParam, endBlockParam, addresses(contracts))
                .addOptionalTopics(eventSignatures(contracts));

        List<EthLog.LogResult> logResults = requestLogs(ethFilter);
        List<Log> logs = new ArrayList<>(logResults.size());
        for (final EthLog.LogResult logResult : logResults) {
            Log log = (Log) logResult.get();
            logs.add(log);
        }
        List<ContractRawEventLog> rawEventLogs = ContractRawEventLogConverter.INSTANT.toContractRawEventLogs(logs);
        return ScrapeLogsResult.builder()
                .logs(rawEventLogs)
                .startBlock(startBlock)
                .endBlock(endBlock)
                .build();
    }

    private BigInteger startBlock(List<FlowableEventContract> contracts) {
        BigInteger blockHeight = blockChainHeightQuery.blockHeight();
        List<BigInteger> startBlocks = contracts.stream()
                .map(FlowableEventContract::startBlock)
                .collect(Collectors.toList());
        BigInteger blockNumber = Collections.min(startBlocks).add(BigInteger.ONE);
        if (CompareUtil.equals(blockNumber, blockHeight)) {
            return blockHeight;
        }
        return Collections.min(Set.of(blockNumber, blockHeight));
    }

    private String[] eventSignatures(List<FlowableEventContract> contracts) {
        return contracts.stream()
                .flatMap(i -> i.needFlowableEvents().stream())
                .map(i -> eventParser.encodeEventToTopic0(i.getAbi()))
                .distinct()
                .toArray(String[]::new);
    }

    private List<String> addresses(List<FlowableEventContract> contracts) {
        return contracts.stream()
                .flatMap(i -> i.addresses().stream())
                .distinct()
                .map(Address::getValue)
                .collect(Collectors.toList());
    }

    private BigInteger endBlock(BigInteger startBlock) {
        BigInteger blockHeight = blockChainHeightQuery.blockHeight();
        BigInteger endBlock = startBlock.add(DEFAULT_BLOCK_STEP);
        if (CompareUtil.equals(endBlock, blockHeight)) {
            return blockHeight;
        }
        return Collections.min(Set.of(endBlock, blockHeight));
    }

    private List<EthLog.LogResult> requestLogs(EthFilter ethFilter) {
        try {
            return web3jFactory.getClient().ethGetLogs(ethFilter).send().getLogs();
        }catch (IOException ex) {
            log.error("拉取合约事件日志异常", ex);
            throw new RuntimeException(ex);
        }
    }
}
