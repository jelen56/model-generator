package com.soga.generator.model.field;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @description: fieldClass类
 * @author: lzr
 * @create: 2021-01-09 23:26
 **/
public class FieldClass {

    public static Class toClazz(Class clazz) {
        return Optional.ofNullable(FieldBasicType.getFieldBasicType(clazz)).map(FieldBasicType::convert).orElse(clazz);
    }
}

/***
 * @Description: basicType convert
 * @Author: lzr
 * @Date: 2021/1/9
 */
enum FieldBasicType {
    SHORT(short.class) {
        @Override
        Class convert() {
            return Short.class;
        }
    },
    INT(int.class) {
        @Override
        Class convert() {
            return Integer.class;
        }
    },
    LONG(long.class) {
        @Override
        Class convert() {
            return Long.class;
        }
    },
    CHAR(char.class) {
        @Override
        Class convert() {
            return Character.class;
        }
    },
    BOOLEAN(boolean.class) {
        @Override
        Class convert() {
            return Boolean.class;
        }
    },
    BYTE(byte.class) {
        @Override
        Class convert() {
            return Byte.class;
        }
    },
    FLOAT(float.class) {
        @Override
        Class convert() {
            return Float.class;
        }
    },
    DOUBLE(double.class) {
        @Override
        Class convert() {
            return Double.class;
        }
    };
    private Class clazz;

    FieldBasicType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    Class convert() {
        return null;
    }

    public static FieldBasicType getFieldBasicType(Class clazz) {
        return Stream.of(values())
                .filter(it -> it.getClazz() == clazz)
                .findFirst()
                .orElse(null);
    }
}
