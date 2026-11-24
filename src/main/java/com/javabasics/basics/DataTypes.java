package com.javabasics.basics;

/**
 * ============================================================================
 *  01 - DATA TYPES
 * ============================================================================
 *  WHAT:  Java has 8 primitive types plus reference types (objects).
 *  WHY:   The type decides the size in memory and which operations are legal.
 *  HOW:   byte, short, int, long, float, double, char, boolean + references.
 *  GOTCHA: `int` overflows silently; `double` cannot represent money exactly;
 *          String is a reference type, not a primitive.
 * ============================================================================
 */
public final class DataTypes {

    private DataTypes() {
    }

    public static void run() {
        // ── 1. The 8 primitive types ─────────────────────────────────────
        byte small = 100;                 // 8 bits  : -128 .. 127
        short medium = 30_000;            // 16 bits : -32,768 .. 32,767
        int count = 2_000_000_000;        // 32 bits : about +/- 2.1 billion
        long big = 9_000_000_000L;        // 64 bits : note the L suffix
        float ratio = 3.14f;              // 32 bits : note the f suffix
        double price = 99.99;             // 64 bits : default decimal type
        char grade = 'A';                 // 16 bits : one Unicode character
        boolean isActive = true;          // true or false

        System.out.println("1) byte    = " + small);
        System.out.println("   short   = " + medium);
        System.out.println("   int     = " + count);
        System.out.println("   long    = " + big);
        System.out.println("   float   = " + ratio);
        System.out.println("   double  = " + price);
        System.out.println("   char    = " + grade);
        System.out.println("   boolean = " + isActive);

        // ── 2. Ranges exposed by the wrapper classes ─────────────────────
        System.out.println("\n2) ranges:");
        System.out.println("   int  : " + Integer.MIN_VALUE + " .. " + Integer.MAX_VALUE);
        System.out.println("   long : " + Long.MIN_VALUE + " .. " + Long.MAX_VALUE);
        System.out.println("   byte : " + Byte.MIN_VALUE + " .. " + Byte.MAX_VALUE);

        // ── 3. Reference types ───────────────────────────────────────────
        // Objects live on the heap; the variable holds a reference to them.
        String name = "Sumit";                    // reference type
        int[] marks = {88, 92, 75};               // array is an object
        StringBuilder builder = new StringBuilder("hi");

        System.out.println("\n3) references:");
        System.out.println("   String  = " + name);
        System.out.println("   int[]   = " + java.util.Arrays.toString(marks));
        System.out.println("   builder = " + builder);

        // ── 4. Wrapper classes and autoboxing ────────────────────────────
        // Each primitive has a wrapper object (needed for collections).
        Integer boxed = 42;          // autoboxing: int -> Integer
        int unboxed = boxed;         // unboxing: Integer -> int
        System.out.println("\n4) boxed = " + boxed + ", unboxed = " + unboxed);
        System.out.println("   Integer.MAX_VALUE = " + Integer.MAX_VALUE);

        // ── 5. Casting between numeric types ─────────────────────────────
        // Widening (small -> large) is automatic.
        int intValue = 100;
        long widened = intValue;          // fine
        double asDouble = intValue;       // fine

        // Narrowing (large -> small) needs an explicit cast and may lose data.
        double bigDecimal = 123.99;
        int narrowed = (int) bigDecimal;  // 123 - the fraction is dropped
        byte overflowed = (byte) 200;     // 200 does not fit in a byte

        System.out.println("\n5) casting:");
        System.out.println("   int -> long   : " + widened);
        System.out.println("   int -> double : " + asDouble);
        System.out.println("   double -> int : " + narrowed + " (fraction lost)");
        System.out.println("   (byte) 200    : " + overflowed + " (wrapped around)");

        // ── 6. The integer overflow gotcha ───────────────────────────────
        int max = Integer.MAX_VALUE;
        int overflow = max + 1;           // wraps around to the minimum value
        System.out.println("\n6) Integer.MAX_VALUE + 1 = " + overflow);
        System.out.println("   Use long or Math.addExact() when overflow is possible.");

        // ── 7. char is a number underneath ───────────────────────────────
        char letter = 'A';
        System.out.println("\n7) 'A' as int = " + (int) letter);
        System.out.println("   next letter  = " + (char) (letter + 1));

        // ── 8. One-line summary ──────────────────────────────────────────
        // 8 primitives: byte short int long float double char boolean.
        // Everything else is a reference type. Prefer int, long, double,
        // boolean and String in everyday code.
    }
}
