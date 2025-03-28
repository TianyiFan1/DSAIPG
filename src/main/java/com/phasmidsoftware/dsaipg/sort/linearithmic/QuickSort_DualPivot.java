/*
 * Copyright (c) 2024. Robin Hillyard
 */

package com.phasmidsoftware.dsaipg.sort.linearithmic;

import com.phasmidsoftware.dsaipg.sort.Helper;
import com.phasmidsoftware.dsaipg.sort.SortException;
import com.phasmidsoftware.dsaipg.util.Config;

import java.util.ArrayList;
import java.util.List;

import static com.phasmidsoftware.dsaipg.sort.InstrumentedComparatorHelper.getRunsConfig;

public class QuickSort_DualPivot<X extends Comparable<X>> extends QuickSort<X> {

    public static final String DESCRIPTION = "QuickSort dual pivot";

    public QuickSort_DualPivot(String description, int N, int nRuns, Config config) {
        super(description, N, nRuns, config);
        setPartitioner(createPartitioner());
    }

    public QuickSort_DualPivot(Helper<X> helper) {
        super(helper);
        setPartitioner(createPartitioner());
    }

    public QuickSort_DualPivot(int N, int nRuns, Config config) {
        this(DESCRIPTION, N, nRuns, config);
    }

    public QuickSort_DualPivot(int N, Config config) {
        this(DESCRIPTION, N, getRunsConfig(config), config);
    }

    public Partitioner<X> createPartitioner() {
        return new Partitioner_DualPivot(getHelper());
    }

    public class Partitioner_DualPivot implements Partitioner<X> {

        public Partitioner_DualPivot(Helper<X> helper) {
            this.helper = helper;
        }

        public List<Partition<X>> partition(Partition<X> partition) {
            int from = partition.from;
            int to = partition.to;
            int n = to - from;

            X[] xs = partition.xs;

            if (n < 3) {
                if (n == 2 && helper.compare(xs[from], xs[to - 1]) > 0) {
                    helper.swap(xs, from, to - 1);
                }
                return new ArrayList<>();
            }

            int p1 = from;
            int p2 = to - 1;

            X v1 = xs[p1];
            X v2 = xs[p2];

            if (helper.compare(v1, v2) > 0) {
                helper.swap(xs, p1, p2);
                v1 = xs[p1];
                v2 = xs[p2];
            }

            int lt = p1 + 1;
            int gt = p2 - 1;
            int i = lt;

            while (i <= gt) {
                X x = helper.get(xs, i);
                if (helper.compare(x, v1) < 0) {
                    helper.swap(xs, i++, lt++);
                } else if (helper.compare(x, v2) > 0) {
                    helper.swap(xs, i, gt--);
                } else {
                    i++;
                }
            }

            helper.swap(xs, p1, --lt);
            helper.swap(xs, p2, ++gt);

            List<Partition<X>> partitions = new ArrayList<>();

            if (from < lt) partitions.add(new Partition<>(xs, from, lt));
            if (lt + 1 < gt) partitions.add(new Partition<>(xs, lt + 1, gt));
            if (gt + 1 < to) partitions.add(new Partition<>(xs, gt + 1, to));

            return partitions;
        }


        private void swap(X[] ys, int i, int j) {
            X temp = ys[i];
            ys[i] = ys[j];
            ys[j] = temp;
        }

        private final Helper<X> helper;
    }
}
