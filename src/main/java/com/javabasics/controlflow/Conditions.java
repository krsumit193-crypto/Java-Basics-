package com.javabasics.controlflow;

/**
 * ============================================================================
 *  02 - CONDITIONS
 * ============================================================================
 *  WHAT:  Code that chooses which path to run based on a condition.
 *  WHY:   Programs must react differently to different inputs.
 *  HOW:   if / else if / else, switch, switch expressions, ternary.
 *  GOTCHA: A condition must be a boolean in Java (no `if (1)` like C).
 *          switch cases fall through unless you break (or use arrows).
 * ============================================================================
 */
public final class Conditions {

    private Conditions() {
    }

    public static void run() {
        // ── 1. if / else if / else ───────────────────────────────────────
        int marks = 78;

        if (marks >= 90) {
            System.out.println("1) Grade A - excellent!");
        } else if (marks >= 75) {
            System.out.println("1) Grade B - very good!");
        } else if (marks >= 40) {
            System.out.println("1) Grade C - passed.");
        } else {
            System.out.println("1) Grade F - try again.");
        }

        // Only the first matching branch runs. Order matters.

        // ── 2. Conditions must be boolean ────────────────────────────────
        // Unlike C, `if (1)` does not compile in Java.
        boolean isLoggedIn = true;
        if (isLoggedIn) {
            System.out.println("2) welcome back");
        }

        // ── 3. Classic switch (with break) ───────────────────────────────
        String day = "Monday";

        switch (day) {
            case "Monday":
                System.out.println("3) Monday: weekly planning");
                break;                        // without break it falls through
            case "Saturday":
            case "Sunday":
                System.out.println("3) Weekend: rest and learn Java");
                break;
            default:
                System.out.println("3) Regular day");
        }

        // ── 4. Switch expression (Java 14+) - returns a value ────────────
        String type = switch (day) {
            case "Saturday", "Sunday" -> "weekend";
            case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> "weekday";
            default -> "unknown";
        };
        System.out.println("4) switch expression: " + day + " is a " + type);

        // A block case can use `yield` to produce the value.
        int workingHours = switch (day) {
            case "Saturday", "Sunday" -> 0;
            default -> {
                int hours = 8;
                yield hours; // return from the switch expression
            }
        };
        System.out.println("   working hours: " + workingHours);

        // ── 5. Ternary operator ──────────────────────────────────────────
        int age = 22;
        String category = age >= 18 ? "adult" : "minor";
        System.out.println("5) age " + age + " -> " + category);

        // ── 6. Guard clauses keep methods flat ───────────────────────────
        System.out.println("6) processOrder cases:");
        System.out.println("   " + processOrder(null));
        System.out.println("   " + processOrder(new Order(new String[]{}, true)));
        System.out.println("   " + processOrder(new Order(new String[]{"book"}, false)));
        System.out.println("   " + processOrder(new Order(new String[]{"book"}, true)));

        // ── 7. Pattern matching for instanceof (Java 16+) ────────────────
        Object value = "a piece of text";
        if (value instanceof String text && !text.isBlank()) {
            // `text` is already cast to String inside this block.
            System.out.println("7) text length: " + text.length());
        }

        // ── 8. One-line summary ──────────────────────────────────────────
        // if/else for ranges, switch for exact matches, ternary for simple
        // picks. Prefer switch expressions over fall-through statements.
    }

    private record Order(String[] items, boolean paid) {
    }

    /** Early returns avoid deeply nested if statements. */
    private static String processOrder(Order order) {
        if (order == null) {
            return "no order";
        }
        if (order.items().length == 0) {
            return "empty order";
        }
        if (!order.paid()) {
            return "payment pending";
        }
        return "processing";
    }
}
