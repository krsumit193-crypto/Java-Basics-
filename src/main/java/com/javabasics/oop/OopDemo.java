package com.javabasics.oop;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 *  08 - OBJECT-ORIENTED JAVA
 * ============================================================================
 *  WHAT:  Building programs out of classes and objects instead of loose code.
 *  WHY:   Real software is too big for one file. OOP lets you group data and
 *         behaviour, reuse code, and swap implementations safely.
 *  HOW:   class, object, fields, constructors, encapsulation (private +
 *         getters), inheritance (extends), interfaces (implements),
 *         polymorphism (one type, many behaviours), records for data.
 *  GOTCHA: Prefer composition over inheritance. Use `private` fields and
 *          expose only what callers need. Always override equals/hashCode
 *          (or use a record) when objects go into collections.
 * ============================================================================
 */
public final class OopDemo {

    private OopDemo() {
    }

    public static void run() {
        // ── 1. A class and its objects ───────────────────────────────────
        // The class is the blueprint; each `new` creates an independent object.
        BankAccount savings = new BankAccount("Sumit", 1_000);
        BankAccount current = new BankAccount("Riya");

        System.out.println("1) objects:");
        System.out.println("   " + savings);
        System.out.println("   " + current);

        // ── 2. Encapsulation ─────────────────────────────────────────────
        // Fields are private. Callers use methods, so the class controls the
        // rules (here: no negative deposits, no overdraft).
        savings.deposit(500);
        savings.withdraw(300);
        savings.withdraw(10_000);            // rejected, balance unchanged
        System.out.println("\n2) after transactions: balance = " + savings.balance());

        // ── 3. Inheritance and polymorphism ──────────────────────────────
        // `Shape` is the parent type; each subclass provides its own area().
        // The list holds the parent type, but the real object decides which
        // area() runs - that is dynamic dispatch / polymorphism.
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Circle(2.0));
        shapes.add(new Rectangle(3.0, 4.0));
        shapes.add(new Square(5.0));

        System.out.println("\n3) polymorphism:");
        for (Shape shape : shapes) {
            System.out.printf("   %-10s area = %6.2f%n", shape.name(), shape.area());
        }

        // ── 4. Abstract classes vs interfaces ────────────────────────────
        // Abstract class  : shared state + partial implementation.
        // Interface       : a contract only (what, not how).
        // A class can extend one class but implement many interfaces.
        System.out.println("\n4) contracts:");
        List<Printable> printables = List.of(
                new Circle(1.0),
                new Invoice("INV-001", 2_499.50));
        for (Printable printable : printables) {
            System.out.println("   " + printable.toLine());
        }

        // ── 5. Records: data carriers with no boilerplate ────────────────
        // A record gives you a constructor, getters, equals, hashCode and
        // toString for free - ideal for DTOs and small value objects.
        Point a = new Point(1, 2);
        Point b = new Point(1, 2);
        System.out.println("\n5) records:");
        System.out.println("   a = " + a + ", b = " + b);
        System.out.println("   a.equals(b) = " + a.equals(b) + "  (value equality)");
        System.out.println("   a.x() = " + a.x() + ", a.y() = " + a.y());

        // A compact constructor can validate without writing boilerplate.
        try {
            new Point(-1, 0);
        } catch (IllegalArgumentException exception) {
            System.out.println("   validation: " + exception.getMessage());
        }

        // ── 6. Static members belong to the class ────────────────────────
        System.out.println("\n6) static members:");
        System.out.println("   BankAccount.count = " + BankAccount.count());
        System.out.println("   BankAccount.bankName() = " + BankAccount.bankName());

        // ── 7. Composition: "has-a" is usually better than "is-a" ────────
        // A customer has an address; it is not a kind of address.
        Address address = new Address("Patna", "Bihar");
        Customer customer = new Customer("Sumit", address);
        System.out.println("\n7) composition:");
        System.out.println("   " + customer);
        System.out.println("   city = " + customer.address().city());

        // ── 8. Sealed types restrict who may extend them (Java 17+) ──────
        // Useful when a type has a known, fixed set of subtypes.
        System.out.println("\n8) sealed hierarchy:");
        for (Payment payment : List.of(new CardPayment("4242"), new UpiPayment("sumit@upi"))) {
            System.out.println("   " + payment.describe());
        }

        // ── 9. One-line summary ──────────────────────────────────────────
        // Class = blueprint, object = instance. Keep fields private.
        // extends for "is-a", implements for contracts, records for data.
        // Polymorphism means the object decides which override runs.
    }

    // ── Supporting types ────────────────────────────────────────────────

    /** Encapsulated mutable object with its own invariants. */
    static final class BankAccount {
        private static final String BANK_NAME = "Java National Bank";
        private static int created;

        private final String owner;
        private double balance;

        BankAccount(String owner) {
            this(owner, 0);
        }

        BankAccount(String owner, double openingBalance) {
            if (owner == null || owner.isBlank()) {
                throw new IllegalArgumentException("owner is required");
            }
            this.owner = owner;
            this.balance = Math.max(0, openingBalance);
            created++;
        }

        void deposit(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("deposit must be positive");
            }
            balance += amount;
        }

        boolean withdraw(double amount) {
            if (amount <= 0 || amount > balance) {
                System.out.println("   rejected withdrawal of " + amount);
                return false;
            }
            balance -= amount;
            return true;
        }

        double balance() {
            return balance;
        }

        static int count() {
            return created;
        }

        static String bankName() {
            return BANK_NAME;
        }

        @Override
        public String toString() {
            return String.format("%s: %s, balance = %.2f", BANK_NAME, owner, balance);
        }
    }

    /** Abstract parent: shared behaviour, but area() must be supplied. */
    abstract static class Shape {
        abstract double area();

        String name() {
            return getClass().getSimpleName();
        }
    }

    static final class Circle extends Shape implements Printable {
        private final double radius;

        Circle(double radius) {
            this.radius = radius;
        }

        @Override
        double area() {
            return Math.PI * radius * radius;
        }

        @Override
        public String toLine() {
            return String.format("Circle r=%.1f -> %.2f", radius, area());
        }
    }

    static class Rectangle extends Shape {
        private final double width;
        private final double height;

        Rectangle(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        double area() {
            return width * height;
        }
    }

    static final class Square extends Rectangle {
        Square(double side) {
            super(side, side);   // reuse the parent constructor
        }

        @Override
        String name() {
            return "Square";
        }
    }

    /** Contract: anything printable can produce a single-line summary. */
    interface Printable {
        String toLine();
    }

    record Invoice(String number, double amount) implements Printable {
        @Override
        public String toLine() {
            return String.format("Invoice %s -> %.2f", number, amount);
        }
    }

    /** Record with validation inside a compact constructor. */
    record Point(int x, int y) {
        Point {
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("coordinates must be >= 0");
            }
        }
    }

    record Address(String city, String state) {
    }

    /** Composition: a Customer HAS an Address. */
    record Customer(String name, Address address) {
        @Override
        public String toString() {
            return name + " from " + address.city() + ", " + address.state();
        }
    }

    /** Sealed: only the permitted subtypes may extend this interface. */
    sealed interface Payment permits CardPayment, UpiPayment {
        String describe();
    }

    record CardPayment(String last4) implements Payment {
        @Override
        public String describe() {
            return "Card ending " + last4;
        }
    }

    record UpiPayment(String handle) implements Payment {
        @Override
        public String describe() {
            return "UPI " + handle;
        }
    }
}
