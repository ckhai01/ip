public class Event extends Task {
    protected String to;
    protected String from;

    Event(String desc, String to, String from) {
        super(desc);
        this.to = to;
        this.from = from;
    }

    public String toString() {
        return "[E]" + super.toString() + "(from: " +  this.from + " to: " +  this.to + ")";
    }

    public String formattedString() {
        return "E" + super.formattedString() + "|" + this.to + "|" + this.from;
    }
}
