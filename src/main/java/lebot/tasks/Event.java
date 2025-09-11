package lebot.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * An event task with a start and end date.
 * <p>
 * Extends {@link Task} by storing a date range and adding an {@code E} type tag
 * to its display and storage formats.
 */
public class Event extends Task {
    /** End date of the event. */
    protected LocalDate to;
    /** Start date of the event. */
    protected LocalDate from;

    /**
     * Creates an event with the given description and date range.
     *
     * <p>Date strings must be in the format {@code dd/MM/yyyy} (e.g., {@code 03/12/2025}).</p>
     *
     * @param desc description of the event
     * @param to   end date string in {@code dd/MM/yyyy} format
     * @param from start date string in {@code dd/MM/yyyy} format
     * @throws java.time.format.DateTimeParseException if either date string cannot be parsed
     */
    public Event(String desc, String to, String from) {
        super(desc);
        assert !Objects.equals(to, "") && !Objects.equals(from, "") : "Date is empty";
        this.to = LocalDate.parse(to, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.from = LocalDate.parse(from, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Returns a string with the event type tag and date range.
     * <p>
     * Dates are shown as {@code MMM d yyyy}, e.g., {@code Jan 1 2025}.
     *
     * @return a string like {@code "[E][ ] Basketball Bootcamp (from: Jan 1 2025 to: Jan 3 2025)"}
     */
    public String toString() {
        return "[E]" + super.toString() + "(from: " + this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ") " + super.formatTags();
    }

    /**
     * Returns a string for storage.
     * <p>
     * Format: {@code E|<0-or-1>|<description>|<to dd/MM/yyyy>|<from dd/MM/yyyy>}.
     *
     * @return the serialized representation of this event
     */
    public String saveString() {
        return "E" + super.saveString() + this.to.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "|"
                + this.from.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
