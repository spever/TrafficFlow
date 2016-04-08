package com.subzero.trafficflow.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by li on 2015/9/12.
 */
public class FieldUtils {

    /**
     * 缓存
     */
    private static final Map<Class<?>, Field[]> cache = new HashMap<>();

    /**
     * 获取字节码文件所有非静态字段
     *
     * @param clazz 字节码文件
     * @return 字段数组
     */
    public static Field[] getFields(Class<?> clazz) {
        if (cache.containsKey(clazz))
            return cache.get(clazz);
        if (clazz == null)
            return new Field[0];
        String className = clazz.getName();
        if (className.startsWith("android.") || className.startsWith("java."))
            return new Field[0];

        List<Field> fieldsList = new ArrayList<>();
        Field[] parentFields = getFields(clazz.getSuperclass());
        Collections.addAll(fieldsList, parentFields);

        Field[] selfFields = clazz.getDeclaredFields();
        for (Field field : selfFields) {
            int modifier = field.getModifiers();
            if (!Modifier.isStatic(modifier))
                fieldsList.add(field);
        }

        Field[] fields = fieldsList.toArray(new Field[fieldsList.size()]);
        cache.put(clazz, fields);
        return fields;
    }

}
