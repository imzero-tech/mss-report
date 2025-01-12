package com.example.mss.application.util;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * packageName  : com.example.mss.application.util
 * fileName     : ClassUtil
 * auther       : imzero-tech
 * date         : 2025. 1. 12.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 12.      imzero-tech             최초생성
 */
public class MssClassUtil {
    public static void copyNonNullProperties(Object source, Object target) {
        Arrays.stream(BeanUtils.getPropertyDescriptors(source.getClass()))
                .forEach(propertyDescriptor -> {
                    try {
                        Method readMethod = propertyDescriptor.getReadMethod();
                        if (readMethod != null) {
                            Object value = readMethod.invoke(source);
                            if (value != null) {
                                Method writeMethod = propertyDescriptor.getWriteMethod();
                                if (writeMethod != null) {
                                    writeMethod.invoke(target, value);
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("copyNonNullProperties error!!" + e);
                    }
                });
    }
}
