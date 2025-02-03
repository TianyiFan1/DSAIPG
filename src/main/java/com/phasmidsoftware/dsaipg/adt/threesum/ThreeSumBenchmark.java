/*
 * Copyright (c) 2024. Robin Hillyard
 */

package com.phasmidsoftware.dsaipg.adt.threesum;

import com.phasmidsoftware.dsaipg.util.Benchmark_Timer;
import com.phasmidsoftware.dsaipg.util.TimeLogger;
import com.phasmidsoftware.dsaipg.util.Utilities;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The ThreeSumBenchmark class provides a framework for evaluating and comparing
 * the performance of various implementations of the Three-Sum algorithm.
 * It benchmarks cubic, quadratic, and quadrithmic implementations and logs results for analysis.
 */
public class ThreeSumBenchmark {
    /**
     * Constructs a ThreeSumBenchmark instance.
     *
     * @param runs the number of benchmark runs.
     * @param n    the size of the input array.
     * @param m    the range of integers in the input array.
     */
    public ThreeSumBenchmark(int runs, int n, int m) {
        this.runs = runs;
        this.n = n;
        this.supplier = new Source(n, m).intsSupplier(10); 
    }

    /**
     * Runs the benchmarks for different ThreeSum implementations.
     */
    public void runBenchmarks() {
        System.out.println("ThreeSumBenchmark: N=" + n);

        int[] testArray = supplier.get();
        System.out.println("Generated array size: " + testArray.length);
        if (testArray.length != n) {
            throw new IllegalStateException("Error: Generated array size " + testArray.length + " is not equal to N=" + n);
        }

        benchmarkThreeSum("ThreeSumQuadratic", (xs) -> new ThreeSumQuadratic(xs).getTriples(), timeLoggersQuadratic);
        benchmarkThreeSum("ThreeSumQuadrithmic", (xs) -> new ThreeSumQuadrithmic(xs).getTriples(), timeLoggersQuadrithmic);
        benchmarkThreeSum("ThreeSumCubic", (xs) -> new ThreeSumCubic(xs).getTriples(), timeLoggersCubic);
    }

    /**
     * The main method runs the benchmarks for different problem sizes.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        new ThreeSumBenchmark(100, 250, 250).runBenchmarks();
        new ThreeSumBenchmark(50, 500, 500).runBenchmarks();
        new ThreeSumBenchmark(20, 1000, 1000).runBenchmarks();
        new ThreeSumBenchmark(10, 2000, 2000).runBenchmarks();
        new ThreeSumBenchmark(5, 4000, 4000).runBenchmarks();
        new ThreeSumBenchmark(3, 8000, 8000).runBenchmarks();
        new ThreeSumBenchmark(2, 16000, 16000).runBenchmarks();
    }

    /**
     * Benchmarks a ThreeSum implementation and logs the results.
     *
     * @param description the name of the algorithm.
     * @param function    the ThreeSum implementation.
     * @param timeLoggers the loggers for recording performance.
     */
    private void benchmarkThreeSum(final String description, final Consumer<int[]> function, final TimeLogger[] timeLoggers) {
    	if (description.equals("ThreeSumCubic") && n > 4000) return;

        int[] testArray = supplier.get();

        // 手动计时
        long start = System.nanoTime();
        function.accept(testArray); 
        long end = System.nanoTime();

        double timeInMs = (end - start) / 1e6; 
        System.out.println(description + " Execution time: " + timeInMs + " ms");

        for (TimeLogger logger : timeLoggers) {
            logger.log(description, timeInMs, n);
        }
    }

    /**
     * TimeLoggers for ThreeSumCubic.
     */
    private final static TimeLogger[] timeLoggersCubic = {
            new TimeLogger("Raw time per run (mSec): ", null),
            new TimeLogger("Normalized time per run (n^3): ", n -> 1.0 / 6 * n * n * n)
    };

    /**
     * TimeLoggers for ThreeSumQuadrithmic.
     */
    private final static TimeLogger[] timeLoggersQuadrithmic = {
            new TimeLogger("Raw time per run (mSec): ", null),
            new TimeLogger("Normalized time per run (n^2 log n): ", n -> n * n * Utilities.lg(n))
    };

    /**
     * TimeLoggers for ThreeSumQuadratic.
     */
    private final static TimeLogger[] timeLoggersQuadratic = {
            new TimeLogger("Raw time per run (mSec): ", null),
            new TimeLogger("Normalized time per run (n^2): ", n -> 1.0 / 2 * n * n)
    };

    private final int runs;
    private final Supplier<int[]> supplier;
    private final int n;
}
