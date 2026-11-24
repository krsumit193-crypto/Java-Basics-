package com.javabasics;

import com.javabasics.arrays.ArraysDemo;
import com.javabasics.basics.DataTypes;
import com.javabasics.basics.Operators;
import com.javabasics.basics.TypeConversion;
import com.javabasics.basics.Variables;
import com.javabasics.collections.CollectionsDemo;
import com.javabasics.concurrency.ConcurrencyDemo;
import com.javabasics.controlflow.Conditions;
import com.javabasics.datetime.DateTimeDemo;
import com.javabasics.exceptions.ExceptionsDemo;
import com.javabasics.files.FilesDemo;
import com.javabasics.functional.FunctionalDemo;
import com.javabasics.generics.GenericsDemo;
import com.javabasics.loops.Loops;
import com.javabasics.methods.Methods;
import com.javabasics.oop.OopDemo;
import com.javabasics.practice.PracticeProblems;
import com.javabasics.strings.StringsDemo;

import java.util.List;

/**
 * ============================================================================
 *  Main - the "run every lesson" entry point
 * ============================================================================
 *
 *  WHAT:  Runs each topic in order and prints a banner before it.
 *  WHY:   One command gives a complete tour of the repository.
 *  HOW:   Every lesson exposes a static {@code run()} method. We store them in
 *         a list of records and invoke them one by one.
 *
 *  RUN EVERYTHING
 *  --------------
 *      mvn -q compile
 *      java -cp target/classes com.javabasics.Main
 *
 *  RUN ONE TOPIC (the argument is matched against the key, case-insensitive)
 *  ----------------------------------------------------------------------
 *      java -cp target/classes com.javabasics.Main collections
 *
 *  OR BUILD A JAR
 *  --------------
 *      mvn -q package
 *      java -jar target/java-basics.jar
 * ============================================================================
 */
public final class Main {

    /** A single lesson: a short key, a display title and the code to run. */
    private record Lesson(String key, String title, Runnable action) {
    }

    private static final List<Lesson> LESSONS = List.of(
            new Lesson("variables", "01 - Variables", Variables::run),
            new Lesson("types", "01 - Data types", DataTypes::run),
            new Lesson("operators", "01 - Operators", Operators::run),
            new Lesson("conversion", "01 - Type conversion", TypeConversion::run),
            new Lesson("conditions", "02 - Conditions", Conditions::run),
            new Lesson("loops", "03 - Loops", Loops::run),
            new Lesson("methods", "04 - Methods", Methods::run),
            new Lesson("arrays", "05 - Arrays", ArraysDemo::run),
            new Lesson("strings", "07 - Strings", StringsDemo::run),
            new Lesson("collections", "06 - Collections", CollectionsDemo::run),
            new Lesson("oop", "08 - Object-oriented Java", OopDemo::run),
            new Lesson("exceptions", "09 - Exceptions", ExceptionsDemo::run),
            new Lesson("generics", "10 - Generics", GenericsDemo::run),
            new Lesson("functional", "11 - Lambdas & streams", FunctionalDemo::run),
            new Lesson("datetime", "12 - Date & time", DateTimeDemo::run),
            new Lesson("files", "13 - Files", FilesDemo::run),
            new Lesson("concurrency", "14 - Concurrency", ConcurrencyDemo::run),
            new Lesson("practice", "15 - Practice problems", PracticeProblems::run)
    );

    private static final String LINE = "=".repeat(74);

    private Main() {
        // Utility class - never instantiated.
    }

    public static void main(String[] args) {
        String filter = args.length > 0 ? args[0].trim().toLowerCase() : null;

        List<Lesson> selected = filter == null
                ? LESSONS
                : LESSONS.stream()
                        .filter(lesson -> lesson.key().contains(filter)
                                || lesson.title().toLowerCase().contains(filter))
                        .toList();

        if (selected.isEmpty()) {
            System.out.println("No lesson matched \"" + args[0] + "\".");
            System.out.println("Available keys: "
                    + LESSONS.stream().map(Lesson::key).toList());
            return;
        }

        System.out.println();
        System.out.println(LINE);
        System.out.println("  Java-Basics-  |  topic-wise Java lessons");
        System.out.println("  " + selected.size() + " lesson(s) selected");
        System.out.println(LINE);

        for (Lesson lesson : selected) {
            System.out.println();
            System.out.println();
            System.out.println(LINE);
            System.out.println("  > " + lesson.title());
            System.out.println(LINE);
            System.out.println();
            lesson.action().run();
        }

        System.out.println();
        System.out.println();
        System.out.println(LINE);
        System.out.println("  Done. " + selected.size() + " lesson(s) finished.");
        System.out.println(LINE);
        System.out.println();
    }
}
