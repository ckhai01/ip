public class ToDo extends Task {
    ToDo(String input) {
        super(input);
    }

    public String toString() {
        return "[T]" + super.toString();
    }

    public String formattedString() {
        return "T" + super.formattedString();
    }
}
