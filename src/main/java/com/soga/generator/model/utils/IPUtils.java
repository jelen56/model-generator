package com.soga.generator.model.utils;

import java.util.stream.Stream;

/**
 * @description: ip地址工具类
 * @author: lzr
 * @create: 2021-02-19 23:54
 */
public class IPUtils {
    /*
     * 将十进制ip转换为真正的IP地址
     */
    public static String dec2ip(int ip) {
        int[] b = new int[4];
        b[0] = (ip >> 24) & 0xff;//ipv4地址分为4部分,每部分最大255,也就是2的8次方,总共最大32位,将ip右移24位,同时只保留剩下的右边8位(0xff=1111 1111)
        b[1] = (ip >> 16) & 0xff;
        b[2] = (ip >> 8) & 0xff;
        b[3] = ip & 0xff;
        return b[0] + "." + b[1] + "." + b[2] + "." + b[3];
    }

    /*
     * 将字符串ip转为数值
     */
    public static int ip2Int(String ip) {
        int[] ipsplices = Stream.of(ip.split("\\.")).mapToInt(ipSplice -> Integer.valueOf(ipSplice)).toArray();
        int result = 0;
        result = ipsplices[3] & 0xFF;
        result |= ((ipsplices[2] << 8) & 0xFF00);
        result |= ((ipsplices[1] << 16) & 0xFF0000);
        result |= ((ipsplices[0] << 24) & 0xFF000000);
        return result;
    }
}
