package com.phasmidsoftware.dsaipg.sort;
import com.phasmidsoftware.dsaipg.sort.Helper;
import com.phasmidsoftware.dsaipg.sort.SimpleHelper;
import com.phasmidsoftware.dsaipg.sort.elementary.InsertionSortComparator;
import com.phasmidsoftware.dsaipg.util.Benchmark_Timer;

import java.util.Random;
import java.util.function.Supplier;

public class InsertionSortBenchmark {
    public static void main(String[] args) {
        int[] sizes = {1000, 2000, 4000, 8000, 16000};

        Helper<Integer> helper = new SimpleHelper<>("Insertion Sort", Integer::compareTo);
        InsertionSortComparator<Integer> sorter = new InsertionSortComparator<>(helper);
        Benchmark_Timer<Integer[]> benchmark = new Benchmark_Timer<>("Insertion Sort", sorter::mutatingSort);

        System.out.println("Running Insertion Sort Benchmarks...\n");

        for (int n : sizes) {
            System.out.printf("\nArray Size = %d\n", n);
            runBenchmark("Random", benchmark, () -> generateRandomArray(n));
            runBenchmark("Sorted", benchmark, () -> generateSortedArray(n));
            runBenchmark("Partially Sorted", benchmark, () -> generatePartiallySortedArray(n));
            runBenchmark("Reversed", benchmark, () -> generateReversedArray(n));
        }
    }

    static void runBenchmark(String type, Benchmark_Timer<Integer[]> benchmark, Supplier<Integer[]> supplier) {
        double time = benchmark.runFromSupplier(supplier, 10);
        System.out.printf("%s Array | Time: %.5f ms\n", type, time);
    }

    static Integer[] generateRandomArray(int n) {
        Random rand = new Random();
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(10000);
        return arr;
    }

    static Integer[] generateSortedArray(int n) {
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        return arr;
    }

    static Integer[] generatePartiallySortedArray(int n) {
        Integer[] arr = generateSortedArray(n);
        Random rand = new Random();
        for (int i = 0; i < n / 10; i++) {
            int index = rand.nextInt(n);
            arr[index] = rand.nextInt(10000); 
        }
        return arr;
    }

    static Integer[] generateReversedArray(int n) {
        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) arr[i] = n - i;
        return arr;
    }
}