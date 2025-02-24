package com.phasmidsoftware.dsaipg.util;

import java.util.Random;
import java.util.function.Supplier;

public class Source {
    private final int n;
    private final int m;
    private final Random random;

    public Source(int n, int m) {
        this.n = n;
        this.m = m;
        this.random = new Random();
    }

    public Supplier<int[]> intsSupplier(int seed) {
        return () -> {
            int[] array = new int[n];
            random.setSeed(seed);
            for (int i = 0; i < n; i++) {
                array[i] = random.nextInt(2 * m) - m; // 生成范围 [-m, m-1]
            }
            return array;
        };
    }
}
