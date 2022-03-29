package io.github.alphagozilla.ethereum.event.ingester.ingester.infra;

import io.github.alphagozilla.ethereum.event.ingester.ingester.ContractEventLogScrapeTaskConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 17:39
 */
@Component
@Slf4j
public class SingleThreadConsumerScheduler {
    private final ContractEventLogScrapeTaskConsumer consumer;

    public SingleThreadConsumerScheduler(ContractEventLogScrapeTaskConsumer consumer) {
        this.consumer = consumer;
        initThread();
        start();
    }

    private Thread taskThreadReference;
    private boolean manualShutdown = false;

    private void initThread() {
        DaemonThreadFactory factory = new DaemonThreadFactory("SingleThreadTaskConsumer");
        taskThreadReference = factory.newThread(() -> {
            log.info("消费线程启动成功");
            while (!isInterrupted() && !manualShutdown) {
                try {
                    consumer.blockUntilCanRunTask();
                }catch (Exception exception) {
                    if (!manualShutdown) {
                        log.error("消费爬取任务的守护线程中断异常", exception);
                    }
                }
            }
            log.warn("消费守护线程发生退出");
        });
    }

    private boolean isInterrupted() {
        return Thread.currentThread().isInterrupted();
    }

    private void start() {
        log.info("准备启动消费线程");
        taskThreadReference.start();
    }

    private void shutdown() {
        manualShutdown = true;
        taskThreadReference.interrupt();
    }

    private boolean isRunning() {
        return taskThreadReference.isAlive();
    }

    private void destroy() {
        if (isRunning()) {
            log.info("发送shutdown指令，等待任务消费者线程关闭");
            shutdown();
            try {
                awaitTermination();
            }catch (final InterruptedException interrupted) {
                Thread.currentThread().interrupt();
            }
            log.info("任务消费者线程关闭成功");
        }else {
            log.info("任务消费者线程已终止");
        }
    }

    private void awaitTermination() throws InterruptedException {
        taskThreadReference.join(TimeUnit.SECONDS.toMillis(30));
    }
}
