# Java-Basics-

> **Learn Java by reading code that runs.** 18 topic-wise lessons, heavily commented, one command to run them all.

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/build-Maven-blue?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)
[![PRs welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](#contributing)

This repository is a **guided tour of core Java**, written for people who learn best by reading real, runnable code. Every lesson:

- lives in its own topic folder,
- is numbered and explained with inline comments,
- prints its results, so you can see what the code actually does,
- ends with a **one-line summary** you can revise before an interview.

No frameworks, no magic, no hidden setup — just the JDK and Maven.

---

## Quick start

```bash
# 1. Clone
git clone https://github.com/krsumit193-crypto/Java-Basics-.git
cd Java-Basics-

# 2. Compile
mvn -q compile

# 3. Run every lesson
java -cp target/classes com.javabasics.Main

# 4. Or run a single topic (case-insensitive, partial match works)
java -cp target/classes com.javabasics.Main collections
java -cp target/classes com.javabasics.Main exceptions
java -cp target/classes com.javabasics.Main practice
```

Prefer a single jar?

```bash
mvn -q package
java -jar target/java-basics.jar
```

**Requirements:** JDK 21 or newer (the code uses records, sealed types, text blocks, switch expressions and virtual threads) and Maven 3.9+.

---

## Topics

| # | Topic | Folder | What you will learn |
|---|-------|--------|---------------------|
| 01 | Variables | [`basics/Variables.java`](src/main/java/com/javabasics/basics/Variables.java) | declaration, `final`, `var`, scope, naming rules |
| 01 | Data types | [`basics/DataTypes.java`](src/main/java/com/javabasics/basics/DataTypes.java) | 8 primitives, wrappers, casting, overflow |
| 01 | Operators | [`basics/Operators.java`](src/main/java/com/javabasics/basics/Operators.java) | arithmetic, comparison, `==` vs `equals`, short-circuiting |
| 01 | Type conversion | [`basics/TypeConversion.java`](src/main/java/com/javabasics/basics/TypeConversion.java) | widening, narrowing, parsing, formatting |
| 02 | Conditions | [`controlflow/Conditions.java`](src/main/java/com/javabasics/controlflow/Conditions.java) | `if`/`else`, `switch`, switch expressions, guards |
| 03 | Loops | [`loops/Loops.java`](src/main/java/com/javabasics/loops/Loops.java) | `for`, `while`, `do-while`, for-each, labels |
| 04 | Methods | [`methods/Methods.java`](src/main/java/com/javabasics/methods/Methods.java) | overloading, varargs, recursion, pass-by-value |
| 05 | Arrays | [`arrays/ArraysDemo.java`](src/main/java/com/javabasics/arrays/ArraysDemo.java) | 1D/2D arrays, `Arrays` helpers, bounds |
| 06 | Collections | [`collections/CollectionsDemo.java`](src/main/java/com/javabasics/collections/CollectionsDemo.java) | `List`, `Set`, `Map`, sorting, word counts |
| 07 | Strings | [`strings/StringsDemo.java`](src/main/java/com/javabasics/strings/StringsDemo.java) | immutability, `String` methods, `StringBuilder`, text blocks |
| 08 | OOP | [`oop/OopDemo.java`](src/main/java/com/javabasics/oop/OopDemo.java) | encapsulation, inheritance, interfaces, records, sealed types |
| 09 | Exceptions | [`exceptions/ExceptionsDemo.java`](src/main/java/com/javabasics/exceptions/ExceptionsDemo.java) | `try`/`catch`/`finally`, checked vs unchecked, custom exceptions |
| 10 | Generics | [`generics/GenericsDemo.java`](src/main/java/com/javabasics/generics/GenericsDemo.java) | generic classes/methods, bounds, wildcards, erasure |
| 11 | Lambdas & streams | [`functional/FunctionalDemo.java`](src/main/java/com/javabasics/functional/FunctionalDemo.java) | functional interfaces, streams, collectors, `Optional` |
| 12 | Date & time | [`datetime/DateTimeDemo.java`](src/main/java/com/javabasics/datetime/DateTimeDemo.java) | `java.time`, `Period`, `Duration`, formatting, zones |
| 13 | Files | [`files/FilesDemo.java`](src/main/java/com/javabasics/files/FilesDemo.java) | `Path`, read/write, walk, copy/move/delete |
| 14 | Concurrency | [`concurrency/ConcurrencyDemo.java`](src/main/java/com/javabasics/concurrency/ConcurrencyDemo.java) | threads, race conditions, atomics, executors, virtual threads |
| 15 | Practice | [`practice/PracticeProblems.java`](src/main/java/com/javabasics/practice/PracticeProblems.java) | FizzBuzz, primes, palindromes, anagrams, binary search, sorting |

---

## Sample output

```text
==========================================================================
  Java-Basics-  |  topic-wise Java lessons
  18 lesson(s) selected
==========================================================================

==========================================================================
  > 06 - Collections
==========================================================================

1) list = [apple, banana, cherry, apple]
   size = 4
   get(1) = banana
   contains("banana") = true
...
7) word counts:
   brown -> 1
   dog -> 1
   fox -> 1
   the -> 3
```

Every lesson prints in the same style: **numbered section → explanation → output → one-line summary**.

---

## Project structure

```text
Java-Basics-/
├── pom.xml                     # Maven build, Java 21, runnable jar
├── src/main/java/com/javabasics/
│   ├── Main.java               # runs all lessons, or one by name
│   ├── basics/                 # variables, data types, operators, conversion
│   ├── controlflow/            # if/else, switch
│   ├── loops/                  # for, while, do-while, for-each
│   ├── methods/                # overloading, varargs, recursion
│   ├── arrays/                 # 1D, 2D, jagged arrays
│   ├── collections/            # List, Set, Map
│   ├── strings/                # String, StringBuilder, text blocks
│   ├── oop/                    # classes, inheritance, interfaces, records
│   ├── exceptions/             # try/catch, custom exceptions
│   ├── generics/               # generic types, wildcards
│   ├── functional/             # lambdas, streams, Optional
│   ├── datetime/               # java.time
│   ├── files/                  # java.nio.file
│   ├── concurrency/            # threads, executors, CompletableFuture
│   └── practice/               # interview-style problems
├── LICENSE
└── README.md
```

---

## How to read a lesson

Each file follows the same shape, so you can skim or study:

```java
/**
 *  WHAT:  what the topic is
 *  WHY:   why it matters
 *  HOW:   the syntax in one line
 *  GOTCHA: the mistake everyone makes
 */
public final class Something {
    public static void run() {
        // ── 1. A small, focused idea ─────────────────────
        // ── 2. Another idea, with its output ─────────────
        // ── 9. One-line summary ──────────────────────────
    }
}
```

Comments explain **why** a line exists, not just what it does. Outputs are real: run the lesson and compare.

---

## Suggested learning path

1. **Week 1 — syntax:** variables → data types → operators → conversion → conditions → loops.
2. **Week 2 — structure:** methods → arrays → strings → collections.
3. **Week 3 — design:** OOP → exceptions → generics.
4. **Week 4 — modern Java:** lambdas & streams → date/time → files → concurrency.
5. **Week 5 — practice:** solve the problems before reading the solutions.

If you already know another language, read `basics/` quickly and spend your time on `oop/`, `functional/` and `concurrency/`.

---

## Add your own lesson

1. Create a class in an existing package (or a new one) with a `public static void run()` method.
2. Register it in `Main.java`:

```java
new Lesson("mytopic", "16 - My topic", MyTopic::run)
```

3. Run it:

```bash
mvn -q compile
java -cp target/classes com.javabasics.Main mytopic
```

Keep the banner comment, number the sections, print something useful, and finish with a one-line summary. That is the whole convention.

---

## Contributing

Small, focused pull requests are welcome:

- fix a confusing explanation or a wrong output,
- add a gotcha you personally hit,
- add a practice problem with a clean solution,
- improve formatting without changing behaviour.

Please run `mvn -q compile` before opening a PR.

---

## License

[MIT](LICENSE) — use it, fork it, teach with it.
