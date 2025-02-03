package com.phasmidsoftware.dsaipg.adt.threesum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the ThreeSum problem using a quadratic approach (O(N^2)).
 * The algorithm iterates over all possible middle elements and applies the two-pointer technique
 * to find pairs that sum to the negative of the middle element.
 * <p>
 * NOTE: The input array provided in the constructor MUST be sorted before using this class.
 */
public class ThreeSumQuadratic implements ThreeSum {
    /**
     * Construct a ThreeSumQuadratic on a.
     *
     * @param a a sorted array.
     */
    public ThreeSumQuadratic(int[] a) {
        this.a = a;
        length = a.length;
    }

    /**
     * Retrieves an array of unique Triples. Each Triple represents a unique combination of three integers from
     * the source array that sum to zero.
     *
     * @return an array of distinct Triples, sorted in natural order, where each Triple satisfies the condition that
     * the sum of its three integers is zero.
     */
    public Triple[] getTriples() {
    	long startTime = System.nanoTime();
        List<Triple> triples = new ArrayList<>();
        for (int j = 1; j < length - 1; j++) {
            triples.addAll(getTriples(j));
        }
        Collections.sort(triples);
        return triples.stream().distinct().toArray(Triple[]::new);
    }

    /**
     * Uses the two-pointer technique to find unique triples such that a[i] + a[j] + a[k] = 0.
     *
     * @param j the index of the middle value.
     * @return a list of Triples where a[i] + a[j] + a[k] = 0.
     */
    public List<Triple> getTriples(int j) {
        List<Triple> triples = new ArrayList<>();
        int left = 0;      // 左指针
        int right = length - 1; // 右指针

        while (left < j && right > j) {
            long sum = (long) a[left] + a[j] + a[right]; 

            if (sum == 0) {
                triples.add(new Triple(a[left], a[j], a[right]));

                left++;
                while (left < j && a[left] == a[left - 1]) {
                    left++;
                }

                right--;
                while (right > j && a[right] == a[right + 1]) {
                    right--;
                }
            } else if (sum < 0) {
                left++; 
            } else {
                right--; 
            }
        }
        return triples;
    }

    private final int[] a;
    private final int length;
}
