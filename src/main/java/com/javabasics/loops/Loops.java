package com.javabasics.loops;

import java.util.List;

/**
 * ============================================================================
 *  03 - LOOPS
 * ============================================================================
 *  WHAT:  A way to repeat a block of code.
 *  WHY:   Doing the same thing 100 times by hand is impossible.
 *  HOW:   for (known count), while/do-while (unknown count),
 *         enhanced for (arrays and collections).
 *  GOTCHA: `for (int i = 0; i <= array.length; i++)` goes one past the end
 *          and throws ArrayIndexOutOfBoundsException.
 * ============================================================================
 */
public final class Loops {

    private Loops() {
    }

    public static void run() {
        // ── 1. Classic for loop ──────────────────────────────────────────
        System.out.println("1) classic for (1 to 5):");
        for (int i = 1; i <= 5; i++) {
            System.out.println("   i = " + i);
        }

        // Counting backwards
        System.out.println("   countdown:");
        for (int i = 3; i > 0; i--) {
            System.out.println("   " + i);
        }

        // ── 2. while loop ────────────────────────────────────────────────
        int balance = 1000;
        int months = 0;
        while (balance < 1500) {
            balance += 200;
            months++;
        }
        System.out.println("\n2) while: needed " + months + " months to reach " + balance);

        // ⚠️ Make sure the condition can become false, or you get an
        // infinite loop. The loop below uses break as a safety valve.
        int attempts = 0;
        while (true) {
            attempts++;
            if (attempts == 3) {
                break;
            }
        }
        System.out.println("   stopped after " + attempts + " attempts");

        // ── 3. do-while ──────────────────────────────────────────────────
        // Always runs at least once, because the check happens afterwards.
        int counter = 0;
        do {
            counter++;
            System.out.println("3) do-while run #" + counter);
        } while (counter < 3);

        // ── 4. Enhanced for (for-each) ───────────────────────────────────
        int[] marks = {88, 92, 75};
        System.out.println("\n4) enhanced for (array):");
        for (int mark : marks) {
            System.out.println("   mark = " + mark);
        }

        List<String> fruits = List.of("apple", "banana", "cherry");
        System.out.println("   enhanced for (list):");
        for (String fruit : fruits) {
            System.out.println("   " + fruit);
        }

        // for-each gives values, not indexes. Use a classic for when you
        // need the index as well.

        // ── 5. break and continue ────────────────────────────────────────
        System.out.println("\n5) break / continue:");
        for (int i = 1; i <= 10; i++) {
            if (i == 3) {
                continue;        // skip the rest of this iteration
            }
            if (i == 7) {
                break;           // leave the loop completely
            }
            System.out.println("   " + i);
        }

        // ── 6. Labeled loops ─────────────────────────────────────────────
        // A label lets break/continue target an OUTER loop.
        System.out.println("\n6) labeled loop:");
        outer:
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                if (row == 2 && col == 2) {
                    System.out.println("   found 2,2 - leaving both loops");
                    break outer;
                }
                System.out.println("   row=" + row + ", col=" + col);
            }
        }

        // ── 7. Nested loops ──────────────────────────────────────────────
        System.out.println("\n7) nested loops (multiplication table):");
        for (int row = 1; row <= 3; row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 1; col <= 3; col++) {
                line.append(String.format("%4d", row * col));
            }
            System.out.println("   " + line);
        }

        // ── 8. The off-by-one gotcha ─────────────────────────────────────
        System.out.println("\n8) array length = " + marks.length
                + ", valid indexes are 0.." + (marks.length - 1));

        // ── 9. One-line summary ──────────────────────────────────────────
        // for = known count, while = unknown count, do-while = at least once,
        // for-each = values of an array/collection. break exits, continue skips.
    }
}
