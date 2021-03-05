package com.soga.generator.model.utils;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class JsonUtils {
    /**
     * 日志输出
     */
    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper mapper;
    private final static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private final static String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    static {
        mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //Date序列化和反序列化
        javaTimeModule.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
                String formattedDate = formatter.format(date);
                jsonGenerator.writeString(formattedDate);
            }
        });
        javaTimeModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
                String date = jsonParser.getText();
                try {
                    return format.parse(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //设置json LocalDateTime日期格式
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));
        mapper.registerModule(javaTimeModule);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T fromJson(String strJson, Class<T> className) throws RuntimeException {
        try {
            return mapper.readValue(strJson, className);
        } catch (JsonParseException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("json error");
        } catch (JsonMappingException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("json error");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("json error");
        }
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> toList(String strJson, Class<T> className) {
        try {
            return mapper.readValue(strJson, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
    public static <T> T readFormFile(String path, Class<T> clazz) {
        try {
            File file=new File(path);
            if(!file.exists()||!file.isFile()){
                throw new FileNotFoundException("file not found");
            }
            return mapper.readValue(file, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}