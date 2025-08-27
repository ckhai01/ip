import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LeBot {
    private static final Pattern DEADLINE_BY = Pattern.compile("/by (\\S+)");
    private static final Pattern EVENT_TO = Pattern.compile("/to (\\S+)");
    private static final Pattern EVENT_FROM = Pattern.compile("/from (\\S+)");

    public static void main(String[] args) {
        displayIntro();
        ArrayList<Task> list = Storage.loadList();
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

    private static void mainLoop(ArrayList<Task> list) {
        Scanner inputScanner = new Scanner(System.in);
        boolean repeat = true;
        while (repeat) {
            System.out.print("Enter: ");
            String input = inputScanner.nextLine();
            Command parsedInput = new Command(input);
            repeat = dispatchAction(parsedInput, list);
        }
    }

    private static boolean dispatchAction(Command parsedInput, ArrayList<Task> list) {
        switch (parsedInput.getAction()) {
            case "list":
                printList(list);
                return true;
            case "mark":
                markTask(list, parsedInput.getDesc());
                return true;
            case "unmark":
                unmarkTask(list, parsedInput.getDesc());
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
                deleteTask(list, parsedInput.getDesc());
                return true;
            default:
                System.out.println("Invalid input? Happens. Adjust, refocus, try again. That’s how you grow.");
                return true;
        }
    }

    private static void printList(ArrayList<Task> list) {
        if (list.isEmpty()) {
            System.out.println("Haven’t added anything yet? Can’t win a game if you don’t put the ball" +
                    " in play. Gotta set the goals before you chase them.");
            return;
        }
        System.out.println("Here’s the list. No excuses, no shortcuts. One by one, we knock these down.");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + "." + list.get(i));
        }
    }

    private static void markTask(ArrayList<Task> list, String desc) {
        try {
            int number = parseIndex(desc);
            list.get(number).markAsDone();
            System.out.println("Checked it off the list. Another step closer to greatness: " + list.get(number));
        } catch (NumberFormatException e) {
            System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
        }
    }

    private static void unmarkTask(ArrayList<Task> list, String desc) {
        try {
            int number = parseIndex(desc);
            list.get(number).unmarkAsDone();
            System.out.println("Alright, not done yet. Back in the lab, time to finish the job: " + list.get(number));
        } catch (NumberFormatException e) {
            System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
        }
    }

    private static void createTodo(ArrayList<Task> list, String desc) {
        if (desc.isEmpty()) {
            System.out.println("ToDo cannot be empty. Gotta put the ball in play.");
            return;
        }
        ToDo todo = new ToDo(desc);
        addTask(list, todo);
    }

    private static void createDeadline(ArrayList<Task> list, String desc) {
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

    private static void createEvent(ArrayList<Task> list, String desc) {
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

    private static void deleteTask(ArrayList<Task> list, String desc) {
        try {
            int number = parseIndex(desc);
            Task tempTask = list.get(number);
            System.out.println("Scratched it off the list. Recenter yourself: " + tempTask);
            list.remove(number);
            Storage.saveList(list);
            System.out.println("Now you have " + list.size() + " tasks on the board.");
        } catch (NumberFormatException e) {
            System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
        }
    }

    private static int parseIndex(String desc) {
        return Integer.parseInt(desc) - 1;
    }

    private static String findGroup(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static void addTask(ArrayList<Task> list, Task task) {
        list.add(task);
        Storage.saveList(list);
        System.out.println("Got it. Next task on the list: ");
        System.out.println(task);
        System.out.println(list.size() + " tasks on the board. Lock in.");
    }


}
