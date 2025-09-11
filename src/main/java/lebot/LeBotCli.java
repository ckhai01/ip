package lebot;

import java.util.Scanner;

import lebot.command.Command;
import lebot.tasks.TaskList;
import lebot.ui.Ui;

/**
 * Entry point for the LeBot task manager.
 * <p>
 * Shows an intro, creates a {@link TaskList}, and then reads user
 * input in a loop, dispatching commands until the user exits with {@code bye}.
 */
public class LeBotCli {

    /**
     * Starts LeBot.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        TaskList list = new TaskList();
        mainLoop(list);
    }

    /**
     * Main read–eval–print loop.
     * <p>
     * Continuously reads a line of user input, parses it into a {@link Command} object,
     * and delegates to {@link #dispatchAction(Command, TaskList)} until that method
     * returns {@code false}.
     *
     * @param list the task list to read from and mutate based on user commands
     */
    private static void mainLoop(TaskList list) {
        Scanner inputScanner = new Scanner(System.in);
        boolean repeat = true;
        System.out.println(Ui.showIntro());
        while (repeat) {
            System.out.print("Enter: ");
            String input = inputScanner.nextLine();
            Command parsedInput = new Command(input);
            repeat = dispatchAction(parsedInput, list);
        }
    }

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
     * @return {@code true} to continue the loop; {@code false} to exit
     */
    private static boolean dispatchAction(Command parsedInput, TaskList list) {
        switch (parsedInput.getAction()) {
        case "list":
            System.out.println(Ui.showList(list));
            return true;
        case "mark":
            System.out.println(list.markTask(parsedInput.getDesc()));
            return true;
        case "unmark":
            System.out.println(list.unmarkTask(parsedInput.getDesc()));
            return true;
        case "bye":
            System.out.println(Ui.showBye());
            return false;
        case "todo":
            System.out.println(list.createTodo(parsedInput.getDesc()));
            return true;
        case "deadline":
            System.out.println(list.createDeadline(parsedInput.getDesc()));
            return true;
        case "event":
            System.out.println(list.createEvent(parsedInput.getDesc()));
            return true;
        case "tag":
            System.out.println(list.addTag(parsedInput.getDesc()));
            return true;
        case "delete":
            System.out.println(list.delete(parsedInput.getDesc()));
            return true;
        case "find":
            System.out.println(list.findTasks(parsedInput.getDesc()));
            return true;
        default:
            System.out.println(Ui.showInvalidInput());
            return true;
        }
    }

}
