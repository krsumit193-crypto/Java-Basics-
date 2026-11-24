package com.javabasics.files;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * ============================================================================
 *  13 - FILES (java.nio.file)
 * ============================================================================
 *  WHAT:  Reading and writing files, folders and paths.
 *  WHY:   Programs must persist data: logs, reports, uploads, configuration.
 *  HOW:   Path.of("data.txt"), Files.writeString, Files.readString,
 *         Files.readAllLines, Files.lines, Files.list, Files.walk.
 *  GOTCHA: Every file operation can throw IOException - always handle it.
 *          Files.lines and Files.walk keep a handle open, so use them inside
 *          try-with-resources or call .toList() immediately.
 * ============================================================================
 */
public final class FilesDemo {

    private FilesDemo() {
    }

    public static void run() {
        // A temporary folder keeps the demo from touching your real files.
        Path workDir = null;
        try {
            workDir = Files.createTempDirectory("java-basics-files-");
            System.out.println("1) working directory:");
            System.out.println("   " + workDir);

            // ── 2. Writing text ──────────────────────────────────────────
            Path notes = workDir.resolve("notes.txt");
            Files.writeString(notes, "first line\nsecond line\nthird line\n", StandardCharsets.UTF_8);
            System.out.println("\n2) wrote " + Files.size(notes) + " bytes to notes.txt");

            // Append instead of overwrite.
            Files.writeString(notes, "fourth line\n", StandardCharsets.UTF_8,
                    java.nio.file.StandardOpenOption.APPEND);

            // ── 3. Reading the whole file ────────────────────────────────
            String content = Files.readString(notes, StandardCharsets.UTF_8);
            System.out.println("\n3) readString:");
            System.out.println(content.indent(3).stripTrailing());

            // ── 4. Reading line by line ──────────────────────────────────
            List<String> lines = Files.readAllLines(notes, StandardCharsets.UTF_8);
            System.out.println("\n4) readAllLines (" + lines.size() + " lines):");
            for (int i = 0; i < lines.size(); i++) {
                System.out.println("   " + (i + 1) + ": " + lines.get(i));
            }

            // ── 5. Streaming a file (good for large files) ───────────────
            long longLines = 0;
            try (Stream<String> stream = Files.lines(notes, StandardCharsets.UTF_8)) {
                longLines = stream.filter(line -> line.length() > 10).count();
            }
            System.out.println("\n5) lines longer than 10 chars = " + longLines);

            // ── 6. Checking what exists ──────────────────────────────────
            System.out.println("\n6) checks:");
            System.out.println("   exists(notes)     = " + Files.exists(notes));
            System.out.println("   isRegularFile     = " + Files.isRegularFile(notes));
            System.out.println("   isDirectory       = " + Files.isDirectory(notes));
            System.out.println("   fileName          = " + notes.getFileName());
            System.out.println("   parent            = " + notes.getParent().getFileName());

            // ── 7. Creating folders and listing them ─────────────────────
            Path reports = Files.createDirectories(workDir.resolve("reports/2026"));
            Files.writeString(reports.resolve("january.csv"), "month,total\nJan,1200\n");

            // `workDir` is reassigned inside the try block, so it is not
            // effectively final - copy it before using it in a lambda.
            Path root = workDir;
            System.out.println("\n7) tree under " + root.getFileName() + ":");
            try (Stream<Path> paths = Files.walk(root)) {
                paths.filter(Files::isRegularFile)
                        .forEach(path -> System.out.println("   " + root.relativize(path)));
            }

            // ── 8. Copying and moving ────────────────────────────────────
            Path backup = workDir.resolve("notes.backup.txt");
            Files.copy(notes, backup, StandardCopyOption.REPLACE_EXISTING);

            Path archived = reports.resolve("notes-archived.txt");
            Files.move(backup, archived, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("\n8) copy + move:");
            System.out.println("   backup still exists? " + Files.exists(backup));
            System.out.println("   archived exists?     " + Files.exists(archived));

            // ── 9. Deleting ──────────────────────────────────────────────
            Files.delete(archived);
            System.out.println("\n9) after delete, archived exists? " + Files.exists(archived));
            System.out.println("   Files.deleteIfExists is safe when the file may be gone.");

            // ── 10. A realistic task: count words in a file ──────────────
            System.out.println("\n10) word count:");
            System.out.println("    notes.txt -> " + countWords(notes) + " words");

        } catch (IOException exception) {
            // In a real program: log it and return a useful error to the caller.
            System.out.println("file operation failed: " + exception.getMessage());
        } finally {
            // ── 11. Clean up the temporary folder ────────────────────────
            if (workDir != null) {
                deleteRecursively(workDir);
                System.out.println("\n11) cleaned up " + workDir.getFileName());
            }
        }

        // ── 12. One-line summary ─────────────────────────────────────────
        // Path = location, Files = operations. Handle IOException, and use
        // try-with-resources for Files.lines / Files.list / Files.walk.
    }

    private static long countWords(Path file) throws IOException {
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            return lines
                    .flatMap(line -> java.util.Arrays.stream(line.trim().split("\\s+")))
                    .filter(word -> !word.isBlank())
                    .count();
        }
    }

    /** Deletes a folder and everything inside it (deepest entries first). */
    private static void deleteRecursively(Path root) {
        try (Stream<Path> paths = Files.walk(root)) {
            paths.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException exception) {
                            System.out.println("   could not delete " + path + ": "
                                    + exception.getMessage());
                        }
                    });
        } catch (IOException exception) {
            System.out.println("   cleanup failed: " + exception.getMessage());
        }
    }
}
