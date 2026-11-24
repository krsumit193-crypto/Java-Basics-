package com.javabasics.functional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * ============================================================================
 *  11 - LAMBDAS, STREAMS & OPTIONAL
 * ============================================================================
 *  WHAT:  Lambdas are short anonymous functions; streams transform data
 *         declaratively; Optional models "a value or nothing".
 *  WHY:   Less boilerplate, fewer loops, and code that reads like the problem
 *         ("filter the paid orders, then sum the totals").
 *  HOW:   (params) -> expression, stream().filter().map().collect(), Optional.
 *  GOTCHA: A stream can be consumed only once. Streams are lazy: nothing runs
 *          until a terminal operation (collect, sum, forEach) is called.
 * ============================================================================
 */
public final class FunctionalDemo {

    private FunctionalDemo() {
    }

    public static void run() {
        // ── 1. A lambda is an implementation of a functional interface ───
        // A functional interface has exactly one abstract method.
        Greeting hello = name -> "Hello, " + name + "!";
        Greeting bye = name -> "Bye, " + name + "!";

        System.out.println("1) lambdas:");
        System.out.println("   " + hello.greet("Sumit"));
        System.out.println("   " + bye.greet("Sumit"));

        // ── 2. The built-in functional interfaces ────────────────────────
        Predicate<Integer> isEven = number -> number % 2 == 0;
        Function<String, Integer> length = String::length;          // method ref
        Supplier<String> greeting = () -> "welcome";
        BiFunction<Integer, Integer, Integer> add = Integer::sum;

        System.out.println("\n2) built-in interfaces:");
        System.out.println("   isEven.test(10)      = " + isEven.test(10));
        System.out.println("   length.apply(\"Java\") = " + length.apply("Java"));
        System.out.println("   greeting.get()       = " + greeting.get());
        System.out.println("   add.apply(2, 3)      = " + add.apply(2, 3));

        // ── 3. Chaining predicates and functions ─────────────────────────
        Predicate<Integer> isPositive = number -> number > 0;
        Predicate<Integer> isSmallEven = isEven.and(isPositive).and(n -> n < 100);

        System.out.println("\n3) composition:");
        System.out.println("   isSmallEven.test(42)  = " + isSmallEven.test(42));
        System.out.println("   isSmallEven.test(-42) = " + isSmallEven.test(-42));
        System.out.println("   isEven.negate().test(3) = " + isEven.negate().test(3));

        // ── 4. Streams: filter -> map -> collect ─────────────────────────
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Integer> evenSquares = numbers.stream()
                .filter(isEven)
                .map(number -> number * number)
                .toList();

        System.out.println("\n4) even squares: " + evenSquares);

        // ── 5. Terminal operations ───────────────────────────────────────
        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        long count = numbers.stream().filter(isEven).count();
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        Optional<Integer> min = numbers.stream().min(Comparator.naturalOrder());
        double average = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);

        System.out.println("\n5) terminals:");
        System.out.println("   sum = " + sum + ", count of evens = " + count);
        System.out.println("   max = " + max.orElseThrow() + ", min = " + min.orElseThrow());
        System.out.printf("   average = %.2f%n", average);
        System.out.println("   anyMatch(> 9) = " + numbers.stream().anyMatch(n -> n > 9));
        System.out.println("   allMatch(> 0) = " + numbers.stream().allMatch(n -> n > 0));

        // ── 6. Streams over objects ──────────────────────────────────────
        List<Order> orders = List.of(
                new Order("A-1", "Sumit", 2_499.0, true),
                new Order("A-2", "Riya", 799.0, true),
                new Order("A-3", "Aman", 5_999.0, false),
                new Order("A-4", "Sumit", 199.0, true));

        double paidTotal = orders.stream()
                .filter(Order::paid)                    // method reference
                .mapToDouble(Order::amount)
                .sum();

        List<String> paidCustomers = orders.stream()
                .filter(Order::paid)
                .map(Order::customer)
                .distinct()
                .sorted()
                .toList();

        System.out.println("\n6) orders:");
        System.out.printf("   paid total = %.2f%n", paidTotal);
        System.out.println("   paid customers = " + paidCustomers);

        // ── 7. Grouping with collectors ──────────────────────────────────
        Map<String, List<Order>> byCustomer = orders.stream()
                .collect(Collectors.groupingBy(Order::customer));

        Map<Boolean, List<Order>> byPayment = orders.stream()
                .collect(Collectors.partitioningBy(Order::paid));

        System.out.println("\n7) collectors:");
        byCustomer.forEach((customer, list) ->
                System.out.println("   " + customer + " -> " + list.size() + " order(s)"));
        System.out.println("   paid = " + byPayment.get(true).size()
                + ", unpaid = " + byPayment.get(false).size());

        // ── 8. FlatMap: one input -> many outputs ────────────────────────
        List<List<Integer>> nested = List.of(List.of(1, 2), List.of(3, 4), List.of(5));
        List<Integer> flat = nested.stream()
                .flatMap(List::stream)
                .toList();
        System.out.println("\n8) flatMap: " + flat);

        // ── 9. Infinite streams and ranges ───────────────────────────────
        List<Integer> firstFiveSquares = Stream.iterate(1, n -> n + 1)
                .limit(5)
                .map(n -> n * n)
                .toList();
        System.out.println("\n9) iterate + limit: " + firstFiveSquares);
        System.out.println("   IntStream.rangeClosed(1,5).sum() = "
                + IntStream.rangeClosed(1, 5).sum());

        // ── 10. Optional: a value or nothing ─────────────────────────────
        // Use it as a return type for "maybe missing", not as a field type.
        System.out.println("\n10) Optional:");
        System.out.println("   " + findOrder(orders, "A-2"));
        System.out.println("   " + findOrder(orders, "missing"));
        System.out.println("   orElse        = " + findOrder(orders, "missing").orElse(Order.empty()));
        System.out.println("   map + orElse  = " + findOrder(orders, "A-1")
                .map(Order::customer)
                .orElse("unknown"));

        // ── 11. One-line summary ─────────────────────────────────────────
        // Lambda = a function value. Stream = lazy pipeline over data.
        // filter -> transform -> collect. Optional avoids returning null.
    }

    /** Functional interface: exactly one abstract method. */
    @FunctionalInterface
    interface Greeting {
        String greet(String name);
    }

    record Order(String id, String customer, double amount, boolean paid) {
        static Order empty() {
            return new Order("-", "-", 0, false);
        }

        @Override
        public String toString() {
            return String.format("%s by %s (%.2f, %s)", id, customer, amount, paid ? "paid" : "unpaid");
        }
    }

    private static Optional<Order> findOrder(List<Order> orders, String id) {
        return orders.stream()
                .filter(order -> order.id().equals(id))
                .findFirst();
    }
}
