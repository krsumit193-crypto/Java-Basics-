package com.javabasics.practice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ============================================================================
 *  15 - PRACTICE PROBLEMS
 * ============================================================================
 *  Classic interview-style questions, each solved with plain Java. Try to
 *  solve them yourself first, then compare with the implementation below.
 *
 *    1. FizzBuzz
 *    2. Reverse a string
 *    3. Palindrome check
 *    4. Prime check
 *    5. Factorial (loop + recursion)
 *    6. Fibonacci
 *    7. Sum of digits
 *    8. Armstrong number
 *    9. Anagram check
 *   10. Character frequency
 *   11. Second largest number
 *   12. Binary search
 *   13. Bubble sort
 *   14. Pyramid pattern
 * ============================================================================
 */
public final class PracticeProblems {

    private PracticeProblems() {
    }

    public static void run() {
        // ── 1. FizzBuzz ──────────────────────────────────────────────────
        // Multiples of 3 -> Fizz, of 5 -> Buzz, of both -> FizzBuzz.
        System.out.println("1) FizzBuzz (1..15):");
        System.out.println("   " + fizzBuzz(15));

        // ── 2. Reverse a string ──────────────────────────────────────────
        String text = "Java Basics";
        System.out.println("\n2) reverse:");
        System.out.println("   \"" + text + "\" -> \"" + reverse(text) + "\"");
        System.out.println("   using StringBuilder -> \"" + new StringBuilder(text).reverse() + "\"");

        // ── 3. Palindrome ────────────────────────────────────────────────
        System.out.println("\n3) palindrome:");
        for (String candidate : List.of("madam", "Java", "A man a plan a canal Panama")) {
            System.out.println("   \"" + candidate + "\" -> " + isPalindrome(candidate));
        }

        // ── 4. Prime check ───────────────────────────────────────────────
        System.out.println("\n4) primes up to 30: " + primesUpTo(30));

        // ── 5. Factorial ─────────────────────────────────────────────────
        System.out.println("\n5) factorial:");
        System.out.println("   5! (loop)      = " + factorialLoop(5));
        System.out.println("   10! (recursion)= " + factorialRecursive(10));

        // ── 6. Fibonacci ─────────────────────────────────────────────────
        System.out.println("\n6) first 10 Fibonacci numbers: " + fibonacci(10));

        // ── 7. Sum of digits ─────────────────────────────────────────────
        System.out.println("\n7) digit sum:");
        System.out.println("   sumDigits(9875)   = " + sumDigits(9875));
        System.out.println("   digitalRoot(9875) = " + digitalRoot(9875));

        // ── 8. Armstrong number ──────────────────────────────────────────
        // 153 = 1^3 + 5^3 + 3^3
        System.out.println("\n8) Armstrong numbers below 1000: " + armstrongNumbers(1000));

        // ── 9. Anagram ───────────────────────────────────────────────────
        System.out.println("\n9) anagram:");
        System.out.println("   listen / silent -> " + isAnagram("listen", "silent"));
        System.out.println("   java / python   -> " + isAnagram("java", "python"));

        // ── 10. Character frequency ──────────────────────────────────────
        System.out.println("\n10) character frequency of \"banana\":");
        System.out.println("    " + charFrequency("banana"));

        // ── 11. Second largest ───────────────────────────────────────────
        int[] numbers = {5, 9, 2, 9, 7, 1};
        System.out.println("\n11) second largest in " + Arrays.toString(numbers)
                + " = " + secondLargest(numbers));

        // ── 12. Binary search (array must be sorted) ─────────────────────
        int[] sorted = {1, 3, 5, 7, 9, 11, 13};
        System.out.println("\n12) binary search in " + Arrays.toString(sorted) + ":");
        System.out.println("    index of 7  = " + binarySearch(sorted, 7));
        System.out.println("    index of 4  = " + binarySearch(sorted, 4) + " (not found)");

        // ── 13. Bubble sort ──────────────────────────────────────────────
        int[] unsorted = {5, 2, 9, 1, 7};
        System.out.println("\n13) bubble sort " + Arrays.toString(unsorted)
                + " -> " + Arrays.toString(bubbleSort(unsorted)));

        // ── 14. Pyramid pattern ──────────────────────────────────────────
        System.out.println("\n14) pyramid:");
        pyramid(5);

        // ── 15. One-line summary ─────────────────────────────────────────
        // Most interview problems are loops + conditions + a HashMap or array.
        // Read the constraints first, then pick the simplest correct approach.
    }

    // ── Solutions ───────────────────────────────────────────────────────

    private static List<String> fizzBuzz(int limit) {
        List<String> output = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            if (i % 15 == 0) {
                output.add("FizzBuzz");
            } else if (i % 3 == 0) {
                output.add("Fizz");
            } else if (i % 5 == 0) {
                output.add("Buzz");
            } else {
                output.add(String.valueOf(i));
            }
        }
        return output;
    }

    private static String reverse(String text) {
        char[] chars = text.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        return new String(chars);
    }

    private static boolean isPalindrome(String text) {
        // Keep only letters/digits and ignore case, so "A man a plan..." works.
        String cleaned = text.toLowerCase().replaceAll("[^a-z0-9]", "");
        int left = 0;
        int right = cleaned.length() - 1;
        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        if (number % 2 == 0) {
            return number == 2;
        }
        // Only check odd divisors up to the square root.
        for (int divisor = 3; (long) divisor * divisor <= number; divisor += 2) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    private static List<Integer> primesUpTo(int limit) {
        List<Integer> primes = new ArrayList<>();
        for (int candidate = 2; candidate <= limit; candidate++) {
            if (isPrime(candidate)) {
                primes.add(candidate);
            }
        }
        return primes;
    }

    private static long factorialLoop(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private static long factorialRecursive(int n) {
        return n <= 1 ? 1 : n * factorialRecursive(n - 1);
    }

    private static List<Long> fibonacci(int count) {
        List<Long> series = new ArrayList<>();
        long previous = 0;
        long current = 1;
        for (int i = 0; i < count; i++) {
            series.add(previous);
            long next = previous + current;
            previous = current;
            current = next;
        }
        return series;
    }

    private static int sumDigits(int number) {
        int total = 0;
        int remaining = Math.abs(number);
        while (remaining > 0) {
            total += remaining % 10;
            remaining /= 10;
        }
        return total;
    }

    private static int digitalRoot(int number) {
        int value = Math.abs(number);
        while (value >= 10) {
            value = sumDigits(value);
        }
        return value;
    }

    private static List<Integer> armstrongNumbers(int limit) {
        List<Integer> found = new ArrayList<>();
        for (int number = 1; number < limit; number++) {
            int digits = String.valueOf(number).length();
            int sum = 0;
            int remaining = number;
            while (remaining > 0) {
                int digit = remaining % 10;
                sum += (int) Math.pow(digit, digits);
                remaining /= 10;
            }
            if (sum == number) {
                found.add(number);
            }
        }
        return found;
    }

    private static boolean isAnagram(String first, String second) {
        if (first.length() != second.length()) {
            return false;
        }
        char[] left = first.toLowerCase().toCharArray();
        char[] right = second.toLowerCase().toCharArray();
        Arrays.sort(left);
        Arrays.sort(right);
        return Arrays.equals(left, right);
    }

    private static Map<Character, Integer> charFrequency(String text) {
        Map<Character, Integer> frequency = new HashMap<>();
        for (char character : text.toCharArray()) {
            frequency.merge(character, 1, Integer::sum);
        }
        return frequency;
    }

    private static int secondLargest(int[] numbers) {
        int largest = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;
        for (int number : numbers) {
            if (number > largest) {
                second = largest;
                largest = number;
            } else if (number > second && number != largest) {
                second = number;
            }
        }
        return second;
    }

    private static int binarySearch(int[] sorted, int target) {
        int low = 0;
        int high = sorted.length - 1;
        while (low <= high) {
            int middle = low + (high - low) / 2;   // avoids overflow
            if (sorted[middle] == target) {
                return middle;
            }
            if (sorted[middle] < target) {
                low = middle + 1;
            } else {
                high = middle - 1;
            }
        }
        return -1;
    }

    private static int[] bubbleSort(int[] numbers) {
        int[] result = numbers.clone();   // do not mutate the caller's array
        for (int pass = 0; pass < result.length - 1; pass++) {
            boolean swapped = false;
            for (int i = 0; i < result.length - 1 - pass; i++) {
                if (result[i] > result[i + 1]) {
                    int temp = result[i];
                    result[i] = result[i + 1];
                    result[i + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;   // already sorted
            }
        }
        return result;
    }

    private static void pyramid(int height) {
        for (int row = 1; row <= height; row++) {
            String spaces = " ".repeat(height - row);
            String stars = "*".repeat(2 * row - 1);
            System.out.println("    " + spaces + stars);
        }
    }
}
