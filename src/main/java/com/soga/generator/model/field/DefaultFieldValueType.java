package com.soga.generator.model.field;

import java.util.stream.Stream;

import static com.soga.generator.model.randoms.RandomDataUtils.*;

/**
 * @description: field值得应用类型
 * @author: lzr
 * @create: 2021-01-08 23:11
 **/
public enum DefaultFieldValueType {
    USER_NAME_TYPE("username", "userName") {
        @Override
        public String convert() {
            return chineseNameValue();
        }
    },
    FIELD_EMAIL_TYPE("email", "email") {
        @Override
        public String convert() {
            return emailValue();
        }
    },
    FIELD_PHONE_TYPE("phone", "phone") {
        @Override
        public String convert() {
            return phoneValue();
        }
    },
    FIELD_HTML_TYPE("page,html", "html") {
        @Override
        public String convert() {
            return htmlValue();
        }
    },
    FIELD_PHOTO_TYPE("photo,picture,portrait", "photo") {
        @Override
        public String convert() {
            return imgValue();
        }
    },
    FIELD_FLAG_TYPE("flag", "flag") {
        @Override
        public Integer convert() {
            return flagValue();
        }
    },
    FIELD_SEX_TYPE("sex", "sex") {
        @Override
        public Short convert() {
            return (short)flagValue();
        }
    },
    FIELD_VERSION_FLAG_TYPE("version", "version flag") {
        @Override
        public Integer convert() {
            return versionValue();
        }
    },
    IDCARD_TYPE("idcard", "idCard") {
        @Override
        public String convert() {
            return IDCardAddressValue().getIdCard();
        }
    },
    IPADDRESS_TYPE("ipaddress", "ip") {
        @Override
        public String convert() {
            return ipValue(false);
        }
    },
    LATITUDE_TYPE("latitude", "latitude") {
        @Override
        public Double convert() {
            return latLongValue().getLati();
        }
    },
    LONGITUDE_TYPE("longitude", "longitude") {
        @Override
        public Double convert() {
            return latLongValue().getLongi();
        }
    };
    private String code;//类型值
    private String desc;//描述

    DefaultFieldValueType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    boolean containCode(String fieldName) {
        return Stream.of(code.split(","))
                .anyMatch(c -> fieldName.toLowerCase().contains(c));
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public <T> T convert() {
        return null;
    }

    public static DefaultFieldValueType getField(String fieldName) {
        return Stream.of(values())
                .filter(it -> it.containCode(fieldName))
                .findFirst()
                .orElse(null);
    }

    public static String getDesc(String code) {
        return Stream.of(values())
                .filter(it -> it.getCode().contains(code))
                .findFirst()
                .map(DefaultFieldValueType::getDesc)
                .orElse("");
    }
}
