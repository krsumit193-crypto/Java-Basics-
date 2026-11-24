package com.javabasics.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 *  10 - GENERICS
 * ============================================================================
 *  WHAT:  Types that take other types as parameters: Box<T>, List<String>.
 *  WHY:   Type safety without casting. The compiler rejects a Box<Integer>
 *         that receives a String, and you get no ClassCastException later.
 *  HOW:   generic class <T>, generic method, bounded types <T extends Number>,
 *         wildcards (? extends / ? super).
 *  GOTCHA: Generics are erased at runtime (type erasure), so you cannot do
 *          `new T[]`, `instanceof List<String>`, or overload on <T> alone.
 * ============================================================================
 */
public final class GenericsDemo {

    private GenericsDemo() {
    }

    public static void run() {
        // ── 1. A generic class ───────────────────────────────────────────
        Box<String> textBox = new Box<>("hello");
        Box<Integer> numberBox = new Box<>(42);

        System.out.println("1) generic class:");
        System.out.println("   textBox   = " + textBox);
        System.out.println("   numberBox = " + numberBox);
        System.out.println("   textBox.get().length() = " + textBox.get().length());

        // Type safety: the next line would not compile.
        // Box<String> wrong = new Box<>(42);

        // ── 2. Two type parameters ───────────────────────────────────────
        Pair<String, Integer> score = new Pair<>("Sumit", 88);
        System.out.println("\n2) two parameters: " + score);
        System.out.println("   key = " + score.key() + ", value = " + score.value());

        // ── 3. Generic methods ───────────────────────────────────────────
        // The <T> before the return type declares the type parameter.
        System.out.println("\n3) generic methods:");
        System.out.println("   first(1,2,3)      = " + first(1, 2, 3));
        System.out.println("   first(\"a\",\"b\")     = " + first("a", "b"));
        System.out.println("   swapped           = " + swap(new Pair<>("x", 1)));

        // ── 4. Bounded type parameters ───────────────────────────────────
        // <T extends Number> gives access to Number methods (doubleValue).
        System.out.println("\n4) bounded types:");
        System.out.println("   sum(1, 2, 3)        = " + sum(1, 2, 3));
        System.out.println("   sum(1.5, 2.5)       = " + sum(1.5, 2.5));
        System.out.println("   sum(10L, 20L)       = " + sum(10L, 20L));

        // ── 5. Wildcards ─────────────────────────────────────────────────
        // ? extends Number = read-only view of "some Number subtype".
        // ? super Integer  = write-only view that accepts Integers.
        List<Integer> integers = List.of(1, 2, 3);
        List<Double> doubles = List.of(1.5, 2.5);

        System.out.println("\n5) wildcards:");
        System.out.println("   total(integers) = " + total(integers));
        System.out.println("   total(doubles)  = " + total(doubles));

        List<Number> sink = new ArrayList<>();
        addNumbers(sink, 1, 2.5, 3L);
        System.out.println("   after addNumbers: " + sink);

        // ── 6. Generics and inheritance ──────────────────────────────────
        // List<Integer> is NOT a List<Number>, even though Integer is a Number.
        // That is why wildcards exist.
        System.out.println("\n6) invariance:");
        System.out.println("   List<Integer> is not a List<Number>");
        System.out.println("   use List<? extends Number> to accept both");

        // ── 7. A generic result type ─────────────────────────────────────
        Result<User> ok = Result.ok(new User("Sumit"));
        Result<User> failed = Result.error("user not found");

        System.out.println("\n7) generic result:");
        System.out.println("   " + ok);
        System.out.println("   " + failed);
        System.out.println("   ok.map(User::name).orElse(\"?\") = "
                + ok.map(User::name).orElse("?"));

        // ── 8. Type erasure in practice ──────────────────────────────────
        // At runtime both lists are just ArrayList. That is why you cannot
        // create generic arrays or check the element type with instanceof.
        System.out.println("\n8) erasure:");
        System.out.println("   List<String>.getClass() = "
                + new ArrayList<String>().getClass().getSimpleName());
        System.out.println("   List<Integer>.getClass() = "
                + new ArrayList<Integer>().getClass().getSimpleName());

        // ── 9. One-line summary ──────────────────────────────────────────
        // Generics move type errors from runtime to compile time.
        // <T extends X> bounds a type; ? extends / ? super make APIs flexible.
    }

    // ── Generic types ───────────────────────────────────────────────────

    /** A container that works with any type, safely. */
    static final class Box<T> {
        private final T value;

        Box(T value) {
            this.value = value;
        }

        T get() {
            return value;
        }

        @Override
        public String toString() {
            return "Box[" + value + "]";
        }
    }

    record Pair<K, V>(K key, V value) {
    }

    record User(String name) {
    }

    /** A tiny Result type: either a value or an error message. */
    static final class Result<T> {
        private final T value;
        private final String error;

        private Result(T value, String error) {
            this.value = value;
            this.error = error;
        }

        static <T> Result<T> ok(T value) {
            return new Result<>(value, null);
        }

        static <T> Result<T> error(String message) {
            return new Result<>(null, message);
        }

        boolean isOk() {
            return error == null;
        }

        <R> Result<R> map(java.util.function.Function<T, R> mapper) {
            return isOk() ? Result.ok(mapper.apply(value)) : Result.error(error);
        }

        T orElse(T fallback) {
            return isOk() ? value : fallback;
        }

        @Override
        public String toString() {
            return isOk() ? "Ok(" + value + ")" : "Error(" + error + ")";
        }
    }

    // ── Generic methods ─────────────────────────────────────────────────

    @SafeVarargs
    private static <T> T first(T... values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("at least one value is required");
        }
        return values[0];
    }

    private static <K, V> Pair<V, K> swap(Pair<K, V> pair) {
        return new Pair<>(pair.value(), pair.key());
    }

    /** T is bounded, so doubleValue() is available. */
    @SafeVarargs
    private static <T extends Number> double sum(T... values) {
        double total = 0;
        for (T value : values) {
            total += value.doubleValue();
        }
        return total;
    }

    /** Read-only wildcard: accepts a list of any Number subtype. */
    private static double total(List<? extends Number> numbers) {
        double total = 0;
        for (Number number : numbers) {
            total += number.doubleValue();
        }
        return total;
    }

    /** Write-only wildcard: accepts List<Integer>, List<Number>, List<Object>. */
    private static void addNumbers(List<? super Integer> target, Number... numbers) {
        for (Number number : numbers) {
            target.add(number.intValue());
        }
    }
}
