import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskList {
    protected ArrayList<Task> list;

    private static final Pattern DEADLINE_BY = Pattern.compile("/by (\\S+)");
    private static final Pattern EVENT_TO = Pattern.compile("/to (\\S+)");
    private static final Pattern EVENT_FROM = Pattern.compile("/from (\\S+)");

    public TaskList() {
        this.list = Storage.loadList();
    }

    private static int parseIndex(String desc) {
        return Integer.parseInt(desc) - 1;
    }

    public int size() {
        return list.size();
    }

    private static String findGroup(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : null;
    }

    public void printList() {
        if (this.list.isEmpty()) {
            System.out.println("Haven’t added anything yet? Can’t win a game if you don’t put the ball" +
                    " in play. Gotta set the goals before you chase them.");
            return;
        }
        System.out.println("Here’s the list. No excuses, no shortcuts. One by one, we knock these down.");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + "." + list.get(i));
        }
    }

    public void markTask(String desc) {
        try {
            int number = parseIndex(desc);
            this.list.get(number).markAsDone();
            System.out.println("Checked it off the list. Another step closer to greatness: " + list.get(number));
        } catch (NumberFormatException e) {
            System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
        }
    }

    public void unmarkTask(String desc) {
        try {
            int number = parseIndex(desc);
            this.list.get(number).unmarkAsDone();
            System.out.println("Alright, not done yet. Back in the lab, time to finish the job: " + list.get(number));
        } catch (NumberFormatException e) {
            System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
        }
    }

    public void add(Task task) {
        this.list.add(task);
        Storage.saveList(this.list);
        System.out.println("Got it. Next task on the list: ");
        System.out.println(task);
        System.out.println(list.size() + " tasks on the board. Lock in.");
    }

    public void delete(String desc) {
        try {
            int number = parseIndex(desc);
            Task tempTask = this.list.get(number);
            System.out.println("Scratched it off the list. Recenter yourself: " + tempTask);
            this.list.remove(number);
            Storage.saveList(list);
            System.out.println("Now you have " + size() + " tasks on the board.");
        } catch (NumberFormatException e) {
            System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
        }
    }

    public void createTodo(String desc) {
        if (desc.isEmpty()) {
            System.out.println("ToDo cannot be empty. Gotta put the ball in play.");
            return;
        }
        ToDo todo = new ToDo(desc);
        this.add(todo);
    }

    public void createDeadline(String desc) {
        String match = findGroup(DEADLINE_BY, desc);
        try {
            if (match != null) {
                LocalDate.parse(match, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                Deadline deadline = new Deadline(desc.replace("/by " + match, ""), match);
                this.add(deadline);
            }
            else {
                System.out.println("No deadline set, can't achieve greatness like that.");
            }

        }
        catch (DateTimeParseException e) {
            System.out.println("Shoot me the date like this, champ: dd/MM/yyyy.");

        }

    }

    public void createEvent(String desc) {
        String to = findGroup(EVENT_TO, desc);
        String from = findGroup(EVENT_FROM, desc);

        try {
            if (to != null && from != null) {
                LocalDate.parse(to, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate.parse(from, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String newDesc = desc.replace("/to " + to, "").replace("/from " + from, "");
                Event event = new Event(newDesc, to, from);
                this.add(event);
            } else {
                System.out.println("Gotta specify a time window. Eyes on the prize.");
            }
        }
        catch (DateTimeParseException e) {
            System.out.println("Shoot me the date like this, champ: dd/MM/yyyy.");
        }


    }





}
