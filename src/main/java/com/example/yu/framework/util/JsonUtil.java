package com.example.yu.framework.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * JSON 工具类
 *
 * @author wukong
 * @since 2017-07-09.
 */
public final class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Convert POJO to JSON
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error("convert POJO to JSON error", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * convert JSON to POJO
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJSON(String json, Class<T> clazz) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("convert JSON to POJO error", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
