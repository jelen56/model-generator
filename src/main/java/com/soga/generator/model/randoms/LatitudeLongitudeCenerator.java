package com.soga.generator.model.randoms;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description: 随机生成经纬度
 * @author: lzr
 * @create: 2021-02-22 23:14
 */
public class LatitudeLongitudeCenerator {
    private static ThreadLocalRandom random = null;

    static {
        random = Optional.ofNullable(random).orElse(ThreadLocalRandom.current());
    }

    public static LatitudeLongitude generate( double minLat, double maxLat,double minLong, double maxLong, int scale) {
        double factor = Math.pow(10, scale);
        BigDecimal factorBigDec = new BigDecimal(factor);
        double minLatStronger = minLat * factor;
        double maxLatStronger = maxLat * factor;
        BigDecimal latitude = new BigDecimal(random.nextInt((int) Math.floor(minLatStronger), (int) Math.floor(maxLatStronger) + 1));
        latitude=latitude.divide(factorBigDec, scale, BigDecimal.ROUND_HALF_UP);
        double minLongStronger = minLong * factor;
        double maxLongStronger = maxLong * factor;
        BigDecimal longitude = new BigDecimal(random.nextInt((int) Math.floor(minLongStronger), (int) Math.floor(maxLongStronger) + 1));
        longitude=longitude.divide(factorBigDec, scale, BigDecimal.ROUND_HALF_UP);
        return new LatitudeLongitude(latitude.doubleValue(), longitude.doubleValue());
    }

    public static void main(String[] args) {
        BigDecimal factorBigDec = new BigDecimal("10");
        BigDecimal latitude = new BigDecimal("1000");
        System.out.println((latitude.divide(factorBigDec,2,BigDecimal.ROUND_HALF_UP)).doubleValue());
        System.out.println(generate(20.1200, 25.3100,109.4500, 117.2000, 4));
    }
}
