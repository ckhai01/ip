package lebot.ui;

import lebot.tasks.Task;
import lebot.tasks.TaskList;

/**
 * Simple class to handle command line output.
 * <p>
 *     Returns pre-configured strings. Each individual method is trivial.
 */

public class Ui {

    public static String showIntro() {
        return "Yo, what’s good! It's LeBot James in the building! What can I help you with today? Let's get it!";
    }

    /**
     * Formats and returns a string of all tasks in a given TaskList
     *
     * @param tasks the list of tasks
     * @return the string of all tasks. Custom string is returned if TaskList is empty
     */
    public static String showList(TaskList tasks) {
        if (tasks.isEmpty()) {
            return "Haven’t added anything yet? Can’t win a game if you don’t put the ball in play. "
                    + "Gotta set the goals before you chase them.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Here’s the list. No excuses, no shortcuts. One by one, we knock these down.");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append("\n").append(i + 1).append(".").append(tasks.get(i));
        }
        return sb.toString();
    }

    /**
     * Formats and returns a string of found tasks
     *
     * @param tasks the filtered list of tasks
     * @return the string of all tasks. Custom string is returned if TaskList is empty
     */
    public static String showFind(TaskList tasks) {
        if (tasks.isEmpty()) {
            return "Scouting report: no matching tasks on your list. Reset, refocus, run it back.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Game plan update: here are the matching tasks in your list.");
        for (int i = 0; i < tasks.getSize(); i++) {
            sb.append("\n").append(i + 1).append(".").append(tasks.get(i));
        }
        return sb.toString();
    }

    public static String showAdd(Task task, int size) {
        return "Got it. Next task on the list: \n" + task + "\n" + size + " tasks on the board. Lock in.";
    }

    /**
     * Formats and returns a string of all tasks in a given TaskList
     *
     * @param task the deleted task
     * @param size the number of tasks after deletion
     * @return confirmation message of task deletion
     */
    public static String showDelete(Task task, int size) {
        return "Scratched it off the list. Recenter yourself: "
                + task + "\n" + "Now you have " + size + " tasks on the board.";
    }

    public static String showMark(Task task) {
        return "Checked it off the list. Another step closer to greatness: " + task;
    }

    public static String showUnmark(Task task) {
        return "Alright, not done yet. Back in the lab, time to finish the job: " + task;
    }

    public static String showBye() {
        return "Ayy, take care! Hope to see you soon! Stay blessed.";
    }

    public static String showInvalidInput() {
        return "Invalid input? Happens. Adjust, refocus, try again. That’s how you grow.";
    }

    public static String showNumberError() {
        return "Enter a real number... locked in, focused. No shortcuts, just the truth.";
    }

    public static String showBoundsError() {
        return "Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.";
    }

    public static String showEmptyTodo() {
        return "lebot.tasks.ToDo cannot be empty. Gotta put the ball in play.";
    }

    public static String showMissingDeadline() {
        return "No deadline set, can't achieve greatness like that.";
    }

    public static String showMissingEventTimes() {
        return "Gotta specify a time window. Eyes on the prize.";
    }

    public static String showInvalidDate() {
        return "Shoot me the date like this, champ: dd/MM/yyyy.";
    }

    public static String showIoError(String msg) {
        return "Error writing to file: " + msg;
    }
}
