package com.phasmidsoftware.dsaipg.util;

import com.phasmidsoftware.dsaipg.sort.*;
import com.phasmidsoftware.dsaipg.sort.elementary.HeapSort;
import com.phasmidsoftware.dsaipg.sort.linearithmic.MergeSort;
import com.phasmidsoftware.dsaipg.sort.linearithmic.QuickSort_DualPivot;

import java.util.Random;

public class SortExperiment {

    public static void main(String[] args) throws Exception {
        int[] sizes = {10000, 20000, 40000, 80000, 160000, 256000};
        String[] algorithms = {"mergesort", "quicksortDualPivot", "heapsort"};

        System.out.println("SortType,Size,Time(s),Comparisons,Copies,Hits");

        for (String algo : algorithms) {
            for (int n : sizes) {
                runExperiment(algo, n);
            }
        }
    }

    private static void runExperiment(String algorithm, int size) throws Exception {

        Config configInstr = Config.load(SortExperiment.class).copy("helper", "instrument", "true");
        Helper<Integer> helper1 = HelperFactory.create(algorithm, size, configInstr);
        Integer[] array1 = helper1.random(Integer.class, r -> r.nextInt(size));
        Sort<Integer> sorter1 = getSorter(algorithm, helper1);
        sorter1.sort(array1, 0, array1.length);

        long compares = helper1.getCompares();
        long copies = helper1.getCopies();
        long hits = helper1.getHits();

        Config configPlain = Config.load(SortExperiment.class).copy("helper", "instrument", "false");
        Helper<Integer> helper2 = HelperFactory.create(algorithm, size, configPlain);
        Integer[] array2 = helper2.random(Integer.class, r -> r.nextInt(size));
        Sort<Integer> sorter2 = getSorter(algorithm, helper2);

        Benchmark<Integer[]> benchmark = new Benchmark_Timer<>(
        	    "Timing sort for " + algorithm,
        	    null,
        	    xs -> sorter2.sort(xs, 0, xs.length),
        	    null
        	);

        	double time = benchmark.run(array2, 1);

        System.out.printf("%s,%d,%.6f,%d,%d,%d%n", algorithm, size, time, compares, copies, hits);
    }

    private static Sort<Integer> getSorter(String algorithm, Helper<Integer> helper) {
        return switch (algorithm) {
            case "mergesort" -> new MergeSort<>(helper);
            case "quicksortDualPivot" -> new QuickSort_DualPivot<>(helper);
            case "heapsort" -> new HeapSort<>(helper);
            default -> throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        };
    }
}
