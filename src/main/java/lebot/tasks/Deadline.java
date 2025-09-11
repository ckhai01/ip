package lebot.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * A deadline task with a single due date.
 * <p>
 * Extends {@link Task} by storing a due date and adding a {@code D} type tag
 * to its display and storage formats.
 */
public class Deadline extends Task {
    /** Due date for the task. */
    protected LocalDate due;

    /**
     * Creates a deadline with the given description and due date.
     *
     * <p>Date string must be in the format {@code dd/MM/yyyy} (e.g., {@code 03/11/2025}).</p>
     *
     * @param desc description of the deadline
     * @param due  due date string in {@code dd/MM/yyyy} format
     * @throws java.time.format.DateTimeParseException if the date string cannot be parsed
     */
    public Deadline(String desc, String due) {
        super(desc);
        assert !Objects.equals(due, "") : "Date is empty";
        this.due = LocalDate.parse(due, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Returns a string with the deadline type tag and due date.
     * <p>
     * Date is shown as {@code MMM d yyyy}, e.g., {@code Jan 1 2025}.
     *
     * @return a string like {@code "[D][ ] Submit report (by: Jan 1 2025)"}
     */
    public String toString() {
        return "[D]" + super.toString() + "(by: " + this.due.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ") "
                + super.formatTags();
    }

    /**
     * Returns a string suitable storage.
     * <p>
     * Format: {@code D|<0-or-1>|<description>|<due dd/MM/yyyy>}.
     *
     * @return the serialized representation of this deadline
     */
    public String formatString() {
        return "D" + super.formatString() + "|" + this.due.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
