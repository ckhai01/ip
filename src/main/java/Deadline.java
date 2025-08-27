public class Deadline extends Task {
    protected String due;

    Deadline(String desc, String due) {
        super(desc);
        this.due = due;
    }

    public String toString() {
        return "[D]" + super.toString() + "(by: " +  this.due + ")";
    }

    public String formattedString() {
        return "D" + super.formattedString() + "|" + this.due;
    }
}
