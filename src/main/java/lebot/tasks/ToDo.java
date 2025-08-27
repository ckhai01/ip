package lebot.tasks;

public class ToDo extends Task {
    public ToDo(String input) {
        super(input);
    }

    public String toString() {
        return "[T]" + super.toString();
    }

    public String formattedString() {
        return "T" + super.formattedString();
    }
}
