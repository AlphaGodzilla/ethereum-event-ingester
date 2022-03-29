package io.github.alphagozilla.ethereum.event.ingester.ingester.util;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimestampUtil {
    public final static Comparator<Timestamp> TIMESTAMP_COMPARATOR_INSTANCE = new TimestampComparator();

    public static class TimestampComparator implements Comparator<Timestamp> {
        @Override
        public int compare(Timestamp o1, Timestamp o2) {
            return o1.compareTo(o2);
        }
    }

    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp minus(Timestamp from, long n, TimeUnit timeUnit) {
        long timestamp = from.getTime();
        final long difference = TimeUnit.MILLISECONDS.convert(n, timeUnit);
        long targetTimestamp = timestamp - difference;
        return new Timestamp(targetTimestamp <= 0 ? 0 : targetTimestamp);
    }

    public static Timestamp minus(long n, TimeUnit timeUnit) {
        final long now = System.currentTimeMillis();
        final long difference = TimeUnit.MILLISECONDS.convert(n, timeUnit);
        long targetTimestamp = now - difference;
        return new Timestamp(targetTimestamp <= 0 ? 0 : targetTimestamp);
    }
    public static Timestamp plus(long from, long n, TimeUnit timeUnit) {
        final long difference = TimeUnit.MILLISECONDS.convert(n, timeUnit);
        return new Timestamp(from + difference);
    }

    public static Timestamp plus(long n, TimeUnit timeUnit) {
        final long now = System.currentTimeMillis();
        final long difference = TimeUnit.MILLISECONDS.convert(n, timeUnit);
        return new Timestamp(now + difference);
    }

    public static Timestamp fromLocalDateTime(LocalDateTime time) {
        return Timestamp.valueOf(time);
    }

    public static Timestamp fromDate(Date time) {
        return new Timestamp(time.getTime());
    }

    public static Timestamp fromTs(String ts) {
        if (StringUtils.isEmpty(ts)) {
            return null;
        }
        switch (ts.length()) {
            case 10:
                return new Timestamp(Long.parseLong(ts) * 1000);
            case 13:
                return new Timestamp(Long.parseLong(ts));
            default:
                throw new RuntimeException("未知的ts格式:" + ts);
        }
    }

    public static Timestamp fromMillis(long millis) {
        return new Timestamp(millis);
    }

    public static Timestamp fromInstant(Instant instant) {
        return new Timestamp(instant.toEpochMilli());
    }

    public static Range<Timestamp> rangeOf(Timestamp startTime, Timestamp endTime) {
        return Range.between(startTime, endTime, TIMESTAMP_COMPARATOR_INSTANCE);
    }

    public static Timestamp parse(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        final LocalDateTime localDateTime = LocalDateTime.parse(value, DateTimeFormatConstant.NORMAL_DATE_TIME_PATTERN);
        final Instant instant = ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant();
        return Timestamp.from(instant);
    }

    public static String utcFormat(Timestamp timestamp) {
        Instant instant = timestamp.toInstant();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
        return zonedDateTime.format(DateTimeFormatConstant.UTC_DATE_FORMAT);
    }

    public static String localFormat(Timestamp timestamp) {
        return localFormat(timestamp, DateTimeFormatConstant.NORMAL_DATE_TIME_PATTERN);
    }

    public static String localFormat(Timestamp timestamp, DateTimeFormatter pattern) {
        if (timestamp == null) {
            return null;
        }
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        return localDateTime.format(pattern);
    }

    public static String formatWithZone(Timestamp timestamp, ZoneId zone) {
        if (timestamp == null) {
            return null;
        }
        ZonedDateTime zonedDateTime = timestamp.toInstant().atZone(zone);
        return zonedDateTime.format(DateTimeFormatConstant.NORMAL_DATE_TIME_WITH_ZONE_PATTERN);
    }
}
