package com.cs.common.utils;

import com.cs.mvc.mybatis.enumhandler.Identifiable;




/**
 * 操作枚举的工具类
 * @author vicent
 */
public class EnumUtils {

    /**
     * 根据一个索引得到某个枚举类的枚举值
     * @param <I>
     * @param <K>
     * @param type
     * 	枚举类的Class
     * @param index
     * 	索引
     * @return
     * @throws AssertionError
     * 	若提供的索引在枚举中没有对映的映射,抛出此断言错误
     */
    public static <I extends Identifiable<K>, K> I getEnum(Class<I> type , K index) {
        I[] types = type.getEnumConstants();
        for (I t : types) {
            if (t.getId().equals(index) ) {
                return t;
            }
        }
        throw new AssertionError("不能够映射:" + index + "到枚举" + type.getSimpleName());
    }

}
