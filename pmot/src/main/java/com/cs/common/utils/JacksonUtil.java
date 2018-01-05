package com.cs.common.utils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Jackson序列化工具类
 * 
 */
public final class JacksonUtil {
    private static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);
	
    private static final ObjectMapper OBJECT_MAPPER;
    private static final TypeFactory TYPE_FACTORY;
   
    /**
     * 个别参数对应不上 不报错
     * null转 空串
     */
    private static final ObjectMapper OBJECT_MAPPER_NO_MATCH;

    /**
     * 加载Jackson对象映射器
     */
    static {
        OBJECT_MAPPER = new ObjectMapper();
        TYPE_FACTORY = OBJECT_MAPPER.getTypeFactory();
        
        OBJECT_MAPPER_NO_MATCH = new ObjectMapper();
        OBJECT_MAPPER_NO_MATCH.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER_NO_MATCH.setSerializationInclusion(Include.NON_NULL);  
    }

    private JacksonUtil() {
    };

    /**
     * json转Object
     * 
     * @param json
     *            json字符串
     * @param clazz
     *            Object对象的Class字节码
     * @return T Object对象
     */
    public static <T extends Serializable> T jsonToObject(String json, Class<T> clazz) {
        /*try {
            if (StringUtils.isBlank(json))
                throw new IllegalArgumentException("json参数不能为空！");
            if (clazz == null)
                throw new IllegalArgumentException("Object对象的Class字节码参数不能为空！");

            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
        try {
            if (StringUtils.isBlank(json))
                throw new IllegalArgumentException("json参数不能为空！");
            if (clazz == null)
                throw new IllegalArgumentException("Object对象的Class字节码参数不能为空！");

            return OBJECT_MAPPER_NO_MATCH.readValue(json, clazz);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("json转对象异常", e);
            return null;
        }
    }

    /**
     * json转List
     * 
     * @param json
     *            json字符串
     * @param clazz
     *            List中泛型对应的Class字节码
     * @return List<T> List对象
     */
    public static <T extends Serializable> List<T> jsonToList(String json, Class<T> clazz) {
        JavaType javaType = null;
        try {
            if (StringUtils.isBlank(json))
                throw new IllegalArgumentException("json参数不能为空！");
            if (clazz == null)
                throw new IllegalArgumentException("List中泛型对应的Class字节码！");

            javaType = TYPE_FACTORY.constructCollectionType(List.class, clazz);
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json转Map
     * 
     * @param json
     *            json字符串
     * @return Map<String, Object> Map对象 注：Map的key为String类型，Map中的Value为顶级父类Object， 对于Map中包含的集合类型，则转为String处理。
     */
    public static Map<String, Object> jsonToMap(String json) {
        JavaType javaType = null;
        try {
            if (StringUtils.isBlank(json))
                throw new IllegalArgumentException("json参数不能为空！");

            javaType = TYPE_FACTORY.constructMapType(Map.class, String.class, Object.class);
            Map<String, Object> resultMap = OBJECT_MAPPER.readValue(json, javaType);

            for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Collection || value instanceof Map) {
                    String valueJson = toJson(value);
                    entry.setValue(valueJson);
                }
            }

            return resultMap;
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("json转Map异常", e);
            return null;
        }
    }

    /**
     * json转Map
     * 
     * @param json
     *            json字符串
     * @param clazz
     *            Map Value对应的Class字节码
     * @return Map<T> Map对象 注：Map的key为String类型，Map中的Value为固定的类型，并且非顶级父类Object
     */
    public static <T extends Serializable> Map<String, T> jsonToMap(String json, Class<T> clazz) {
        JavaType javaType = null;
        try {
            if (StringUtils.isBlank(json))
                throw new IllegalArgumentException("json参数不能为空！");
            if (clazz == null)
                throw new IllegalArgumentException("Map中泛型对应的Class字节码参数不能为空！");

            javaType = TYPE_FACTORY.constructMapType(Map.class, String.class, clazz);
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json转数组
     * 
     * @param json
     *            json字符串
     * @param clazz
     *            数组类型对应的Class字节码
     * @return T[] 数组对象
     */
    public static <T extends Serializable> T[] jsonToArray(String json, Class<T> clazz) {
        JavaType javaType = null;
        try {
            if (StringUtils.isBlank(json))
                throw new IllegalArgumentException("json参数不能为空！");
            if (clazz == null)
                throw new IllegalArgumentException("数组类型对应的Class字节码参数不能为空！");

            javaType = TYPE_FACTORY.constructArrayType(clazz);
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象序列化为json字符串
     * 
     * @param obj
     *            对象
     * @return String json字符串
     */
    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("对象转json异常", e);
            return "toJson解析错误："+e.getStackTrace();
        }
    }
    
    /**
     * 将对象序列化为json字符串
     * 
     * @param obj
     *            对象
     * @return String json字符串
     */
    public static String toJsonNoMatch(Object obj) {
        try {
            return OBJECT_MAPPER_NO_MATCH.writeValueAsString(obj);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("对象转json异常", e);
            return null;
        }
    }
    
    /**
     * json转Object
     * 
     * @param json
     *            json字符串
     * @param clazz
     *            Object对象的Class字节码
     * @return T Object对象
     */
    public static <T extends Serializable> T jsonToObjectNoMatch(String json, Class<T> clazz) {
        try {
            if (StringUtils.isBlank(json))
                throw new IllegalArgumentException("json参数不能为空！");
            if (clazz == null)
                throw new IllegalArgumentException("Object对象的Class字节码参数不能为空！");

            return OBJECT_MAPPER_NO_MATCH.readValue(json, clazz);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("对象转json异常", e);
            return null;
        }
    }

    /**
     * 获取泛型的Collection Type
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return OBJECT_MAPPER_NO_MATCH.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * json转Object
     *
     * @param json
     *            json字符串
     * @param clazz
     *            Object对象的Class字节码
     * @return T Object对象
     */
    public static <T extends Serializable> T jsonToObject(String json, Class<T> clazz, Class<?> genericClazz) {
        /*try {
            if (StringUtils.isBlank(json))
                throw new IllegalArgumentException("json参数不能为空！");
            if (clazz == null)
                throw new IllegalArgumentException("Object对象的Class字节码参数不能为空！");

            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }*/
        try {
            if (StringUtils.isBlank(json))
                throw new IllegalArgumentException("json参数不能为空！");
            if (clazz == null)
                throw new IllegalArgumentException("Object对象的Class字节码参数不能为空！");

            JavaType javaType = JacksonUtil.getCollectionType(clazz,genericClazz);
            return OBJECT_MAPPER_NO_MATCH.readValue(json, javaType);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("json转对象异常", e);
            return null;
        }
    }
}