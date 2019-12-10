package com.fj.common.util;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

public class Safes {

    public static <T> Collection<T> of(Collection<T> col) {
        if (CollectionUtils.isEmpty(col)) {
            return Lists.newArrayList();
        }
        return col;
    }

    public static <K, V> Map<K, V> of(Map<K, V> map) {
        if (map == null || map.size() == 0) {
            return Maps.newHashMap();
        }
        return map;
    }
}
