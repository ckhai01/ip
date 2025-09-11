package lebot;

import lebot.command.Command;
import lebot.tasks.TaskList;
import lebot.ui.Ui;

/**
 * Entry point for the LeBot task manager.
 * <p>
 * Shows an intro, creates a {@link TaskList}, and then reads user
 * input in a loop, dispatching commands until the user exits with {@code bye}.
 */

public class LeBotGui {

    /**
     * Executes the action contained in the parsed command.
     * <p>
     * Supported actions:
     * <ul>
     *   <li>{@code list} — show all tasks</li>
     *   <li>{@code mark} / {@code unmark} — toggle completion on a task</li>
     *   <li>{@code todo}, {@code deadline}, {@code event} — add a task</li>
     *   <li>{@code delete} — remove a task</li>
     *   <li>{@code find} — finds tasks containing keyword(s)</li>
     *   <li>{@code bye} — exit the program</li>
     * </ul>
     * Any unknown action results in an "invalid input" message.
     *
     * @param parsedInput the parsed user input containing an action and optional description
     * @param list        the task list to operate on
     * @return Confirmation message for display on GUI
     */
    public static String dispatchAction(Command parsedInput, TaskList list) {
        switch (parsedInput.getAction()) {
        case "list":
            return Ui.showList(list);
        case "mark":
            return list.markTask(parsedInput.getDesc());
        case "unmark":
            return list.unmarkTask(parsedInput.getDesc());
        case "tag":
            return list.addTag(parsedInput.getDesc());
        case "bye":
            return Ui.showBye();
        case "todo":
            return list.createTodo(parsedInput.getDesc());
        case "deadline":
            return list.createDeadline(parsedInput.getDesc());
        case "event":
            return list.createEvent(parsedInput.getDesc());
        case "delete":
            return list.delete(parsedInput.getDesc());
        case "find":
            return list.findTasks(parsedInput.getDesc());
        default:
            return Ui.showInvalidInput();
        }
    }

}
