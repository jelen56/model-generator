package com.soga.generator.model.randoms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 随机身份证号及其地址（县或区级）
 * @author: lzr
 * @create: 2021-02-21 17:04
 */
@Setter
@Getter
@AllArgsConstructor
@ToString
public class RandomIDCardAddress {
    private String idCard;
    private String address;
}
