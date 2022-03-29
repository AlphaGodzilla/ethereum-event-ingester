package io.github.alphagozilla.ethereum.event.ingester.ingester.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 17:31
 */
@Slf4j
public class TaskQueue {
    /**
     * 队列
     */
    private final LinkedBlockingQueue<ContractEventLogScrapeTask> queue;
    /**
     * 队列最大长度
     */
    int maxLimit;
    /**
     * 最大负载系数
     */
    double maxLoadFactor;

    public TaskQueue(int maxLimit, double maxLoadFactor) {
        this.maxLimit = maxLimit;
        this.maxLoadFactor = maxLoadFactor;
        this.queue = new LinkedBlockingQueue<>(maxLimit);
    }

    public boolean canOffer() {
        return queue.remainingCapacity() > 0;
    }

    public void offer(ContractEventLogScrapeTask task) {
        boolean success = this.queue.offer(task);
        if (!success) {
            log.error("ContractScapeTaskQueue was fulled");
        }
    }

    public ContractEventLogScrapeTask take() throws InterruptedException {
        return queue.take();
    }

    /**
     * 队列任务满载率
     * @return 满载率
     */
    public double loadFactor() {
        return queue.size() * 1.0 / maxLimit;
    }

    /**
     * 是否达到负载参数
     */
    public boolean arriveMaxLoadFactor() {
        return loadFactor() >= maxLoadFactor;
    }

    /**
     * 清空队列
     */
    public void clear() {
        queue.clear();
    }

    /**
     * 补充队列到达负载系数为止
     * @param taskSupplier 任务工厂
     * @return 本次添加的任务数
     */
    public int offerUntilArriveLoadFactor(Supplier<ContractEventLogScrapeTask> taskSupplier) {
        int count = 0;
        while (canOffer() && !arriveMaxLoadFactor()) {
            offer(taskSupplier.get());
            count++;
        }
        return count;
    }
}
