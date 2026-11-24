package com.javabasics.datetime;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * ============================================================================
 *  12 - DATE & TIME
 * ============================================================================
 *  WHAT:  The java.time API (Java 8+): LocalDate, LocalTime, LocalDateTime,
 *         ZonedDateTime, Duration, Period and DateTimeFormatter.
 *  WHY:   The old Date/Calendar classes are mutable and confusing. java.time
 *         objects are immutable, thread-safe and clear about time zones.
 *  HOW:   LocalDate.of(2026, 11, 24), plusDays(7), Duration.between(a, b),
 *         DateTimeFormatter.ofPattern("dd MMM yyyy").
 *  GOTCHA: LocalDateTime has no zone, so it cannot represent an instant.
 *          Use Instant or ZonedDateTime for timestamps across regions.
 * ============================================================================
 */
public final class DateTimeDemo {

    private DateTimeDemo() {
    }

    public static void run() {
        // ── 1. Creating dates and times ──────────────────────────────────
        LocalDate today = LocalDate.now();
        LocalDate independenceDay = LocalDate.of(2026, 8, 15);
        LocalTime meeting = LocalTime.of(10, 30);
        LocalDateTime appointment = LocalDateTime.of(2026, 11, 24, 14, 0);

        System.out.println("1) creating:");
        System.out.println("   today            = " + today);
        System.out.println("   independenceDay  = " + independenceDay);
        System.out.println("   meeting          = " + meeting);
        System.out.println("   appointment      = " + appointment);

        // ── 2. Reading the parts ─────────────────────────────────────────
        System.out.println("\n2) parts of today:");
        System.out.println("   year = " + today.getYear()
                + ", month = " + today.getMonth()
                + ", day = " + today.getDayOfMonth());
        System.out.println("   day of week = " + today.getDayOfWeek());
        System.out.println("   day of year = " + today.getDayOfYear());
        System.out.println("   leap year   = " + today.isLeapYear());

        // ── 3. Immutability: every change returns a new object ───────────
        LocalDate nextWeek = today.plusWeeks(1);
        LocalDate yesterday = today.minusDays(1);
        LocalDate firstOfMonth = today.withDayOfMonth(1);

        System.out.println("\n3) immutability:");
        System.out.println("   today        = " + today + " (unchanged)");
        System.out.println("   plusWeeks(1) = " + nextWeek);
        System.out.println("   minusDays(1) = " + yesterday);
        System.out.println("   withDayOfMonth(1) = " + firstOfMonth);

        // ── 4. Comparing dates ───────────────────────────────────────────
        System.out.println("\n4) comparison:");
        System.out.println("   today.isAfter(independenceDay)  = " + today.isAfter(independenceDay));
        System.out.println("   today.isBefore(independenceDay) = " + today.isBefore(independenceDay));
        System.out.println("   today.equals(today)             = " + today.equals(LocalDate.now()));

        // ── 5. Period: a date-based amount (years, months, days) ─────────
        LocalDate birthDate = LocalDate.of(2003, 5, 17);
        Period age = Period.between(birthDate, today);

        System.out.println("\n5) Period:");
        System.out.println("   age = " + age.getYears() + " years, "
                + age.getMonths() + " months, " + age.getDays() + " days");
        System.out.println("   total days = " + ChronoUnit.DAYS.between(birthDate, today));

        // ── 6. Duration: a time-based amount (hours, minutes, seconds) ───
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 30);
        Duration workday = Duration.between(start, end);

        System.out.println("\n6) Duration:");
        System.out.println("   workday = " + workday);
        System.out.println("   hours   = " + workday.toHours()
                + ", minutes = " + workday.toMinutes());

        // ── 7. Formatting for humans ─────────────────────────────────────
        DateTimeFormatter indian = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter pretty = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        DateTimeFormatter withTime = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

        System.out.println("\n7) formatting:");
        System.out.println("   dd-MM-yyyy          : " + today.format(indian));
        System.out.println("   EEEE, dd MMMM yyyy  : " + today.format(pretty));
        System.out.println("   with time           : " + appointment.format(withTime));

        // ── 8. Parsing text into dates ───────────────────────────────────
        LocalDate parsed = LocalDate.parse("15-08-2026", indian);
        LocalTime parsedTime = LocalTime.parse("18:45");

        System.out.println("\n8) parsing:");
        System.out.println("   \"15-08-2026\" -> " + parsed);
        System.out.println("   \"18:45\"      -> " + parsedTime);

        // ── 9. Time zones ────────────────────────────────────────────────
        ZonedDateTime india = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        ZonedDateTime utc = india.withZoneSameInstant(ZoneId.of("UTC"));

        System.out.println("\n9) zones:");
        System.out.println("   India : " + india.format(withTime));
        System.out.println("   UTC   : " + utc.format(withTime));
        System.out.println("   same instant? " + india.toInstant().equals(utc.toInstant()));

        // ── 10. Instant: a machine timestamp ─────────────────────────────
        Instant now = Instant.now();
        System.out.println("\n10) Instant.now() = " + now);
        System.out.println("    epoch millis   = " + now.toEpochMilli());

        // ── 11. A realistic example: next working day ────────────────────
        System.out.println("\n11) next working day after " + today + " = " + nextWorkingDay(today));

        // ── 12. One-line summary ─────────────────────────────────────────
        // LocalDate = date, LocalTime = time, LocalDateTime = both, no zone.
        // Period = dates, Duration = times. java.time objects are immutable.
    }

    /** Skips Saturday and Sunday. */
    private static LocalDate nextWorkingDay(LocalDate date) {
        LocalDate next = date.plusDays(1);
        while (next.getDayOfWeek() == DayOfWeek.SATURDAY
                || next.getDayOfWeek() == DayOfWeek.SUNDAY) {
            next = next.plusDays(1);
        }
        return next;
    }
}
