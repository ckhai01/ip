package lebot.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate to;
    protected LocalDate from;

    public Event(String desc, String to, String from) {
        super(desc);
        this.to = LocalDate.parse(to, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.from = LocalDate.parse(from, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String toString() {
        return "[E]" + super.toString() + "(from: " + this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy")) +
                " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    public String formattedString() {
        return "E" + super.formattedString() + "|" + this.to.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "|"
                + this.from.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
