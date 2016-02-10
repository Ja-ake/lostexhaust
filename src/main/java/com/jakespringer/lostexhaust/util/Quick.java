package com.jakespringer.lostexhaust.util;

import java.util.function.Supplier;

public class Quick {
    public static boolean notNull(Object... objects) {
        for (Object o : objects)
            if (o == null)
                return false;
        return true;
    }

    public static <T> T succOrNull(Supplier<T> supp) {
        try {
            return supp.get();
        } catch (Throwable t) {
            return null;
        }
    }
}
