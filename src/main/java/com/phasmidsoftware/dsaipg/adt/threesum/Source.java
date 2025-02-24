/*
 * Copyright (c) 2024. Robin Hillyard
 */

package com.phasmidsoftware.dsaipg.adt.threesum;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

/**
 * The Source class provides a source of entropy and a set of utilities
 * for generating arrays of integers based on specific constraints,
 * and designed for use in scenarios like testing algorithms or data structures
 * that require randomly generated inputs.
 */
class Source {
    /**
     * Primary constructor of a Source instance with the specified values for N, M, and a Random instance.
     *
     * @param N      the number of integers to generate.
     * @param M      the range of integers, where each integer is in the range -M through M-1.
     * @param random the Random instance used for generating random numbers.
     */
    public Source(int N, int M, Random random) {
        n = N;
        m = M;
        this.random = random;
    }

    /**
     * Secondary constructor of a Source instance with the specified number of integers, the range of integers,
     * and a seed value for the random number generator.
     *
     * @param N    the number of integers to generate.
     * @param M    the range of integers, where each integer is in the range -M through M-1.
     * @param seed the seed value to initialize the random number generator.
     */
    public Source(int N, int M, long seed) {
        this(N, M, new Random(seed));
    }

    /**
     * Secondary constructor of a Source instance with the specified number of integers and the range of integers.
     * A default Random instance will be used for generating random numbers.
     *
     * @param N the number of integers to generate.
     * @param M the range of integers, where each integer is in the range -M through M-1.
     */
    public Source(int N, int M) {
        this(N, M, new Random());
    }

    /**
     * Generates a {@code Supplier} that provides an array of {@code n} integers.
     * The output array contains distinct and sorted random integers.
     *
     * @param safetyFactor The multiplier used to generate an initial larger set of random integers.
     * @return A {@code Supplier<int[]>} providing an array of {@code n} distinct, ordered integers.
     */
    public Supplier<int[]> intsSupplier(int safetyFactor) {
        return () -> {
            int[] ints = new int[Math.max(n * safetyFactor, n + 100)]; // 确保数组足够大，避免 distinct 数量不足
            for (int i = 0; i < ints.length; i++) {
                ints[i] = random.nextInt(2 * m) - m; // 生成范围 [-M, M]
            }

            Arrays.sort(ints);
            int[] distinct = Arrays.stream(ints).distinct().toArray(); // 去重

            // 确保返回的数组大小等于 n
            if (distinct.length >= n) {
                return Arrays.copyOf(distinct, n);
            } else {
                System.out.println("Warning: Generated array size is less than expected! Expanding...");
                return expandArray(distinct, n);
            }
        };
    }

    /**
     * If the distinct array size is too small, we regenerate extra elements to reach N.
     *
     * @param smallArray The original small distinct array.
     * @param targetSize The required target size.
     * @return A new array of length {@code targetSize}.
     */
    private int[] expandArray(int[] smallArray, int targetSize) {
        int[] result = Arrays.copyOf(smallArray, targetSize);
        int index = smallArray.length;
        while (index < targetSize) {
            int newValue = random.nextInt(2 * m) - m; 
            if (Arrays.binarySearch(smallArray, newValue) < 0) { 
                result[index++] = newValue;
            }
        }
        Arrays.sort(result); 
        return result;
    }

    private final int n;
    private final int m;
    private final Random random;
}
