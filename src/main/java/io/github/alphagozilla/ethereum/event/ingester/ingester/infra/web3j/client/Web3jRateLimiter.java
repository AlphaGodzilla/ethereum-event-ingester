package io.github.alphagozilla.ethereum.event.ingester.ingester.infra.web3j.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.Duration;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 15:36
 */
@Slf4j
@Component
public class Web3jRateLimiter implements Interceptor, InitializingBean {
    private static final String LOCK_NAME = "Web3jRateLimiter";

    @Resource
    private RedissonClient redissonClient;
    @Value("${spring.application.name}")
    private String appName;
    @Value("${web3j.qps}")
    private int qps;
    @Value("${web3j.pause}")
    private Duration pause;

    private RRateLimiter rateLimiter;
    private long maxWaitMills = 0;

    @Override
    public void afterPropertiesSet() throws Exception {
        String redisKey = String.join(":", appName, LOCK_NAME);
        this.rateLimiter = redissonClient.getRateLimiter(redisKey);
        this.rateLimiter.setRate(RateType.PER_CLIENT, qps, 1, RateIntervalUnit.SECONDS);
        log.info("完成{}限流器初始化", LOCK_NAME);
        this.maxWaitMills = pause.toMillis();
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        long maxTimestamp = getStopTimestamp();
        boolean hasPermit = false;
        while (!isInterrupted() && !maxWait(maxTimestamp) && !(hasPermit = acquirePermit())) {
            try {
                Thread.sleep(100);
            }catch (InterruptedException ex) {
                log.error("排队线程异常中断", ex);
                throw new RuntimeException("排队线程异常中断", ex);
            }
        }
        if (!hasPermit || maxWait(maxTimestamp)) {
            throw new RuntimeException("无法获得请求许可");
        }
        log.debug("获得web3j请求许可");
        // 发出请求
        return chain.proceed(chain.request());
    }

    private long getStopTimestamp() {
        return System.currentTimeMillis() + maxWaitMills;
    }

    private boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }

    private boolean maxWait(long maxTimestamp) {
        return System.currentTimeMillis() >= maxTimestamp;
    }

    private boolean acquirePermit() {
        return rateLimiter.tryAcquire();
    }
}
