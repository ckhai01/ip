package lebot.tasks;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "[✓]" : "[ ]");
    }

    public void markAsDone() {
        isDone = true;
    }

    public void unmarkAsDone() {
        isDone = false;
    }

    public String toString() {
        return getStatusIcon() + " " + description;
    }

    public String formattedString()
    {
        String done = isDone ? "1" : "0";
        return "|" + done + "|" + description;
    }

}