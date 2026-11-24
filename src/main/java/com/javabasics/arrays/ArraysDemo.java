package com.javabasics.arrays;

import java.util.Arrays;

/**
 * ============================================================================
 *  05 - ARRAYS
 * ============================================================================
 *  WHAT:  A fixed-size container for values of the SAME type.
 *  WHY:   Most data is a list - marks, prices, log lines, pixels.
 *  HOW:   type[] name = new type[size]; or {value, value, ...}
 *  GOTCHA: An array's size is fixed after creation, and indexes start at 0.
 *          Reading length as an index throws ArrayIndexOutOfBoundsException.
 * ============================================================================
 */
public final class ArraysDemo {

    private ArraysDemo() {
    }

    public static void run() {
        // ── 1. Creating arrays ───────────────────────────────────────────
        int[] marks = new int[5];              // five zeros
        int[] scores = {88, 92, 75, 61, 99};   // literal form
        String[] names = {"Sumit", "Riya", "Aman"};

        System.out.println("1) marks (defaults): " + Arrays.toString(marks));
        System.out.println("   scores: " + Arrays.toString(scores));
        System.out.println("   length: " + scores.length);
        System.out.println("   first: " + scores[0] + ", last: " + scores[scores.length - 1]);

        // ── 2. Filling and updating ──────────────────────────────────────
        marks[0] = 70;
        marks[1] = 80;
        marks[4] = 90;
        System.out.println("\n2) after updates: " + Arrays.toString(marks));

        Arrays.fill(marks, 50);
        System.out.println("   Arrays.fill(marks, 50): " + Arrays.toString(marks));

        // ── 3. Iterating ─────────────────────────────────────────────────
        System.out.println("\n3) iteration:");
        for (int i = 0; i < scores.length; i++) {
            System.out.println("   index " + i + " = " + scores[i]);
        }

        int total = 0;
        for (int score : scores) {
            total += score;
        }
        System.out.println("   total = " + total + ", average = " + (total / scores.length));

        // ── 4. Useful Arrays helpers ─────────────────────────────────────
        int[] unsorted = {5, 2, 9, 1, 7};
        Arrays.sort(unsorted);
        System.out.println("\n4) Arrays.sort: " + Arrays.toString(unsorted));

        int[] copy = Arrays.copyOf(unsorted, unsorted.length);
        System.out.println("   copyOf: " + Arrays.toString(copy));
        System.out.println("   equals: " + Arrays.equals(unsorted, copy));
        System.out.println("   binarySearch(7): " + Arrays.binarySearch(unsorted, 7));

        // ── 5. Two-dimensional arrays ────────────────────────────────────
        int[][] grid = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9},
        };
        System.out.println("\n5) 2D array:");
        for (int[] row : grid) {
            System.out.println("   " + Arrays.toString(row));
        }
        System.out.println("   grid[1][2] = " + grid[1][2]);

        // A jagged array: rows may have different lengths.
        int[][] jagged = new int[3][];
        jagged[0] = new int[]{1};
        jagged[1] = new int[]{2, 3};
        jagged[2] = new int[]{4, 5, 6};
        System.out.println("   jagged lengths: "
                + jagged[0].length + ", " + jagged[1].length + ", " + jagged[2].length);

        // ── 6. Array of objects ──────────────────────────────────────────
        Student[] students = {
                new Student("Sumit", 88),
                new Student("Riya", 92),
        };
        System.out.println("\n6) object array:");
        for (Student student : students) {
            System.out.println("   " + student);
        }

        // ── 7. The bounds gotcha ─────────────────────────────────────────
        System.out.println("\n7) valid indexes: 0.." + (scores.length - 1));
        try {
            int ignored = scores[scores.length]; // one past the end
            System.out.println("   never runs: " + ignored);
        } catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("   scores[length] throws ArrayIndexOutOfBoundsException");
        }

        // ── 8. One-line summary ──────────────────────────────────────────
        // Arrays are fixed-size and zero-indexed. Use Arrays.toString to print,
        // Arrays.sort to sort, and an enhanced for loop to visit every value.
    }

    private record Student(String name, int score) {
    }
}
