package lebot.tasks;

/**
 * A simple to-do task with only a description and completion status.
 * <p>
 * A concrete {@link Task} subtype with the type tag {@code T}.
 */
public class ToDo extends Task {

    /**
     * Creates a new to-do task.
     *
     * @param input the task description/name
     */
    public ToDo(String input) {
        super(input);
    }

    /**
     * Returns a string with the to-do type tag [T].
     *
     * @return a string like {@code "[T][ ] Win"} or {@code "[T][✓] Win"}
     */
    public String toString() {
        return "[T]" + super.toString() + " " + super.formatTags();
    }

    /**
     * Returns a string for storage. Appends type tag T to {@link Task}'s formattedString() method.
     * <p>
     * Format: {@code T|<0-or-1>|<description>} where {@code 1} means done and {@code 0} means not done.
     *
     * @return the serialized representation of this to-do
     */
    public String saveString() {
        return "T" + super.saveString();
    }
}
