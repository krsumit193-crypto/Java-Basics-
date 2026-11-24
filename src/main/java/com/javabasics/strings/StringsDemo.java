package com.javabasics.strings;

/**
 * ============================================================================
 *  07 - STRINGS
 * ============================================================================
 *  WHAT:  Text. A String is an immutable sequence of characters.
 *  WHY:   Names, messages, filenames, API responses - everything is text.
 *  HOW:   literals, concatenation, String methods, StringBuilder, text blocks.
 *  GOTCHA: Strings are immutable, so every "change" returns a NEW string.
 *          Compare text with .equals(), never with ==.
 * ============================================================================
 */
public final class StringsDemo {

    private StringsDemo() {
    }

    public static void run() {
        // ── 1. Creating strings ──────────────────────────────────────────
        String city = "Patna";                 // literal (goes to the string pool)
        String language = new String("Java");  // explicit object
        String intro = "I live in " + city;    // concatenation

        System.out.println("1) " + language + " | " + intro);
        System.out.println("   length = " + language.length());
        System.out.println("   first  = " + language.charAt(0));
        System.out.println("   last   = " + language.charAt(language.length() - 1));

        // ── 2. Immutability ──────────────────────────────────────────────
        String greeting = "Hello";
        greeting.toUpperCase();               // result discarded
        System.out.println("\n2) after discarded toUpperCase: " + greeting);

        greeting = greeting.toUpperCase();    // assign the result
        System.out.println("   after assigning: " + greeting);

        // ── 3. Equality: == vs equals() ──────────────────────────────────
        String a = "java";
        String b = "java";
        String c = new String("java");

        System.out.println("\n3) equality:");
        System.out.println("   a == b        : " + (a == b) + "  (same pooled literal)");
        System.out.println("   a == c        : " + (a == c) + "  (different objects)");
        System.out.println("   a.equals(c)   : " + a.equals(c) + "  <-- use this");
        System.out.println("   a.equalsIgnoreCase(\"JAVA\") : " + a.equalsIgnoreCase("JAVA"));

        // ── 4. Searching and inspecting ──────────────────────────────────
        String email = "sumit.sharma@example.com";
        System.out.println("\n4) searching:");
        System.out.println("   contains(\"@\")     : " + email.contains("@"));
        System.out.println("   startsWith(\"sumit\"): " + email.startsWith("sumit"));
        System.out.println("   endsWith(\".com\")   : " + email.endsWith(".com"));
        System.out.println("   indexOf(\"@\")      : " + email.indexOf("@"));
        System.out.println("   isBlank()         : " + "   ".isBlank());
        System.out.println("   isEmpty()         : " + "".isEmpty());

        // ── 5. Extracting ────────────────────────────────────────────────
        String text = "JavaScript is everywhere";
        System.out.println("\n5) extracting:");
        System.out.println("   substring(0, 10) : " + text.substring(0, 10));
        System.out.println("   substring(11)    : " + text.substring(11));

        // ── 6. Replacing and trimming ────────────────────────────────────
        String messy = "   Hello   World   ";
        System.out.println("\n6) cleaning:");
        System.out.println("   trim()              : \"" + messy.trim() + "\"");
        System.out.println("   replace(\"World\",\"Java\") : " + messy.trim().replace("World", "Java"));
        System.out.println("   toUpperCase()       : " + messy.trim().toUpperCase());

        // ── 7. Splitting and joining ─────────────────────────────────────
        String csv = "javascript,react,node";
        String[] parts = csv.split(",");
        System.out.println("\n7) split: " + java.util.Arrays.toString(parts));
        System.out.println("   join: " + String.join(" | ", parts));

        // ── 8. Formatting ────────────────────────────────────────────────
        String name = "Sumit";
        int marks = 88;
        String line = String.format("%s scored %d marks (%.1f%%)", name, marks, 88.0);
        System.out.println("\n8) String.format: " + line);

        // ── 9. Text blocks (Java 15+) ────────────────────────────────────
        String block = """
                {
                  "name": "Sumit",
                  "language": "Java"
                }
                """;
        System.out.println("\n9) text block:");
        System.out.println(block);

        // ── 10. StringBuilder for many changes ───────────────────────────
        // Concatenating in a loop creates many temporary strings; a builder
        // avoids that. Use it when you mutate text repeatedly.
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i <= 3; i++) {
            builder.append("item").append(i).append(' ');
        }
        System.out.println("10) StringBuilder: " + builder.toString().trim());

        // ── 11. A practical helper ───────────────────────────────────────
        System.out.println("11) slugify: " + slugify("Hello, Java World!"));

        // ── 12. One-line summary ─────────────────────────────────────────
        // Strings are immutable: always assign the result of a method.
        // Use .equals() for text, .trim() for input, StringBuilder for loops.
    }

    private static String slugify(String title) {
        return title.toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-");
    }
}
