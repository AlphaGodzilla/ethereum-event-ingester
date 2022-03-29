package io.github.alphagozilla.ethereum.event.ingester.ingester.util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class IdUtil {
    /**
     * 返回去除中划线的uuid
     * @return uuid
     */
    public static String simpleUuid() {
        return uuid().replace("-", "");
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Long型的ID
     * @return 长整型ID
     */
    public static long nextId() {
        return nextId(System.currentTimeMillis());
    }

    public static long nextId(long timestampMillis) {
        return (timestampMillis << 20) | (ThreadLocalRandom.current().nextLong(0, 1 << 20));
    }
}
