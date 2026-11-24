package com.javabasics.basics;

/**
 * ============================================================================
 *  01 - VARIABLES
 * ============================================================================
 *  WHAT:  A variable is a named box that stores a value of ONE fixed type.
 *  WHY:   Programs need to remember things (a score, a name, a result).
 *  HOW:   type name = value;  Java is statically typed, so the type is fixed.
 *  GOTCHA: Local variables must be initialized before use, and a `final`
 *          variable can be assigned only once.
 * ============================================================================
 */
public final class Variables {

    private Variables() {
        // Utility class - never instantiated.
    }

    public static void run() {
        // ── 1. Declaration, initialization and assignment ────────────────
        int age;              // declaration only
        age = 22;             // first assignment (initialization)

        int score = 0;        // declaration + initialization in one line
        score = score + 10;   // assignment
        score += 5;           // compound assignment: score = score + 5

        System.out.println("1) age = " + age + ", score = " + score);

        // ── 2. Constants with `final` ────────────────────────────────────
        // `final` means the variable can never be reassigned.
        final double PI = 3.14159;
        final int MAX_STUDENTS = 30;
        // PI = 3.14;  // compile error: cannot assign a value to final variable

        System.out.println("2) PI = " + PI + ", MAX_STUDENTS = " + MAX_STUDENTS);

        // ── 3. `var` (Java 10+) - type inferred from the initializer ─────
        // Only for local variables. It is still statically typed!
        var city = "Patna";      // inferred as String
        var year = 2026;         // inferred as int
        System.out.println("3) var city = " + city + " (" + city.getClass().getSimpleName() + ")");
        System.out.println("   var year = " + year + " (int)");

        // ── 4. Naming rules and conventions ──────────────────────────────
        // Rules: letters, digits, _ and $; cannot start with a digit; no spaces.
        // Conventions: camelCase for variables, UPPER_SNAKE_CASE for constants.
        int totalMarks = 450;
        String studentName = "Sumit";
        System.out.println("4) " + studentName + " scored " + totalMarks);

        // ── 5. Block scope ───────────────────────────────────────────────
        int visibleEverywhere = 1;
        {
            int onlyInThisBlock = 2;
            System.out.println("5) inside block: " + visibleEverywhere + ", " + onlyInThisBlock);
        }
        // System.out.println(onlyInThisBlock); // compile error: out of scope

        // ── 6. Multiple variables in one statement ───────────────────────
        int a = 1, b = 2, c = 3;
        System.out.println("6) a, b, c = " + a + ", " + b + ", " + c);

        // ── 7. Swapping values ───────────────────────────────────────────
        int left = 10;
        int right = 20;
        int temp = left;
        left = right;
        right = temp;
        System.out.println("7) after swap: left = " + left + ", right = " + right);

        // ── 8. One-line summary ──────────────────────────────────────────
        // Java variables always have a type. Prefer final for constants.
        // `var` reduces noise but does not make Java dynamically typed.
    }
}
