import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LeBot {
    private static final Pattern DEADLINE_BY = Pattern.compile("/by (\\S+)");
    private static final Pattern EVENT_TO = Pattern.compile("/to (\\S+)");
    private static final Pattern EVENT_FROM = Pattern.compile("/from (\\S+)");

    public static void main(String[] args) {
        displayIntro();
        TaskList list = new TaskList();
        mainLoop(list);
    }

    private static void displayIntro() {
        String lebron = """
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣤⣶⣾⣿⣿⣿⣶⣶⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⠛⠛⠉⠉⠉⠉⠛⣿⣿⣿⣦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⣿⡿⠟⠀⠀⠀⠀⠀⠀⠀⠺⠶⣾⣿⣿⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡿⣇⠀⢀⡀⠀⠀⠀⠀⢀⣀⣀⣌⣿⣿⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⣿⢣⣿⠆⢀⡀⠀⠀⠀⠀⠈⠉⢉⡺⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣿⣿⣾⡿⣻⣿⣿⡷⠇⠀⠀⣴⣿⣿⣿⣿⢿⣿⣿⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⣟⣿⣿⡏⣾⣿⣿⣯⣿⣶⠾⠻⣿⣿⣽⣿⣿⣿⣿⣿⢿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣻⣿⣿⢁⣙⡋⠉⠉⣿⡇⠀⠀⣺⣿⡯⠻⠛⠛⣿⣿⢉⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⡽⡏⠁⠀⣴⢿⣏⣥⣄⣠⣤⣽⡿⠆⠀⠀⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣿⣇⣿⢰⣿⣤⣶⣾⡿⢿⡿⢿⣿⣶⣦⣌⢢⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣄⣻⣿⣿⣯⣉⡉⣉⣉⣩⣿⣿⣿⣼⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⡋⠠⣤⣤⣤⣴⡾⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⣦⡀⠿⠃⣀⣴⣿⣿⣿⣿⣿⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣿⣿⣿⣿⣿⣿⣷⣶⣶⣿⣿⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣀⡀⢀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣼⠏⠙⢿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠉⣿⣿⣿⣿⠛⡗⠶⢤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡤⣾⠻⣿⡟⣿⠀⠀⠀⠀⠈⠛⢉⣠⡝⠋⠉⠀⠀⠀⢻⣯⣿⠇⣼⢃⡆⠀⠈⠙⡶⢤⢤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡤⠞⠋⢳⡘⣧⡹⣿⡹⡇⠀⠀⠀⠀⠀⠚⠛⠀⠀⠀⠀⠀⠀⣼⣿⠏⣰⠋⡜⠀⠀⠀⠀⡇⢸⢸⣷⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⢻⠛⡇⠀⠀⠀⠳⣌⢷⡌⢷⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣼⣿⠏⣴⠋⠐⠁⡀⠀⠀⠀⡇⢸⢸⣿⢹⣯⣻⣷⣦⣤⡀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⣀⣴⣾⣿⠘⡇⢹⠀⠀⠀⠀⠙⢦⡙⢦⣍⡻⠶⣤⣀⣀⠀⠀⠀⣀⣴⣾⡿⢛⣥⠞⡡⠀⠰⣿⡿⣿⡆⠀⠀⢸⠈⣯⠲⣏⠉⢻⣾⣿⡿⣦⡀⠀⠀
                ⠀⠀⠀⠀⣀⣴⣾⣿⣿⠟⢻⠀⣷⢸⠀⠀⠀⠀⠀⠀⠈⠓⢮⣙⠳⠦⣌⣙⣛⣳⣞⣛⣋⡥⠶⠋⠥⠛⠀⢠⣤⣭⣟⡟⣀⣀⡀⢸⡄⢿⠀⠀⠀⠈⢹⣿⡀⠬⣿⣄⠀
                ⢀⣠⣶⣿⣿⣿⡉⠉⠀⠀⢸⠀⣿⠸⠀⠀⠀⠀⠀⠀⢀⡤⠄⠈⠙⢒⢦⢀⢈⣉⡉⡁⠀⢀⠀⠀⠀⠀⠀⠈⠛⠛⠛⠻⠿⡛⠛⠈⡇⢸⠀⠀⠀⠀⢸⡟⠳⢆⢈⣿⣄""";
        System.out.println(lebron);
        System.out.println("Yo, what’s good! It's LeBot James in the building! What can I help you with today? Let's get it!");
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
                list.printList();
                return true;
            case "mark":
                list.markTask(parsedInput.getDesc());
                return true;
            case "unmark":
                list.unmarkTask(parsedInput.getDesc());
                return true;
            case "bye":
                System.out.println("Ayy, take care! Hope to see you soon! Stay blessed.");
                return false;
            case "todo":
                createTodo(list, parsedInput.getDesc());
                return true;
            case "deadline":
                createDeadline(list, parsedInput.getDesc());
                return true;
            case "event":
                createEvent(list, parsedInput.getDesc());
                return true;
            case "delete":
                list.delete(parsedInput.getDesc());
                return true;
            default:
                System.out.println("Invalid input? Happens. Adjust, refocus, try again. That’s how you grow.");
                return true;
        }
    }

    private static void createTodo(TaskList list, String desc) {
        if (desc.isEmpty()) {
            System.out.println("ToDo cannot be empty. Gotta put the ball in play.");
            return;
        }
        ToDo todo = new ToDo(desc);
        addTask(list, todo);
    }

    private static void createDeadline(TaskList list, String desc) {
        String match = findGroup(DEADLINE_BY, desc);
        try {
            if (match != null) {
                LocalDate.parse(match, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Deadline deadline = new Deadline(desc.replace("/by " + match, ""), match);
                addTask(list, deadline);
            }
            else {
                System.out.println("No deadline set, can't achieve greatness like that.");
            }

        }
        catch (DateTimeParseException e) {
            System.out.println("Shoot me the date like this, champ: dd/MM/yyyy.");

        }

    }

    private static void createEvent(TaskList list, String desc) {
        String to = findGroup(EVENT_TO, desc);
        String from = findGroup(EVENT_FROM, desc);

        try {
            if (to != null && from != null) {
                LocalDate.parse(to, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate.parse(from, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String newDesc = desc.replace("/to " + to, "").replace("/from " + from, "");
                Event event = new Event(newDesc, to, from);
                addTask(list, event);
            } else {
                System.out.println("Gotta specify a time window. Eyes on the prize.");
            }
        }
        catch (DateTimeParseException e) {
            System.out.println("Shoot me the date like this, champ: dd/MM/yyyy.");
        }


    }

    private static String findGroup(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static void addTask(TaskList list, Task task) {
        list.add(task);
        System.out.println("Got it. Next task on the list: ");
        System.out.println(task);
        System.out.println(list.size() + " tasks on the board. Lock in.");
    }


}
