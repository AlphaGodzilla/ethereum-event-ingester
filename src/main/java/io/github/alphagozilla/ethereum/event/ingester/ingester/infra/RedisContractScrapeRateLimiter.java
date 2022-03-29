package io.github.alphagozilla.ethereum.event.ingester.ingester.infra;

import io.github.alphagozilla.ethereum.event.ingester.ingester.ContractScrapeRateLimiter;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 18:23
 */
@Component
public class RedisContractScrapeRateLimiter implements ContractScrapeRateLimiter, InitializingBean {
    private final RedissonClient redissonClient;

    @Value("${spring.application.name}")
    private String appName;

    private RRateLimiter rateLimiter;

    public RedisContractScrapeRateLimiter(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rateLimiter = redissonClient.getRateLimiter(String.join(":",appName, "rateLimiter"));
        rateLimiter.trySetRate(RateType.PER_CLIENT, 30, 1, RateIntervalUnit.SECONDS);
    }

    @Override
    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }
}
