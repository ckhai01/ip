package lebot.tasks;

import java.util.ArrayList;

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
    protected ArrayList<String> tags;

    /**
     * Creates a new task.
     *
     * @param description the task name/description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.tags = new ArrayList<>();
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
     * Adds a tag to the tag list.
     *
     * @param tag the tag name for the task
     */
    public void addTag(String tag) {
        tags.add(tag);
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

    public String formatString() {
        String done = isDone ? "1" : "0";
        return "|" + done + "|" + description;
    }

    /**
     * Returns a string of tags for display purposes.
     * <p>
     * Subclasses use this method to append to the end.
     *
     * @return a string in the form {@code #fun, #win}
     */
    public String formatTags() {
        if (tags.isEmpty()) {
            return "";
        }
        StringBuilder output = new StringBuilder();
        for (String tag : tags) {
            output.append("#").append(tag).append(", ");
        }
        return output.substring(0, output.length() - 2);
    }

}
