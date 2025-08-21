import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class LeBot {
    public static void main(String[] args) {

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
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣼⠏⠙⢿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠉⣿⣿⣿⣿⠛⡗⠶⢤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡤⣾⠻⣿⡟⣿⠀⠀⠀⠀⠈⠛⢉⣠⡝⠋⠉⠀⠀⠀⢻⣯⣿⠇⣼⢃⡆⠀⠈⠙⡶⢤⢤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡤⠞⠋⢳⡘⣧⡹⣿⡹⡇⠀⠀⠀⠀⠀⠚⠛⠀⠀⠀⠀⠀⠀⣼⣿⠏⣰⠋⡜⠀⠀⠀⠀⡇⢸⢸⣷⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⢻⠛⡇⠀⠀⠀⠳⣌⢷⡌⢷⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣼⣿⠏⣴⠋⠐⠁⡀⠀⠀⠀⡇⢸⢸⣿⢹⣯⣻⣷⣦⣤⡀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⣀⣴⣾⣿⠘⡇⢹⠀⠀⠀⠀⠙⢦⡙⢦⣍⡻⠶⣤⣀⣀⠀⠀⠀⣀⣴⣾⡿⢛⣥⠞⡡⠀⠰⣿⡿⣿⡆⠀⠀⢸⠈⣯⠲⣏⠉⢻⣾⣿⡿⣦⡀⠀⠀
                ⠀⠀⠀⠀⣀⣴⣾⣿⣿⠟⢻⠀⣷⢸⠀⠀⠀⠀⠀⠀⠈⠓⢮⣙⠳⠦⣌⣙⣛⣳⣞⣛⣋⡥⠶⠋⠥⠛⠀⢠⣤⣭⣟⡟⣀⣀⡀⢸⡄⢿⠀⠀⠀⠈⢹⣿⡀⠬⣿⣄⠀
                ⢀⣠⣶⣿⣿⣿⡉⠉⠀⠀⢸⠀⣿⠸⠀⠀⠀⠀⠀⠀⢀⡤⠄⠈⠙⢒⢦⢀⢈⣉⡉⡁⠀⢀⠀⠀⠀⠀⠀⠈⠛⠛⠛⠻⠿⡛⠛⠈⡇⢸⠀⠀⠀⠀⢸⡟⠳⢆⢈⣿⣄""";
        System.out.println(lebron);
        System.out.println("Yo, what’s good! It's LeBot James in the building! What can I help you with today? Let's get it!");
        //Task[] toDo = new Task[100];
        ArrayList<Task> list = new ArrayList<>();
        String input;
        boolean repeat = true;
        while (repeat) {
            Scanner inputScanner = new Scanner(System.in);
            System.out.print("Enter: ");
            input = inputScanner.nextLine();

            Command parsedInput = new Command(input);

            switch (parsedInput.getAction()) {
                case "list":
                    if (list.isEmpty()) {
                        System.out.println("Haven’t added anything yet? Can’t win a game if you don’t put the ball" +
                                " in play. Gotta set the goals before you chase them.");
                        break;
                    }

                    System.out.println("Here’s the list. No excuses, no shortcuts. One by one, we knock these down.");
                    for (int i=0; i < list.size(); i++) {
                        System.out.println(i+1 + "." + list.get(i));
                    }
                    break;
                case "mark":
                    try {
                        int number = Integer.parseInt(parsedInput.getDesc()) - 1;
                        list.get(number).markAsDone();
                        System.out.println("Checked it off the list. Another step closer to greatness: " + list.get(number));
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
                    } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                        System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
                    }
                    break;
                case "unmark":
                    try {
                        int number = Integer.parseInt(parsedInput.getDesc()) - 1;
                        list.get(number).unmarkAsDone();
                        System.out.println("Alright, not done yet. Back in the lab, time to finish the job: " + list.get(number));
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
                    } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                        System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
                    }
                    break;
                case "bye":
                    repeat = false;
                    System.out.println("Ayy, take care! Hope to see you soon! Stay blessed.");
                    break;
                case "todo":

                    if (parsedInput.getDesc().isEmpty()) {
                        System.out.println("ToDo cannot be empty. Gotta put the ball in play.");
                        break;
                    }
                    ToDo todo = new ToDo(parsedInput.getDesc());
                    list.add(todo);
                    System.out.println("Got it. Next task on the list: ");
                    System.out.println(todo);
                    System.out.println(list.size() + " tasks on the board. Lock in.");
                    break;

                case "deadline":
                    Pattern pattern = Pattern.compile("/by (\\S+)");
                    Matcher matcher = pattern.matcher(parsedInput.getDesc());
                    if (matcher.find()) {
                        String match = matcher.group(1);
                        Deadline deadline = new Deadline(parsedInput.getDesc().replace("/by " + match, ""), match);
                        list.add(deadline);
                        System.out.println("Got it. Next task on the list: ");
                        System.out.println(deadline);
                        System.out.println(list.size() + " tasks on the board. Lock in.");
                    }
                    else {
                        System.out.println("No deadline set, can't achieve greatness like that.");
                    }
                    break;

                case "event":
                    Pattern toPattern = Pattern.compile("/to (\\S+)");
                    Pattern fromPattern = Pattern.compile("/from (\\S+)");
                    Matcher toMatcher = toPattern.matcher(parsedInput.getDesc());
                    Matcher fromMatcher = fromPattern.matcher(parsedInput.getDesc());

                    if (toMatcher.find() && fromMatcher.find()) {
                        String to = toMatcher.group(1);
                        String from = fromMatcher.group(1);
                        String newDesc = parsedInput.getDesc().replace("/to " + to, "")
                                .replace("/from " + from, "");
                        Event event = new Event(newDesc, to, from);
                        list.add(event);
                        System.out.println("Got it. Next task on the list: ");
                        System.out.println(event);
                        System.out.println(list.size() + " tasks on the board. Lock in.");
                    }

                    else {
                        System.out.println("Gotta specify a time window. Eyes on the prize.");
                    }
                    break;

                case "delete":
                    try {
                        int number = Integer.parseInt(parsedInput.getDesc()) - 1;
                        Task tempTask = list.get(number);
                        System.out.println("Scratched it off the list. Recenter yourself: " + tempTask);
                        list.remove(number);
                        System.out.println("Now you have " + list.size() + " tasks on the board.");
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
                    } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                        System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
                    }
                    break;

                default:
                    System.out.println("Invalid input? Happens. Adjust, refocus, try again. That’s how you grow.");
                    break;
            }
        }
    }

}
