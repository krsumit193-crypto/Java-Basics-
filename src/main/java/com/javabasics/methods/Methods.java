package com.javabasics.methods;

/**
 * ============================================================================
 *  04 - METHODS
 * ============================================================================
 *  WHAT:  A named, reusable block of code inside a class.
 *  WHY:   Avoid repetition, hide complexity, split a problem into pieces.
 *  HOW:   modifier returnType name(parameters) { ... return value; }
 *  GOTCHA: Java is always pass-by-value. For objects the value is a
 *          reference, so the object's contents can still be changed.
 * ============================================================================
 */
public final class Methods {

    private Methods() {
    }

    public static void run() {
        // ── 1. Calling a static method ───────────────────────────────────
        System.out.println("1) add(2, 3) = " + add(2, 3));
        System.out.println("   subtract(9, 4) = " + subtract(9, 4));

        // ── 2. A method with no return value ─────────────────────────────
        greet("Sumit");

        // ── 3. Method overloading ────────────────────────────────────────
        // Same name, different parameter lists. The compiler picks one.
        System.out.println("\n3) overloading:");
        System.out.println("   area(5)       = " + area(5));
        System.out.println("   area(5, 3)    = " + area(5, 3));
        System.out.println("   area(2.5)     = " + area(2.5));

        // ── 4. Varargs ───────────────────────────────────────────────────
        System.out.println("\n4) varargs:");
        System.out.println("   sum()             = " + sum());
        System.out.println("   sum(1, 2, 3)      = " + sum(1, 2, 3));
        System.out.println("   sum(10, 20, 30, 40) = " + sum(10, 20, 30, 40));

        // ── 5. Returning multiple values with a record ───────────────────
        MinMax result = minMax(5, 2, 9, 1);
        System.out.println("\n5) minMax = " + result);

        // ── 6. Static vs instance methods ────────────────────────────────
        // Static belongs to the class; instance belongs to an object.
        Counter counter = new Counter();
        counter.increment();
        counter.increment();
        System.out.println("\n6) instance counter = " + counter.value());
        System.out.println("   static helper     = " + Counter.describe());

        // ── 7. Recursion ─────────────────────────────────────────────────
        System.out.println("\n7) recursion:");
        System.out.println("   factorial(5) = " + factorial(5));
        System.out.println("   fibonacci(10) = " + fibonacci(10));

        // ── 8. Pass-by-value ─────────────────────────────────────────────
        int number = 10;
        changePrimitive(number);
        System.out.println("\n8) after changePrimitive(number): " + number);

        int[] values = {1, 2, 3};
        changeArray(values);
        System.out.println("   after changeArray(values): " + java.util.Arrays.toString(values));

        // The primitive was copied; the array reference was copied, but both
        // references point to the same array, so its contents changed.

        // ── 9. One-line summary ──────────────────────────────────────────
        // Methods group logic. Overload by changing the parameter list.
        // varargs collect extra arguments. Java passes everything by value.
    }

    // ── Method declarations ─────────────────────────────────────────────

    private static int add(int a, int b) {
        return a + b;
    }

    private static int subtract(int a, int b) {
        return a - b;
    }

    private static void greet(String name) {
        System.out.println("2) Hello, " + name + "!");
        // no return value -> void
    }

    private static int area(int side) {
        return side * side;
    }

    private static int area(int width, int height) {
        return width * height;
    }

    private static double area(double radius) {
        return Math.PI * radius * radius;
    }

    private static int sum(int... numbers) {
        int total = 0;
        for (int number : numbers) {
            total += number;
        }
        return total;
    }

    private record MinMax(int min, int max) {
    }

    private static MinMax minMax(int... numbers) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int number : numbers) {
            min = Math.min(min, number);
            max = Math.max(max, number);
        }
        return new MinMax(min, max);
    }

    private static long factorial(int n) {
        if (n <= 1) {
            return 1;                    // base case stops the recursion
        }
        return n * factorial(n - 1);     // recursive case
    }

    private static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    private static void changePrimitive(int value) {
        value = 99; // only the local copy changes
    }

    private static void changeArray(int[] values) {
        values[0] = 99; // the shared array is mutated
    }

    /** Small class used to show static vs instance methods. */
    private static final class Counter {
        private int count;

        void increment() {
            count++;
        }

        int value() {
            return count;
        }

        static String describe() {
            return "a counter starts at 0";
        }
    }
}
