package com.soga.generator.model.randoms;

import com.soga.generator.model.utils.JsonUtils;

import java.io.File;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @description: 随机生成身份证号工具类
 * @author: lzr
 * @create: 2021-02-21 01:06
 */
public class IDCardGenerator {

    public static Map areaCodeMap = null;
    public static int areaCodeMapLength = 0;
    private static ThreadLocalRandom random = null;

    static {
        String path = IDCardGenerator.class.getClassLoader().getResource("").getPath() + "idcard" + File.separator + "areacode.json";
        areaCodeMap = JsonUtils.readFormFile(path, Map.class);
        areaCodeMapLength = areaCodeMap.size();
        random = Optional.ofNullable(random).orElse(ThreadLocalRandom.current());
    }

    /*
     * 地区编码
     */
    private static RandomIDCardAddress areaCode() {
        int index = random.nextInt(areaCodeMapLength);
        Object[] keySets = areaCodeMap.keySet().toArray();
        Object key = keySets[index];
        String parentKey = Integer.parseInt(key.toString()) / 10000 * 10000 + "";
        String parentAddress = areaCodeMap.get(parentKey).toString();
        return new RandomIDCardAddress(key.toString(), parentAddress + areaCodeMap.get(key + ""));
    }

    /*
     * 出生日期
     */
    private static String birthday() {
        LocalDate localDate = LocalDate.now();
        int nowYear = localDate.getYear();
        int nowMonth = localDate.getMonthValue();
        int nowDay = localDate.getDayOfMonth();
        int newYear = random.nextInt(1950, nowYear);
        localDate = localDate.withYear(newYear);
        int newMonth = 0;
        if (nowYear == newYear) {
            newMonth = random.nextInt(1, nowMonth + 1);
            localDate = localDate.withMonth(newMonth);
            if (newMonth == nowMonth) {
                localDate = localDate.withDayOfMonth(random.nextInt(1, nowDay + 1));
            } else {
                localDate = localDate.withDayOfMonth(random.nextInt(1, localDate.lengthOfMonth() + 1));
            }
        } else {
            newMonth = random.nextInt(1, 13);
            localDate = localDate.withMonth(newMonth);
            localDate = localDate.withDayOfMonth(random.nextInt(1, localDate.lengthOfMonth() + 1));
        }
        StringBuilder birthday = new StringBuilder();
        birthday.append(localDate.getYear());
        if (newMonth < 10) {
            birthday.append("0");
        }
        birthday.append(newMonth);
        if (localDate.getDayOfMonth() < 10) {
            birthday.append("0");
        }
        birthday.append(localDate.getDayOfMonth());
        return birthday.toString();
    }

    /*
     * 随机产生倒数三位
     */
    private static String lastThreeCode() {
        int code = random.nextInt(1000);
        if (code < 10) {
            return "00" + code;
        } else if (code < 100) {
            return "0" + code;
        } else {
            return "" + code;
        }
    }

    /*
     * 生成最后一位数字
     */
    private static char lastCode(String previousCodes) {
        if (previousCodes.length() < 17) {
            throw new InvalidParameterException("参数异常");
        }
        int[] intPreviousCodes = Stream.of(previousCodes.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        int[] c = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] r = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int result = IntStream.range(0, intPreviousCodes.length)
                .map(i -> intPreviousCodes[i] * c[i])
                .sum();
        return r[result % 11];
    }

    /*
     * 生成18位身份证号
     */
    public static RandomIDCardAddress generate() {
        StringBuilder idCard = new StringBuilder();
        RandomIDCardAddress randomIDCardAddress = areaCode();
        idCard.append(randomIDCardAddress.getIdCard());
        idCard.append(birthday());
        idCard.append(lastThreeCode());
        idCard.append(lastCode(idCard.toString()));
        randomIDCardAddress.setIdCard(idCard.toString());
        return randomIDCardAddress;
    }

    public static void main(String[] args) {
        System.out.println(generate().toString());
    }
}
