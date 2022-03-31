package io.github.alphagozilla.ethereum.event.ingester.ingester.infra;

import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.alphagozilla.ethereum.event.ingester.ingester.BlockChainInfoQuery;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContractFactory;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventFactory;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.EventParser;
import io.github.alphagozilla.ethereum.event.ingester.ingester.progress.SyncProgressManager;
import io.github.alphagozilla.ethereum.event.ingester.ingester.scraper.ContractEventLogScraper;
import io.github.alphagozilla.ethereum.event.ingester.ingester.task.ContractEventLogScrapeTaskFactory;
import io.github.alphagozilla.ethereum.event.ingester.ingester.task.TaskQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author AlphaGodzilla
 * @date 2022/3/28 17:08
 */
@Configuration
public class IngesterConfig {
    @Bean
    public CacheManager blockHeightCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofSeconds(3))
                .initialCapacity(1)
                .maximumSize(100)
        );
        return caffeineCacheManager;
    }

    @Bean
    public ContractEventLogScrapeTaskFactory contractEventLogScraperFactory(ContractEventLogScraper scraper,
                                                                            SyncProgressManager syncProgressManager,
                                                                            BlockChainInfoQuery blockChainInfoQuery
    ) {
        return new ContractEventLogScrapeTaskFactory(scraper, syncProgressManager, blockChainInfoQuery);
    }

    @Value("${ingester.queue.max}")
    private int maxLimit;
    @Value("${ingester.queue.load-factor}")
    private double maxLoadFactor;
    @Bean
    public TaskQueue taskQueue() {
        return new TaskQueue(maxLimit, maxLoadFactor);
    }

    @Bean
    public FlowableEventContractFactory flowableEventContractFactory(SyncProgressManager syncProgressManager,
                                                                     EventParser eventParser) {
        return new FlowableEventContractFactory(syncProgressManager, eventParser);
    }

    @Bean
    public ContractEventFactory contractEventFactory(EventParser eventParser) {
        return new ContractEventFactory(eventParser);
    }
}
