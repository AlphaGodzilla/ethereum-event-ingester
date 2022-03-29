package io.github.alphagozilla.ethereum.event.ingester.ingester;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 17:36
 */
public interface ContractEventLogScrapeTaskConsumer {
    /**
     * 当前方法会阻塞线程，直到任务能够执行
     */
    void blockUntilCanRunTask() throws Exception;

    /**
     * 退出暂停状态，立即执行
     */
    void immediateRun();
}
