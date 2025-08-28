package lebot.tasks;

/**
 * Base class for all tasks with a text description and a completion flag.
 * <p>
 * This class is extended by {@link Deadline}, {@link Event}, and {@link ToDo}.
 * <p>
 * Provides helpers to mark/unmark completion, show a status icon,
 * and format itself as a string for display and storage.
 *
 * @see Deadline
 * @see Event
 * @see ToDo
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task.
     *
     * @param description the task name/description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns a status icon for display.
     *
     * @return {@code "[✓]"} if done; {@code "[ ]"} otherwise
     */
    public String getStatusIcon() {
        return (isDone ? "[✓]" : "[ ]");
    }

    /**
     * Marks task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks task as not completed.
     */
    public void unmarkAsDone() {
        isDone = false;
    }

    /**
     * Returns a string combining the status icon and description.
     * <p>
     *     Subclasses extend this format to include their task type, like [T] for {@link ToDo}.
     *
     * @return a string like {@code "[ ] Lose"} or {@code "[✓] Win the Finals"}
     */
    public String toString() {
        return getStatusIcon() + " " + description;
    }

    /**
     * Returns a string for storage purposes, delimited by |.
     * <p>
     * Uses {@code "1"} for done and {@code "0"} for not done.
     * Subclasses extend this format to append their own fields, like task Type and DateTime fields.
     *
     * @return a string in the form {@code |<0-or-1>|<description>}
     */
    public String formattedString()
    {
        String done = isDone ? "1" : "0";
        return "|" + done + "|" + description;
    }

}
