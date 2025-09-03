package lebot.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lebot.storage.Storage;
import lebot.ui.Ui;

/**
 * Mutable list of {@link Task} objects with helpers for creating,
 * updating, and deleting tasks based on user input.
 * <p>
 * Persists changes via {@link Storage} and reports outcomes via {@link Ui}.
 * Supports {@link ToDo}, {@link Deadline}, and {@link Event}.
 */
public class TaskList {
    private static final Pattern DEADLINE_BY = Pattern.compile("/by (\\S+)");
    /** Extracts {@code /to <dd/MM/yyyy>} from an event description. */
    private static final Pattern EVENT_TO = Pattern.compile("/to (\\S+)");
    /** Extracts {@code /from <dd/MM/yyyy>} from an event description. */
    private static final Pattern EVENT_FROM = Pattern.compile("/from (\\S+)");
    protected ArrayList<Task> list;

    /**
     * Loads the task list from persistent storage (default: data/LeBot.txt).
     */
    public TaskList() {
        this.list = Storage.loadList();
    }

    /**
     * Creates a task list backed by the provided list (no copy is made).
     *
     * @param list the underlying list of tasks
     */
    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    /**
     * Converts a 1-based index string (e.g., from user input) to a 0-based index.
     *
     * @param desc the 1-based index as a string
     * @return the 0-based integer index
     * @throws NumberFormatException if {@code desc} is not a valid integer
     */
    private static int parseIndex(String desc) {
        return Integer.parseInt(desc) - 1;
    }

    /**
     * Finds the first regex group match in the given text.
     *
     * @param pattern the compiled pattern
     * @param text    the text to search
     * @return the first captured group if found; {@code null} otherwise
     */

    private static String findGroup(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * Returns the number of tasks.
     *
     * @return task count
     */
    public int getSize() {
        return list.size();
    }

    /**
     * Returns whether the list has no tasks.
     *
     * @return {@code true} if empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Gets the task at a 0-based index.
     *
     * @param index 0-based index
     * @return the task at {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is out of range
     */
    public Task get(int index) {
        return list.get(index);
    }


    /**
     * Marks the task at the 1-based index given by {@code desc} as done.
     * Saves the list and shows feedback via {@link Ui}.
     * <p>
     * Handles invalid numbers or out-of-bounds indices by showing error messages.
     *
     * @param desc 1-based index as a string (e.g., {@code "2"})
     * @return Confirmation message
     */
    public String markTask(String desc) {
        try {
            int number = parseIndex(desc);
            this.list.get(number).markAsDone();
            Storage.saveList(this.list);
            return Ui.showMark(this.list.get(number));
        } catch (NumberFormatException e) {
            return Ui.showNumberError();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return Ui.showBoundsError();
        }
    }

    /**
     * Marks the task at the 1-based index given by {@code desc} as not done.
     * Saves the list and shows feedback via {@link Ui}.
     * <p>
     * Handles invalid numbers or out-of-bounds indices by showing error messages.
     *
     * @param desc 1-based index as a string
     * @return Confirmation message
     */
    public String unmarkTask(String desc) {
        try {
            int number = parseIndex(desc);
            this.list.get(number).unmarkAsDone();
            Storage.saveList(this.list);
            return Ui.showUnmark(this.list.get(number));
        } catch (NumberFormatException e) {
            return Ui.showNumberError();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return Ui.showBoundsError();
        }
    }

    /**
     * Adds a task, saves the list, and shows a confirmation.
     *
     * @param task the task to add
     * @return Confirmation message
     */
    public String add(Task task) {
        this.list.add(task);
        Storage.saveList(this.list);
        return Ui.showAdd(task, this.getSize());
    }

    /**
     * Deletes the task at the 1-based index given by {@code desc}.
     * Saves the list and shows feedback via {@link Ui}.
     * <p>
     * Handles invalid numbers or out-of-bounds indices by showing error messages.
     *
     * @param desc 1-based index as a string
     * @return Confirmation message
     */
    public String delete(String desc) {
        try {
            int number = parseIndex(desc);
            Task tempTask = this.list.get(number);
            this.list.remove(number);
            Storage.saveList(list);
            return Ui.showDelete(tempTask, this.getSize());

        } catch (NumberFormatException e) {
            return Ui.showNumberError();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return Ui.showBoundsError();
        }
    }

    /**
     * Creates and adds a {@link ToDo} from the given description.
     * If the description is empty, shows an error via {@link Ui}.
     *
     * @param desc description text
     * @return Confirmation message
     */
    public String createTodo(String desc) {
        if (desc.isEmpty()) {
            return Ui.showEmptyTodo();
        }
        ToDo todo = new ToDo(desc);
        return this.add(todo);
    }

    /**
     * Creates and adds a {@link Deadline} parsed from the description.
     * <p>
     * Expected format includes {@code /by dd/MM/yyyy}, e.g.:
     * {@code "Submit report /by 31/12/2025"}.
     * Shows errors via {@link Ui} if the date is missing or invalid.
     *
     * @param desc full user input containing description and {@code /by} date
     * @return Confirmation message
     */
    public String createDeadline(String desc) {
        String match = findGroup(DEADLINE_BY, desc);
        try {
            if (match != null) {
                LocalDate.parse(match, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Deadline deadline = new Deadline(desc.replace("/by " + match, ""), match);
                return this.add(deadline);
            } else {
                return Ui.showMissingDeadline();
            }
        } catch (DateTimeParseException e) {
            return Ui.showInvalidDate();
        }

    }

    /**
     * Creates and adds an {@link Event} parsed from the description.
     * <p>
     * Expected format includes both {@code /from dd/MM/yyyy} and {@code /to dd/MM/yyyy}, e.g.:
     * {@code "Conference /from 01/01/2025 /to 03/01/2025"}.
     * Shows errors via {@link Ui} if dates are missing or invalid.
     *
     * @param desc full user input containing description, {@code /from}, and {@code /to} dates
     * @return Confirmation message
     */
    public String createEvent(String desc) {
        String to = findGroup(EVENT_TO, desc);
        String from = findGroup(EVENT_FROM, desc);

        try {
            if (to != null && from != null) {
                LocalDate.parse(to, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate.parse(from, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String newDesc = desc.replace("/to " + to, "").replace("/from " + from, "");
                Event event = new Event(newDesc, to, from);
                return this.add(event);
            } else {
                return Ui.showMissingEventTimes();
            }
        } catch (DateTimeParseException e) {
            return Ui.showInvalidDate();
        }


    }

    /**
     * Finds the tasks containing a given keyword and displays them.
     *
     * @param desc the keyword to find
     * @return Confirmation message
     */
    public String findTasks(String desc) {
        ArrayList<Task> output = new ArrayList<>();
        for (Task task : this.list) {
            if (task.description.contains(desc)) {
                output.add(task);
            }
        }
        return Ui.showFind(new TaskList(output));

    }


}
