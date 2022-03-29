package io.github.alphagozilla.ethereum.event.ingester.ingester.util;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatConstant {
    public static DateTimeFormatter DATA_FORMAT_COMPACT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static DateTimeFormatter NORMAL_DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static DateTimeFormatter NORMAL_DATE_TIME_WITH_ZONE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z");

    public static DateTimeFormatter DATA_FORMAT_MINUTE_COMPACT = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public static DateTimeFormatter UTC_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
}
