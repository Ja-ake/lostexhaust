package com.jakespringer.lostexhaust.util;

public class Tup<L, R> {
    public L l;
    public R r;

    public Tup() {
        l = null;
        r = null;
    }

    public Tup(L _l, R _r) {
        l = _l;
        r = _r;
    }
}
