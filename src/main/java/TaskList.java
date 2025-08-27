import java.util.ArrayList;

public class TaskList {
    protected ArrayList<Task> list;

    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    public TaskList() {
        this.list = Storage.loadList();
    }

    private static int parseIndex(String desc) {
        return Integer.parseInt(desc) - 1;
    }

    public int size() {
        return list.size();
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



}
