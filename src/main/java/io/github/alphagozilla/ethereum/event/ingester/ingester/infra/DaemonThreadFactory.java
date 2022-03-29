package io.github.alphagozilla.ethereum.event.ingester.ingester.infra;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 17:40
 */
public class DaemonThreadFactory implements ThreadFactory {
    private final String threadName;

    public DaemonThreadFactory(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        final Thread t = new Thread(r, threadName);
        t.setDaemon(true);
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
