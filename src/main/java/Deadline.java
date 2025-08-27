import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    protected LocalDate due;

    Deadline(String desc, String due) {
        super(desc);
        this.due = LocalDate.parse(due, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String toString() {
        return "[D]" + super.toString() + "(by: " +  this.due.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }

    public String formattedString() {
        return "D" + super.formattedString() + "|" + this.due.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
