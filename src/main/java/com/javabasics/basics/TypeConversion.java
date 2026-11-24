package com.javabasics.basics;

/**
 * ============================================================================
 *  01 - TYPE CONVERSION
 * ============================================================================
 *  WHAT:  Turning one type into another - number <-> text <-> char.
 *  WHY:   Console input and files give you text; maths needs numbers.
 *  HOW:   widening is automatic, narrowing needs a cast, parsing uses
 *         Integer.parseInt / Double.parseDouble / String.valueOf.
 *  GOTCHA: Parsing invalid text throws NumberFormatException; a bad cast can
 *          silently truncate or wrap a value.
 * ============================================================================
 */
public final class TypeConversion {

    private TypeConversion() {
    }

    public static void run() {
        // ── 1. Widening conversion (automatic, safe) ─────────────────────
        int intValue = 100;
        long asLong = intValue;
        float asFloat = intValue;
        double asDouble = intValue;

        System.out.println("1) widening:");
        System.out.println("   int -> long   : " + asLong);
        System.out.println("   int -> float  : " + asFloat);
        System.out.println("   int -> double : " + asDouble);

        // ── 2. Narrowing conversion (explicit cast, may lose data) ───────
        double price = 199.75;
        int wholePrice = (int) price;       // 199 - fraction dropped
        long huge = 5_000_000_000L;
        int wrapped = (int) huge;           // does not fit in 32 bits

        System.out.println("\n2) narrowing:");
        System.out.println("   (int) 199.75       = " + wholePrice);
        System.out.println("   (int) 5_000_000_000 = " + wrapped + "  <-- wrapped around");

        // ── 3. String -> number (parsing) ────────────────────────────────
        String numberText = "42";
        String decimalText = "3.14";
        String truthText = "true";

        int parsedInt = Integer.parseInt(numberText);
        long parsedLong = Long.parseLong(numberText);
        double parsedDouble = Double.parseDouble(decimalText);
        boolean parsedBoolean = Boolean.parseBoolean(truthText);

        System.out.println("\n3) parsing:");
        System.out.println("   Integer.parseInt(\"42\")   = " + parsedInt);
        System.out.println("   Long.parseLong(\"42\")     = " + parsedLong);
        System.out.println("   Double.parseDouble(\"3.14\") = " + parsedDouble);
        System.out.println("   Boolean.parseBoolean(\"true\") = " + parsedBoolean);
        System.out.println("   parsedInt + 8 = " + (parsedInt + 8));

        // ── 4. The parsing gotcha ────────────────────────────────────────
        String bad = "12abc";
        try {
            int ignored = Integer.parseInt(bad);
            System.out.println("\n4) this line never runs: " + ignored);
        } catch (NumberFormatException exception) {
            System.out.println("\n4) Integer.parseInt(\"12abc\") threw: " + exception.getMessage());
            System.out.println("   Always validate input before parsing.");
        }

        // ── 5. Number -> String (formatting) ─────────────────────────────
        int score = 88;
        double ratio = 0.756;

        String viaValueOf = String.valueOf(score);
        String viaConcat = "" + score;
        String formatted = String.format("%.1f%%", ratio * 100);

        System.out.println("\n5) to text:");
        System.out.println("   String.valueOf(88) = " + viaValueOf);
        System.out.println("   \"\" + 88            = " + viaConcat);
        System.out.println("   String.format       = " + formatted);

        // ── 6. char <-> int ──────────────────────────────────────────────
        char letter = 'A';
        int code = letter;                  // widening: char -> int
        char fromCode = (char) (code + 2);  // narrowing: int -> char

        System.out.println("\n6) char <-> int:");
        System.out.println("   'A' as int   = " + code);
        System.out.println("   (char) 67    = " + fromCode);

        // ── 7. Realistic example: total from text values ─────────────────
        String priceText = " 250 ";
        String quantityText = "3";

        // trim() removes surrounding spaces, then parse.
        int unitPrice = Integer.parseInt(priceText.trim());
        int quantity = Integer.parseInt(quantityText);
        int total = unitPrice * quantity;

        System.out.println("\n7) order total: " + unitPrice + " x " + quantity + " = " + total);

        // ── 8. One-line summary ──────────────────────────────────────────
        // Widening is automatic; narrowing needs (cast) and can lose data.
        // Use Integer.parseInt / Double.parseDouble to read numbers from text.
        // Wrap parsing in try/catch - bad input throws NumberFormatException.
    }
}
