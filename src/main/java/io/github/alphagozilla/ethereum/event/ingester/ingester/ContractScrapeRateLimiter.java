package io.github.alphagozilla.ethereum.event.ingester.ingester;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 18:04
 */
public interface ContractScrapeRateLimiter {
    /**
     * 申请一个执行许可
     * @return 是否获得执行许可
     */
    boolean tryAcquire();
}
