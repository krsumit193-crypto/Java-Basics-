package com.javabasics.basics;

/**
 * ============================================================================
 *  01 - OPERATORS
 * ============================================================================
 *  WHAT:  Symbols that compute a value (+, -, ==, &&, ?:, ...).
 *  WHY:   They are the verbs of programming - compare, combine, decide.
 *  HOW:   Learn them in groups: arithmetic, assignment, comparison, logical.
 *  GOTCHA: Integer division drops the remainder; `==` compares String
 *          references, not their text.
 * ============================================================================
 */
public final class Operators {

    private Operators() {
    }

    public static void run() {
        int a = 10;
        int b = 3;

        // ── 1. Arithmetic ────────────────────────────────────────────────
        System.out.println("1) arithmetic:");
        System.out.println("   a + b = " + (a + b));
        System.out.println("   a - b = " + (a - b));
        System.out.println("   a * b = " + (a * b));
        System.out.println("   a / b = " + (a / b) + "  <-- integer division!");
        System.out.println("   a % b = " + (a % b) + "  (remainder)");
        System.out.println("   (double) a / b = " + ((double) a / b));

        // ── 2. Assignment operators ──────────────────────────────────────
        int x = 5;
        x += 2;   // 7
        x -= 1;   // 6
        x *= 3;   // 18
        x /= 2;   // 9
        x %= 4;   // 1
        System.out.println("\n2) after compound assignments, x = " + x);

        // ── 3. Increment / decrement ─────────────────────────────────────
        int count = 0;
        System.out.println("\n3) count++ = " + (count++));  // prints 0, then count = 1
        System.out.println("   ++count = " + (++count));    // count = 2, prints 2
        System.out.println("   count-- = " + (count--));    // prints 2, then count = 1
        System.out.println("   final count = " + count);

        // ── 4. Comparison ────────────────────────────────────────────────
        System.out.println("\n4) comparison:");
        System.out.println("   10 > 3   : " + (10 > 3));
        System.out.println("   10 >= 10 : " + (10 >= 10));
        System.out.println("   10 == 10 : " + (10 == 10));
        System.out.println("   10 != 3  : " + (10 != 3));

        // ── 5. The String gotcha: == vs equals() ─────────────────────────
        String first = "hello";
        String second = "hello";
        String third = new String("hello");

        System.out.println("\n5) String comparison:");
        System.out.println("   first == second       : " + (first == second) + "  (same pooled literal)");
        System.out.println("   first == third        : " + (first == third) + "  (different objects)");
        System.out.println("   first.equals(third)   : " + first.equals(third) + "  <-- use this");
        System.out.println("   first.equalsIgnoreCase(\"HELLO\") : " + first.equalsIgnoreCase("HELLO"));

        // ── 6. Logical operators (short-circuit) ─────────────────────────
        boolean loggedIn = true;
        boolean isAdmin = false;

        System.out.println("\n6) logical:");
        System.out.println("   loggedIn && isAdmin : " + (loggedIn && isAdmin));
        System.out.println("   loggedIn || isAdmin : " + (loggedIn || isAdmin));
        System.out.println("   !loggedIn           : " + (!loggedIn));

        // `&&` stops at the first false; `||` stops at the first true.
        System.out.println("   short-circuit demo  : " + (isAdmin && sideEffect()));

        // ── 7. Ternary operator ──────────────────────────────────────────
        int marks = 78;
        String result = marks >= 40 ? "PASS" : "FAIL";
        System.out.println("\n7) ternary: " + marks + " -> " + result);

        // ── 8. Bitwise operators (rarely needed, good to recognise) ──────
        int p = 5;   // 0101
        int q = 3;   // 0011
        System.out.println("\n8) bitwise:");
        System.out.println("   p & q  = " + (p & q) + "  (AND)");
        System.out.println("   p | q  = " + (p | q) + "  (OR)");
        System.out.println("   p ^ q  = " + (p ^ q) + "  (XOR)");
        System.out.println("   p << 1 = " + (p << 1) + "  (shift left = x2)");

        // ── 9. instanceof ────────────────────────────────────────────────
        Object value = "text";
        System.out.println("\n9) value instanceof String: " + (value instanceof String));

        // ── 10. One-line summary ─────────────────────────────────────────
        // Integer / keeps the type. Use .equals() for text, == for numbers.
        // && and || short-circuit, which makes them useful beyond booleans.
    }

    private static boolean sideEffect() {
        System.out.println("   (this line only runs if the left side was true)");
        return true;
    }
}
