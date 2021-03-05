package com.soga.generator.test.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BaseEntity {
    /**
     * 主键，UUID
     */
    private long id;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 删除标识，0：未删除，1：删除
     */
    private Integer deleteFlag = 0;
    /**
     * 版本号，默认1
     */
    private Integer version = 1;
}
