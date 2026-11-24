package com.javabasics.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * ============================================================================
 *  14 - CONCURRENCY
 * ============================================================================
 *  WHAT:  Doing several things at the same time with threads.
 *  WHY:   Waiting for a network call or a file should not freeze the program.
 *  HOW:   Thread / Runnable, ExecutorService, Callable + Future,
 *         CompletableFuture, synchronized and AtomicInteger.
 *  GOTCHA: Shared mutable state causes race conditions. Two threads doing
 *          count++ can lose updates. Prefer immutable data, atomics, or
 *          confinement over `synchronized` everywhere.
 * ============================================================================
 */
public final class ConcurrencyDemo {

    private ConcurrencyDemo() {
    }

    public static void run() {
        // ── 1. A plain Thread ────────────────────────────────────────────
        Thread worker = new Thread(() -> System.out.println("1) running on " + Thread.currentThread().getName()));
        worker.setName("demo-worker");
        worker.start();
        join(worker);

        // ── 2. The race condition ────────────────────────────────────────
        // `count++` is read-modify-write: not atomic. Many increments can be
        // lost when threads interleave.
        UnsafeCounter unsafe = new UnsafeCounter();
        runInParallel(1_000, unsafe::increment);
        System.out.println("\n2) unsafe counter after 1000 increments = " + unsafe.value()
                + "  (often less than 1000)");

        // ── 3. Fix 1: synchronized ───────────────────────────────────────
        SynchronizedCounter safe = new SynchronizedCounter();
        runInParallel(1_000, safe::increment);
        System.out.println("\n3) synchronized counter = " + safe.value());

        // ── 4. Fix 2: AtomicInteger (lock-free, usually faster) ──────────
        AtomicInteger atomic = new AtomicInteger();
        runInParallel(1_000, atomic::incrementAndGet);
        System.out.println("4) atomic counter = " + atomic.get());

        // ── 5. ExecutorService: reuse a pool of threads ──────────────────
        ExecutorService pool = Executors.newFixedThreadPool(3);
        try {
            List<Callable<String>> tasks = List.of(
                    () -> "task-1 on " + Thread.currentThread().getName(),
                    () -> "task-2 on " + Thread.currentThread().getName(),
                    () -> "task-3 on " + Thread.currentThread().getName());

            // invokeAll runs them in parallel and waits for every result.
            List<Future<String>> results = pool.invokeAll(tasks);
            System.out.println("\n5) executor:");
            for (Future<String> result : results) {
                System.out.println("   " + result.get());
            }

            // submit + Future for a single task
            Future<Integer> sum = pool.submit(() -> IntStream.rangeClosed(1, 100).sum());
            System.out.println("   sum 1..100 = " + sum.get());

        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();   // restore the flag
            System.out.println("   interrupted");
        } catch (ExecutionException exception) {
            System.out.println("   task failed: " + exception.getCause().getMessage());
        } finally {
            pool.shutdown();
            awaitTermination(pool);
        }

        // ── 6. CompletableFuture: async pipelines ───────────────────────
        // thenApply transforms, thenCompose chains another async step,
        // exceptionally handles failures. join() blocks until the value is ready.
        String pipeline = CompletableFuture
                .supplyAsync(() -> 21)
                .thenApply(value -> value * 2)
                .thenApply(value -> "result = " + value)
                .exceptionally(exception -> "failed: " + exception.getMessage())
                .join();
        System.out.println("\n6) CompletableFuture: " + pipeline);

        // Combining two independent async results.
        CompletableFuture<Integer> left = CompletableFuture.supplyAsync(() -> 10);
        CompletableFuture<Integer> right = CompletableFuture.supplyAsync(() -> 32);
        int combined = left.thenCombine(right, Integer::sum).join();
        System.out.println("   combine 10 + 32 = " + combined);

        // ── 7. Virtual threads (Java 21+) ────────────────────────────────
        // Cheap threads: millions are possible. Great for blocking I/O.
        try (ExecutorService virtual = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<Integer>> futures = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                int id = i;
                futures.add(virtual.submit(() -> id * id));
            }
            System.out.println("\n7) virtual threads:");
            for (Future<Integer> future : futures) {
                System.out.println("   square = " + future.get());
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException exception) {
            System.out.println("   task failed: " + exception.getCause().getMessage());
        }

        // ── 8. Rules of thumb ────────────────────────────────────────────
        System.out.println("""

                8) rules of thumb:
                   - share as little mutable state as possible
                   - prefer executors over raw `new Thread`
                   - never call Thread.sleep inside a lock
                   - always restore the interrupt flag (Thread.currentThread().interrupt())
                   - use virtual threads for blocking I/O, not for CPU-heavy work
                """);

        // ── 9. One-line summary ─────────────────────────────────────────
        // Threads run code in parallel. Shared counters need synchronized or
        // atomics. Executors manage threads; CompletableFuture composes them.
    }

    /** Runs `task` 1000 times across a small pool and waits for all of them. */
    private static void runInParallel(int times, Runnable task) {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        try {
            for (int i = 0; i < times; i++) {
                pool.submit(task);
            }
        } finally {
            pool.shutdown();
            awaitTermination(pool);
        }
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private static void awaitTermination(ExecutorService pool) {
        try {
            if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException exception) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /** Deliberately broken: no synchronization. */
    static final class UnsafeCounter {
        private int count;

        void increment() {
            count++;   // read, add, write - three steps, not atomic
        }

        int value() {
            return count;
        }
    }

    /** Correct: the monitor lock makes increment atomic. */
    static final class SynchronizedCounter {
        private int count;

        synchronized void increment() {
            count++;
        }

        synchronized int value() {
            return count;
        }
    }
}
