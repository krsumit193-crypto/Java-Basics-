package com.javabasics.exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * ============================================================================
 *  09 - EXCEPTIONS
 * ============================================================================
 *  WHAT:  Java's way of signalling that something went wrong.
 *  WHY:   Hiding errors is how programs corrupt data. Exceptions separate the
 *         happy path from the failure handling.
 *  HOW:   try / catch / finally, throw, throws, custom exception classes,
 *         try-with-resources for anything that must be closed.
 *  GOTCHA: Never swallow an exception with an empty catch block. Catch the
 *          most specific type you can handle, and let the rest bubble up.
 * ============================================================================
 */
public final class ExceptionsDemo {

    private ExceptionsDemo() {
    }

    public static void run() {
        // ── 1. try / catch ───────────────────────────────────────────────
        System.out.println("1) basic try/catch:");
        try {
            int result = 10 / 0;                 // ArithmeticException
            System.out.println("   never runs: " + result);
        } catch (ArithmeticException exception) {
            System.out.println("   caught: " + exception.getMessage());
        }

        // ── 2. Multiple catch blocks ─────────────────────────────────────
        // Order matters: a subclass must come before its superclass.
        System.out.println("\n2) multiple catches:");
        System.out.println("   " + describe(null));
        System.out.println("   " + describe("42"));
        System.out.println("   " + describe("abc"));

        // ── 3. finally always runs ───────────────────────────────────────
        // Use it for cleanup. (try-with-resources is better for resources.)
        System.out.println("\n3) finally:");
        System.out.println("   " + divide(10, 2));
        System.out.println("   " + divide(10, 0));

        // ── 4. Multi-catch (Java 7+) ─────────────────────────────────────
        System.out.println("\n4) multi-catch:");
        try {
            Object value = "text";
            Integer number = (Integer) value;    // ClassCastException
            System.out.println("   never runs: " + number);
        } catch (ClassCastException | NullPointerException exception) {
            System.out.println("   caught " + exception.getClass().getSimpleName());
        }

        // ── 5. Checked vs unchecked ──────────────────────────────────────
        // Unchecked (RuntimeException): programming bugs - don't force catch.
        // Checked (Exception): recoverable external problems - the compiler
        // insists you handle or declare them.
        System.out.println("\n5) checked exception:");
        try {
            readFirstLine("");
        } catch (IOException exception) {
            System.out.println("   declared + caught: " + exception.getMessage());
        }

        // ── 6. try-with-resources ────────────────────────────────────────
        // Any AutoCloseable is closed automatically, even if an exception is
        // thrown. No `finally` and no leak.
        System.out.println("\n6) try-with-resources:");
        try (BufferedReader reader = new BufferedReader(new StringReader("first line\nsecond line"))) {
            System.out.println("   read: " + reader.readLine());
        } catch (IOException exception) {
            System.out.println("   failed: " + exception.getMessage());
        }

        // ── 7. Throwing your own exception ───────────────────────────────
        System.out.println("\n7) custom exception:");
        try {
            withdraw(100, 500);
        } catch (InsufficientBalanceException exception) {
            System.out.println("   " + exception.getMessage());
            System.out.println("   shortfall = " + exception.shortfall());
        }

        // ── 8. Exception chaining keeps the original cause ───────────────
        System.out.println("\n8) chaining:");
        try {
            loadConfig(null);
        } catch (ConfigException exception) {
            System.out.println("   " + exception.getMessage()
                    + " (cause: " + exception.getCause().getClass().getSimpleName() + ")");
        }

        // ── 9. Best practices ────────────────────────────────────────────
        System.out.println("""

                9) rules of thumb:
                   - catch only what you can actually handle
                   - never leave a catch block empty
                   - fail fast: validate arguments at the start of a method
                   - include context in the message, never the password
                   - prefer try-with-resources over manual close()
                """);

        // ── 10. One-line summary ─────────────────────────────────────────
        // try = risky code, catch = recovery, finally = cleanup.
        // Checked exceptions must be handled; unchecked ones are bugs.
    }

    /** Returns a description, or a friendly error message. */
    private static String describe(String text) {
        try {
            return "length of \"" + text + "\" = " + text.length();
        } catch (NullPointerException exception) {
            return "input was null";
        }
    }

    private static String divide(int a, int b) {
        try {
            return "   " + a + " / " + b + " = " + (a / b);
        } catch (ArithmeticException exception) {
            return "   cannot divide by zero";
        } finally {
            // Runs on both paths, including after a return above.
            System.out.println("   (finally: cleanup for " + a + "/" + b + ")");
        }
    }

    /** Declares a checked exception: callers must deal with it. */
    private static String readFirstLine(String text) throws IOException {
        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("input was empty");
            }
            return line;
        }
    }

    private static void withdraw(double balance, double amount) {
        if (amount > balance) {
            throw new InsufficientBalanceException(amount - balance);
        }
        System.out.println("   withdrawn " + amount);
    }

    private static void loadConfig(String path) {
        try {
            if (path == null) {
                throw new IllegalArgumentException("path is null");
            }
            System.out.println("   loading " + path);
        } catch (IllegalArgumentException exception) {
            // Wrap the low-level cause in a domain-specific exception.
            throw new ConfigException("could not load configuration", exception);
        }
    }

    /** Custom unchecked exception with extra data. */
    static final class InsufficientBalanceException extends RuntimeException {
        private final double shortfall;

        InsufficientBalanceException(double shortfall) {
            super(String.format("insufficient balance: short by %.2f", shortfall));
            this.shortfall = shortfall;
        }

        double shortfall() {
            return shortfall;
        }
    }

    /** Custom checked exception: part of the method's contract. */
    static final class ConfigException extends RuntimeException {
        ConfigException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
