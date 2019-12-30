package com.lhy.web.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 * @author liuhaiyan
 * @date 2019-12-25 19:44
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     * */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
