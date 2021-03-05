package com.soga.generator.model.field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import static com.soga.generator.model.randoms.RandomDataUtils.*;

/**
 * @description: field值得应用类型
 * @author: lzr
 * @create: 2021-01-08 23:11
 **/
public enum FieldType {
    FIELD_STRING_TYPE(String.class) {
        @Override
        public String convert(String fieldName) {
            return stringValue1(fieldName);
        }
    },
    FIELD_SHORT_TYPE(Short.class) {
        @Override
        public Short convert(String fieldName) {
            return shortValue1(fieldName);
        }
    },
    FIELD_INTEGER_TYPE(Integer.class) {
        @Override
        public Integer convert(String fieldName) {
            return intValue1(fieldName);
        }
    },
    FIELD_LONG_TYPE(Long.class) {
        @Override
        public Long convert(String fieldName) {
            return longValue(null);
        }
    },
    FIELD_FLOAT_TYPE(Float.class) {
        @Override
        public Float convert(String fieldName) {
            return floatValue();
        }
    },
    FIELD_DOUBLE_TYPE(Double.class) {
        @Override
        public Double convert(String fieldName) {
            return doubleValue1(fieldName);
        }
    },
    FIELD_CHARACTER_TYPE(Character.class) {
        @Override
        public Character convert(String fieldName) {
            return charValue();
        }
    },
    FIELD_BOOLEAN_TYPE(Boolean.class) {
        @Override
        public Boolean convert(String fieldName) {
            return booleanValue();
        }
    },
    FIELD_BYTE_TYPE(Byte.class) {
        @Override
        public Byte convert(String fieldName) {
            return byteValue();
        }
    },
    FIELD_DATE_TYPE(Date.class) {
        @Override
        public Date convert(String fieldName) {
            return dateValue(null);
        }
    },
    FIELD_LOCALDATE_TYPE(LocalDate.class) {
        @Override
        public LocalDate convert(String fieldName) {
            return localDateValue(null);
        }
    },
    FIELD_LOCALTIME_TYPE(LocalTime.class) {
        @Override
        public LocalTime convert(String fieldName) {
            return localTimeValue(null);
        }
    },
    FIELD_LOCALDATETIME_TYPE(LocalDateTime.class) {
        @Override
        public LocalDateTime convert(String fieldName) {
            return localDateTimeValue(null);
        }
    };
    private Class clazz;//类型值

    FieldType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public <T> T convert(String fieldName) {
        return null;
    }

    public static FieldType getField(Class clazz) {
        return Stream.of(values())
                .filter(it -> it.getClazz() == clazz)
                .findFirst()
                .orElse(null);
    }

    protected String stringValue1(String fieldName) {
        return (String) Optional.ofNullable(DefaultFieldValueType.getField(fieldName)).map(DefaultFieldValueType::convert).orElse(stringValue());
    }

    protected Short shortValue1(String fieldName) {
        return (Short) Optional.ofNullable(DefaultFieldValueType.getField(fieldName)).map(DefaultFieldValueType::convert).orElse(shortValue(null));
    }

    protected Integer intValue1(String fieldName) {
        return (Integer) Optional.ofNullable(DefaultFieldValueType.getField(fieldName)).map(DefaultFieldValueType::convert).orElse(intValue(null));
    }

    protected Double doubleValue1(String fieldName) {
        return (Double) Optional.ofNullable(DefaultFieldValueType.getField(fieldName)).map(DefaultFieldValueType::convert).orElse(doubleValue(null));
    }
}
