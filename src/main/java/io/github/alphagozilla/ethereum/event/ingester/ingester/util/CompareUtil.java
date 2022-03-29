package io.github.alphagozilla.ethereum.event.ingester.ingester.util;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 17:06
 */
public class CompareUtil {
    @SuppressWarnings("unchecked")
    public static <T> boolean isGreaterThan(Comparable<T> a, Comparable<T> b) {
        return a.compareTo((T) b) > 0;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean isLessThan(Comparable<T> a, Comparable<T> b) {
        return a.compareTo((T) b) < 0;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean equals(Comparable<T> a, Comparable<T> b) {
        return a.compareTo((T) b) == 0;
    }
}
