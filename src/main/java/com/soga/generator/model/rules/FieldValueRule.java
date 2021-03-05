package com.soga.generator.model.rules;

import lombok.ToString;

import java.util.List;

/**
 * @description: 成员变量规则
 * @author: lzr
 * @create: 2021-01-10 18:08
 **/
@ToString
public class FieldValueRule<T> {
    /***
     * @Description: 规则生效的成员变量过滤名
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private final String filterFieldName;
    /***
     * @Description: 后缀名
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private final String suffixs;
    /***
     * @Description: 最短长度
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private final int minLength;
    /***
     * @Description: 最大长度
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private final int maxLength;
    /***
     * @Description: 最小取值范围()
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private final T minRange;
    /***
     * @Description: 最大取值范围()
     * @Author: lzr
     * @Date: 2021/1/10
     */
    private final T maxRange;

    /***
     * @Description: 可取值(优先级别高于 minRange和maxRange)
     * @Author: lzr
     * @Date: 2021/1/13
     */
    private final List<T> rangeValues;

    FieldValueRule(Builder<T> builder) {
        filterFieldName = builder.filterFieldName;
        suffixs = builder.suffixs;
        minLength = builder.minLength;
        maxLength = builder.maxLength;
        minRange = builder.minRange;
        maxRange = builder.maxRange;
        rangeValues = builder.rangeValues;
    }

    private void checkParams(){

    }

    public static class Builder<T> {
        private String filterFieldName;
        private String suffixs;
        private int minLength;
        private int maxLength;
        private T minRange;
        private T maxRange;
        private List<T> rangeValues;

        public Builder() {
        }

        public Builder filterFieldName(String filterFieldName) {
            this.filterFieldName = filterFieldName;
            return this;
        }

        public Builder suffixs(String suffixs) {
            this.suffixs = suffixs;
            return this;
        }

        public Builder minLength(int minLength) {
            this.minLength = minLength;
            return this;
        }

        public Builder maxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public Builder minRange(T minRange) {
            this.minRange = minRange;
            return this;
        }

        public Builder maxRange(T maxRange) {
            this.maxRange = maxRange;
            return this;
        }
        public Builder rangeValues(List<T> rangeValues){
            this.rangeValues=rangeValues;
            return this;
        }

        public FieldValueRule build(){
            return new FieldValueRule(this);
        }

    }
}
