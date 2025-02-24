package com.phasmidsoftware.dsaipg.adt.threesum;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ThreeSumQuadraticWithCalipersTest {

    /**
     * Test case: Ensure `getTriples` finds all unique triples that sum to zero in a simple input.
     */
    @Test
    public void testGetTriplesSimple() {
        int[] input = {-1, 0, 1, 2, -1, -4};
        Arrays.sort(input); 
        ThreeSum threeSum = new ThreeSumQuadraticWithCalipers(input);
        Triple[] expected = {new Triple(-1, -1, 2), new Triple(-1, 0, 1)};
        Triple[] result = threeSum.getTriples();
        Arrays.sort(result);
        Arrays.sort(expected);
        System.out.println("Simple Test Result: " + Arrays.toString(result));
        assertArrayEquals(expected, result);
    }

    /**
     * Test case: Validate `getTriples` handles input with no triples summing to zero.
     */
    @Test
    public void testGetTriplesNoTriples() {
        int[] input = {1, 2, 3, 4, 5};
        Arrays.sort(input);
        ThreeSum threeSum = new ThreeSumQuadraticWithCalipers(input);
        Triple[] result = threeSum.getTriples();
        assertEquals(0, result.length);
    }

    /**
     * Test case: Check `getTriples` handles input containing duplicates properly and avoids duplicate triples.
     */
    @Test
    public void testGetTriplesWithDuplicates() {
        int[] input = {-1, -1, -1, 2, 2, 0, 0, 1, 1};
        Arrays.sort(input);
        ThreeSum threeSum = new ThreeSumQuadraticWithCalipers(input);
        Triple[] expected = {new Triple(-1, -1, 2), new Triple(-1, 0, 1)};
        Triple[] result = threeSum.getTriples();
        Arrays.sort(result);
        Arrays.sort(expected);
        System.out.println("Duplicates Test Result: " + Arrays.toString(result));
        assertArrayEquals(expected, result);
    }

    /**
     * Test case: Validate `getTriples` on an empty array.
     */
    @Test
    public void testGetTriplesEmptyInput() {
        int[] input = {};
        ThreeSum threeSum = new ThreeSumQuadraticWithCalipers(input);
        Triple[] result = threeSum.getTriples();
        assertEquals(0, result.length);
    }

    /**
     * Test case: Validate `getTriples` on an array with less than three elements.
     */
    @Test
    public void testGetTriplesInsufficientElements() {
        int[] input = {1, -1};
        ThreeSum threeSum = new ThreeSumQuadraticWithCalipers(input);
        Triple[] result = threeSum.getTriples();
        assertEquals(0, result.length);
    }

    /**
     * Test case: Validate `getTriples` handles input where all elements are zero.
     */
    @Test
    public void testGetTriplesAllZeros() {
        int[] input = {0, 0, 0, 0};
        Arrays.sort(input);
        ThreeSum threeSum = new ThreeSumQuadraticWithCalipers(input);
        Triple[] expected = {new Triple(0, 0, 0)};
        Triple[] result = threeSum.getTriples();
        Arrays.sort(result);
        System.out.println("All Zeros Test Result: " + Arrays.toString(result));
        assertArrayEquals(expected, result);
    }

    /**
     * Test case: Validate `getTriples` on a large input with only one valid triple.
     */
    @Test
    public void testGetTriplesSingleValidTriple() {
        int[] input = {-10, -7, -3, 0, 7, 10, 3};
        Arrays.sort(input);
        ThreeSum threeSum = new ThreeSumQuadraticWithCalipers(input);
        Triple[] result = threeSum.getTriples();
        
        System.out.println("Single Triple Test Result: " + Arrays.toString(result));

        Triple[] expected = {new Triple(-10, 0, 10)};
        Arrays.sort(result);
        Arrays.sort(expected);
        assertArrayEquals(expected, result);
    }
}