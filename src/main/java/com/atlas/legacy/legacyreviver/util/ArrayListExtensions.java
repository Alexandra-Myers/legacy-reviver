package com.atlas.legacy.legacyreviver.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListExtensions<T> extends ArrayList<T> {
    @SafeVarargs
    public final boolean addAll(T... c) {
        List<T> list = Arrays.asList(c);
        return super.addAll(list);
    }

    @SafeVarargs
    public final boolean addAll(int index, T... c) {
        List<T> list = Arrays.asList(c);
        return super.addAll(index, list);
    }
}
