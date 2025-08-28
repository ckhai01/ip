package lebot;

import lebot.ui.Ui;
import lebot.command.Command;
import lebot.tasks.TaskList;

import java.util.Scanner;


public class LeBot {


    public static void main(String[] args) {
        Ui.displayIntro();
        TaskList list = new TaskList();
        mainLoop(list);
    }

    private static void mainLoop(TaskList list) {
        Scanner inputScanner = new Scanner(System.in);
        boolean repeat = true;
        while (repeat) {
            System.out.print("Enter: ");
            String input = inputScanner.nextLine();
            Command parsedInput = new Command(input);
            repeat = dispatchAction(parsedInput, list);
        }
    }

    private static boolean dispatchAction(Command parsedInput, TaskList list) {
        switch (parsedInput.getAction()) {
            case "list":
                Ui.showList(list);
                return true;
            case "mark":
                list.markTask(parsedInput.getDesc());
                return true;
            case "unmark":
                list.unmarkTask(parsedInput.getDesc());
                return true;
            case "bye":
                Ui.showBye();
                return false;
            case "todo":
                list.createTodo(parsedInput.getDesc());
                return true;
            case "deadline":
                list.createDeadline(parsedInput.getDesc());
                return true;
            case "event":
                list.createEvent(parsedInput.getDesc());
                return true;
            case "delete":
                list.delete(parsedInput.getDesc());
                return true;
            case "find":
                list.findTasks(parsedInput.getDesc());
                return true;
            default:
                Ui.showInvalidInput();
                return true;
        }
    }

}
