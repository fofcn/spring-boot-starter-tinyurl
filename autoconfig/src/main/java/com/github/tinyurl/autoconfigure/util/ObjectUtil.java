package com.github.tinyurl.autoconfigure.util;

/**
 * 对象工具类
 *
 * @author errorfatal89@gmail.com
 * @date 2020/07/07
 */
public class ObjectUtil {

    private ObjectUtil() {}

    public static boolean isNull(Object o) {
        return o == null;
    }

    public static boolean isNotNull(Object o) {
        return o != null;
    }
}
