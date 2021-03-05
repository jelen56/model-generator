package com.soga.generator.model.randoms;

import com.soga.generator.model.config.FieldValueConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * @description: 时间生成
 * @author: lzr
 * @create: 2021-03-02 22:51
 */
public class DateGenerator {
    private static DateTimeFormatter dateTimeFormatter;
    private static DateTimeFormatter timeFormatter;

    static {
        dateTimeFormatter = Optional.ofNullable(dateTimeFormatter).orElse(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        timeFormatter = Optional.ofNullable(timeFormatter).orElse(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public static LocalDateTime randomLocalDateTime(String start, String end) {
        assert start != null && end != null : new IllegalArgumentException("start or end can't be null");
        LocalDateTime startDateTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endDateTime = LocalDateTime.parse(end, dateTimeFormatter);
        long betweenMilli = getTimestampBetween(startDateTime, endDateTime);
        long randomAddMilli = RandomDataUtils.longValue(0L, betweenMilli);
        long targetTimestamp = getTimestamp(startDateTime) + randomAddMilli;
        return milliToLocalDateTime(targetTimestamp);
    }

    public static LocalDate randomLocalDate(String start, String end) {
        return randomLocalDateTime(start, end).toLocalDate();
    }

    public static LocalTime randomLocalTime(String start, String end) {
        LocalTime startTime = LocalTime.parse(start, timeFormatter);
        LocalTime endTime = LocalTime.parse(end, timeFormatter);
        long betweenNano = getTimestampBetween(startTime, endTime);
        long randomAddNano = RandomDataUtils.longValue(0L, betweenNano);
        long targetTimestamp = getMilli(startTime) + randomAddNano;
        return LocalTime.ofSecondOfDay(targetTimestamp / (1000));//1 seconde= 1000 milli;
    }

    public static LocalDateTime milliToLocalDateTime(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, getTimeZone());
    }

    public static ZoneOffset getTimeZone() {
        return ZoneOffset.of(FieldValueConfig.TIME_ZONE_OFFSET >= 0 ? "+" + FieldValueConfig.TIME_ZONE_OFFSET : "-" + FieldValueConfig.TIME_ZONE_OFFSET);
    }

    public static long getTimestamp(LocalDateTime localDateTime) {
        assert localDateTime != null : new IllegalArgumentException("localDateTime can't be null");
        return localDateTime.toInstant(getTimeZone()).toEpochMilli();
    }

    public static long getTimestampBetween(LocalDateTime start, LocalDateTime end) {
        return getTimestamp(end) - getTimestamp(start);
    }

    public static long getMilli(LocalTime localTime) {
        assert localTime != null : new IllegalArgumentException("localDateTime can't be null");
        return localTime.toNanoOfDay() / 1000000;//1 milli=1000000 ns
    }

    public static long getTimestampBetween(LocalTime start, LocalTime end) {
        return getMilli(end) - getMilli(start);
    }

    public static String toLocalDateStr(LocalDateTime localDateTime) {
        assert localDateTime != null : new IllegalArgumentException("localDateTime can't be null");
        return dateTimeFormatter.format(localDateTime);
    }

    public static Date randomDate(String start,String end) {
        LocalDateTime localDateTime=randomLocalDateTime(start,end);
        return new Date(getTimestamp(localDateTime));
    }

    public static Date randomDay(String start,String end) {
        return randomDate(start,end);
    }

//    public static Time randomTime(LocalDateTime localDateTime) {
//        return new Date(getTimestamp(localDateTime));
//    }

    public static void main(String[] args) {
        String start = "2019-11-30 15:16:17";
        String end = "2020-01-30 15:16:17";
        System.out.println(randomDay(start, end));
        String startLT = "15:16:17";
        String endLT = "23:14:17";
        System.out.println(randomLocalTime(startLT, endLT));
    }

}
