import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;

public class LeBot {
    private static final Pattern DEADLINE_BY = Pattern.compile("/by (\\S+)");
    private static final Pattern EVENT_TO = Pattern.compile("/to (\\S+)");
    private static final Pattern EVENT_FROM = Pattern.compile("/from (\\S+)");

    public static void main(String[] args) {
        displayIntro();
        ArrayList<Task> list = loadList();
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
        if (match != null) {
            Deadline deadline = new Deadline(desc.replace("/by " + match, ""), match);
            addTask(list, deadline);
        } else {
            System.out.println("No deadline set, can't achieve greatness like that.");
        }
    }

    private static void createEvent(ArrayList<Task> list, String desc) {
        String to = findGroup(EVENT_TO, desc);
        String from = findGroup(EVENT_FROM, desc);
        if (to != null && from != null) {
            String newDesc = desc.replace("/to " + to, "").replace("/from " + from, "");
            Event event = new Event(newDesc, to, from);
            addTask(list, event);
        } else {
            System.out.println("Gotta specify a time window. Eyes on the prize.");
        }
    }

    private static void deleteTask(ArrayList<Task> list, String desc) {
        try {
            int number = parseIndex(desc);
            Task tempTask = list.get(number);
            System.out.println("Scratched it off the list. Recenter yourself: " + tempTask);
            list.remove(number);
            saveList(list);
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
        saveList(list);
        System.out.println("Got it. Next task on the list: ");
        System.out.println(task);
        System.out.println(list.size() + " tasks on the board. Lock in.");
    }

    private static void saveList(ArrayList<Task> list) {
        Path path = Path.of("../../data/LeBot.txt");
        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            String content = list.stream()
                    .map(Task::formattedString)
                    .collect(Collectors.joining(System.lineSeparator()));

            Files.writeString(
                    path,
                    content,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            return;
        }
    }

    private static ArrayList<Task> loadList() {
        ArrayList<Task> list = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File("../../data/LeBot.txt"));
            Task task;
            while (s.hasNextLine()) {
                String[] tempList = s.nextLine().split("\\|");
                String type = tempList[0];

                switch (type) {
                    case "T" -> task = new ToDo(tempList[2]);
                    case "D" -> task = new Deadline(tempList[2], tempList[3]);
                    case "E" -> task = new Event(tempList[2], tempList[3], tempList[4]);
                    default -> task = new Task(tempList[0]);
                }
                if (tempList[1].equals("1")) {
                    task.markAsDone();
                }
                list.add(task);

            }
            s.close();
            return list;
        }
        catch (FileNotFoundException e) {
            return new ArrayList<Task>();
        }
    }
}
