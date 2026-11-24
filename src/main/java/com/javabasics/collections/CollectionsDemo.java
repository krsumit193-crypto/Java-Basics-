package com.javabasics.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * ============================================================================
 *  06 - COLLECTIONS
 * ============================================================================
 *  WHAT:  Resizable containers from java.util: List, Set and Map.
 *  WHY:   Arrays are fixed-size; real programs add and remove data at runtime.
 *  HOW:   List = ordered, Set = unique, Map = key -> value.
 *  GOTCHA: A Set/Map does not keep insertion order unless you pick
 *          LinkedHashSet/LinkedHashMap. HashMap order is unpredictable.
 * ============================================================================
 */
public final class CollectionsDemo {

    private CollectionsDemo() {
    }

    public static void run() {
        // ── 1. List - ordered, duplicates allowed ────────────────────────
        List<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("cherry");
        fruits.add("apple");                       // duplicate is fine

        System.out.println("1) list = " + fruits);
        System.out.println("   size = " + fruits.size());
        System.out.println("   get(1) = " + fruits.get(1));
        System.out.println("   contains(\"banana\") = " + fruits.contains("banana"));
        System.out.println("   indexOf(\"cherry\") = " + fruits.indexOf("cherry"));

        fruits.set(1, "blueberry");                // replace
        fruits.remove("apple");                    // remove first match
        System.out.println("   after updates = " + fruits);

        // ── 2. Set - unique values, no duplicates ────────────────────────
        Set<String> tags = new HashSet<>();
        tags.add("java");
        tags.add("backend");
        tags.add("java");                          // ignored

        System.out.println("\n2) set = " + tags);
        System.out.println("   size = " + tags.size() + " (duplicate ignored)");
        System.out.println("   contains(\"backend\") = " + tags.contains("backend"));

        // TreeSet keeps natural (sorted) order.
        Set<Integer> sortedNumbers = new TreeSet<>(List.of(5, 1, 9, 3, 1));
        System.out.println("   TreeSet = " + sortedNumbers);

        // ── 3. Map - key -> value ────────────────────────────────────────
        Map<String, Integer> stock = new HashMap<>();
        stock.put("apple", 50);
        stock.put("banana", 30);
        stock.put("cherry", 20);
        stock.put("apple", 45);                    // replaces the old value

        System.out.println("\n3) map = " + stock);
        System.out.println("   get(\"apple\") = " + stock.get("apple"));
        System.out.println("   getOrDefault(\"kiwi\", 0) = " + stock.getOrDefault("kiwi", 0));
        System.out.println("   containsKey(\"banana\") = " + stock.containsKey("banana"));
        System.out.println("   keySet() = " + stock.keySet());
        System.out.println("   values() = " + stock.values());

        // TreeMap keeps keys sorted.
        Map<String, Integer> sortedStock = new TreeMap<>(stock);
        System.out.println("   TreeMap = " + sortedStock);

        // ── 4. Iterating ─────────────────────────────────────────────────
        System.out.println("\n4) iteration:");
        for (String fruit : fruits) {
            System.out.println("   fruit: " + fruit);
        }
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            System.out.println("   " + entry.getKey() + " = " + entry.getValue());
        }

        // ── 5. Handy factory methods (Java 9+) ───────────────────────────
        List<Integer> fixed = List.of(10, 20, 30);
        Set<String> fixedSet = Set.of("a", "b", "c");
        Map<String, Integer> fixedMap = Map.of("x", 1, "y", 2);

        System.out.println("\n5) immutable factories:");
        System.out.println("   List.of = " + fixed);
        System.out.println("   Set.of  = " + fixedSet);
        System.out.println("   Map.of  = " + fixedMap);
        // fixed.add(40); // UnsupportedOperationException

        // ── 6. Sorting and utility methods ───────────────────────────────
        List<Integer> numbers = new ArrayList<>(List.of(5, 2, 9, 1, 7));
        Collections.sort(numbers);
        System.out.println("\n6) Collections.sort = " + numbers);
        Collections.reverse(numbers);
        System.out.println("   Collections.reverse = " + numbers);
        System.out.println("   Collections.max = " + Collections.max(numbers));
        System.out.println("   Collections.min = " + Collections.min(numbers));

        // ── 7. A realistic example: count words ──────────────────────────
        String sentence = "the quick brown fox jumps over the lazy dog the end";
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : sentence.split(" ")) {
            wordCount.merge(word, 1, Integer::sum);
        }
        System.out.println("\n7) word counts:");
        for (Map.Entry<String, Integer> entry : new TreeMap<>(wordCount).entrySet()) {
            System.out.println("   " + entry.getKey() + " -> " + entry.getValue());
        }

        // ── 8. One-line summary ──────────────────────────────────────────
        // List = ordered + duplicates, Set = unique, Map = key -> value.
        // Prefer the interface type (List<String>) on the left.
    }
}
