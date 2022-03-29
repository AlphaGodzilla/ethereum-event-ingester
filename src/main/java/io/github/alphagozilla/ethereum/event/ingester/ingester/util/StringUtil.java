package io.github.alphagozilla.ethereum.event.ingester.ingester.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:22
 */
public class StringUtil {
    public static boolean isNotEmpty(String value) {
        return StringUtils.isNotEmpty(value);
    }

    public static boolean isEmpty(String value) {
        return StringUtils.isEmpty(value);
    }
}
