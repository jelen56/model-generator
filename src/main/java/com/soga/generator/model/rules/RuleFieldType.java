package com.soga.generator.model.rules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @description: 规则field类型映射
 * @author: lzr
 * @create: 2021-03-03
 */
public enum RuleFieldType {
    BOOLEAN_RULE(BooleanFieldValueRule.class) {
        @Override
        public Class[] get() {
            return new Class[]{Boolean.class};
        }

        @Override
        public BooleanFieldValueRule[] instance() {
            return new BooleanFieldValueRule[]{new BooleanFieldValueRule.Builder().value(false).build()};
        }
    },
    FLOATDOUBLE_RULE(FloatDoubleFieldValueRule.class) {
        @Override
        public Class[] get() {
            return new Class[]{Float.class, Double.class};
        }

        @Override
        public FloatDoubleFieldValueRule[] instance() {
            return new FloatDoubleFieldValueRule[]{
                    new FloatDoubleFieldValueRule.Builder<Float>() {
                    }.build(),
                    new FloatDoubleFieldValueRule.Builder<Double>() {
                    }.build()};
        }
    },
    LISTSET_RULE(ListSetFieldValueRule.class) {
        @Override
        public Class[] get() {
            return new Class[]{List.class, Set.class};
        }

        @Override
        public ListSetFieldValueRule[] instance() {
            return new ListSetFieldValueRule[]{
                    new ListSetFieldValueRule.Builder<List>() {
                    }.build(),
                    new ListSetFieldValueRule.Builder<Set>() {
                    }.build()};
        }
    },
    MAP_RULE(MapFieldValueRule.class) {
        @Override
        public Class[] get() {
            return new Class[]{Map.class};
        }

        @Override
        public MapFieldValueRule[] instance() {
            return new MapFieldValueRule[]{new MapFieldValueRule.Builder().build()};
        }
    },
    STRING_RULE(StringFieldValueRule.class) {
        @Override
        public Class[] get() {
            return new Class[]{String.class, Character.class};
        }

        @Override
        public StringFieldValueRule[] instance() {
            return new StringFieldValueRule[]{
                    new StringFieldValueRule.Builder<String>() {
                    }.build(),
                    new StringFieldValueRule.Builder<Character>() {
                    }.build()};
        }
    },
    WHOLENUMBER_RULE(WholeNumberFieldValueRule.class) {
        @Override
        public Class[] get() {
            return new Class[]{Byte.class, Short.class, Integer.class, Long.class};
        }

        @Override
        public WholeNumberFieldValueRule[] instance() {
            return new WholeNumberFieldValueRule[]{
                    new WholeNumberFieldValueRule.Builder<Byte>() {
                    }.build(),
                    new WholeNumberFieldValueRule.Builder<Short>() {
                    }.build(),
                    new WholeNumberFieldValueRule.Builder<Integer>() {
                    }.build(),
                    new WholeNumberFieldValueRule.Builder<Long>() {
                    }.build()};
        }
    },
    DATELOCALDATE_RULE(DateLocalDateFieldValueRule.class) {
        @Override
        public Class[] get() {
            return new Class[]{Date.class, LocalDate.class, LocalDateTime.class, LocalTime.class};
        }

        @Override
        public DateLocalDateFieldValueRule[] instance() {
            return new DateLocalDateFieldValueRule[]{
                    new DateLocalDateFieldValueRule.Builder<Date>() {
                    }.build(),
                    new DateLocalDateFieldValueRule.Builder<LocalDate>() {
                    }.build(),
                    new DateLocalDateFieldValueRule.Builder<LocalDateTime>() {
                    }.build(),
                    new DateLocalDateFieldValueRule.Builder<LocalTime>() {
                    }.build()
            };
        }
    };
    private Class clazz;

    RuleFieldType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public Class[] get() {
        return null;
    }

    public RuleAction[] instance() {
        return null;
    }

    public static RuleFieldType getField(Class clazz) {
        return Stream.of(values())
                .filter(it -> it.getClazz() == clazz)
                .findFirst()
                .orElse(null);
    }

}
