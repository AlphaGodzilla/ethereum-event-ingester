package io.github.alphagozilla.ethereum.event.ingester.ingester.infra;

import io.github.alphagozilla.ethereum.event.ingester.ingester.ContractEventLogScrapeTaskConsumer;
import io.github.alphagozilla.ethereum.event.ingester.ingester.ContractScrapeRateLimiter;
import io.github.alphagozilla.ethereum.event.ingester.ingester.task.ContractEventLogScrapeTask;
import io.github.alphagozilla.ethereum.event.ingester.ingester.task.TaskQueue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 17:57
 */
@Slf4j
@Component
public class SingleThreadConsumer implements ContractEventLogScrapeTaskConsumer, InitializingBean {
    private final ContractScrapeRateLimiter rateLimiter;
    private final TaskQueue taskQueue;

    @Value("${ingester.consumer.pause}")
    private Duration consumerPause;
    @Value("${ingester.consumer.min-wait}")
    private Duration minWaitToNextTask;
    @Value("${ingester.consumer.max-wait}")
    private Duration maxWaitToNextTask;

    private final AtomicLong waitToNextTaskMillis = new AtomicLong(0);
    private final AtomicBoolean immediatelyExecute = new AtomicBoolean(false);

    public SingleThreadConsumer(ContractScrapeRateLimiter rateLimiter, TaskQueue taskQueue) {
        this.rateLimiter = rateLimiter;
        this.taskQueue = taskQueue;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        waitToNextTaskMillis.set(minWaitToNextTask.toMillis());
    }

    @Override
    public void blockUntilCanRunTask() throws Exception {
        // 等待直到可以执行
        awaitUtilCanRun();
        // 申请消费凭证
        tryAcquirePermit();
        // take()方法会阻塞在本行，直到队列中出现可消费的元素
        ContractEventLogScrapeTask task = taskQueue.take();
        log.debug("准备执行爬取任务");
        try {
            boolean hasLogs = task.call();
            calcNextWaitTime(hasLogs);
        }catch (Exception e) {
            log.error("同步任务执行异常", e);
            return;
        }
        log.debug("爬取任务执行完成");
    }

    @Override
    public void immediateRun() {
        immediatelyExecute.set(true);
        waitToNextTaskMillis.set(minWaitToNextTask.toMillis());
        log.debug("取消线程挂起");
    }

    private void awaitUtilCanRun() throws InterruptedException {
        log.debug("等待下一个任务开始: {}ms", waitToNextTaskMillis);
        long start = System.currentTimeMillis();
        while (!immediatelyExecute.get() && System.currentTimeMillis() - start <= waitToNextTaskMillis.get()) {
            Thread.sleep(100);
        }
        // 恢复正常执行状态
        immediatelyExecute.set(false);
    }

    private void tryAcquirePermit() throws InterruptedException {
        boolean hasPermit = rateLimiter.tryAcquire();
        if (!hasPermit) {
            log.warn("超过最大消费频率: 暂停消费");
            Thread.sleep(consumerPause.toMillis());
            log.warn("超过最大消费频率: 恢复消费");
        }
    }

    private void calcNextWaitTime(boolean hasLog) {
        if (hasLog) {
            waitToNextTaskMillis.set(minWaitToNextTask.toMillis());
            return;
        }
        // 如果没有数据，等待时间乘10倍
        waitToNextTaskMillis.set(Math.min(waitToNextTaskMillis.get() * 10, maxWaitToNextTask.toMillis()));
    }
}
