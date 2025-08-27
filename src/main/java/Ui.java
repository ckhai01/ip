import java.util.Scanner;

public class Ui {

    public static void displayIntro() {
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

    public static void showList(TaskList tasks) {
        if (tasks.isEmpty()) {
            System.out.println("Haven’t added anything yet? Can’t win a game if you don’t put the ball in play. Gotta set the goals before you chase them.");
            return;
        }
        System.out.println("Here’s the list. No excuses, no shortcuts. One by one, we knock these down.");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public static void showAdd(Task task, int size) {
        System.out.println("Got it. Next task on the list: ");
        System.out.println(task);
        System.out.println(size + " tasks on the board. Lock in.");
    }

    public static void showDelete(Task task, int size) {
        System.out.println("Scratched it off the list. Recenter yourself: " + task);
        System.out.println("Now you have " + size + " tasks on the board.");
    }

    public static void showMark(Task task) {
        System.out.println("Checked it off the list. Another step closer to greatness: " + task);
    }

    public static void showUnmark(Task task) {
        System.out.println("Alright, not done yet. Back in the lab, time to finish the job: " + task);
    }

    public static void showBye() {
        System.out.println("Ayy, take care! Hope to see you soon! Stay blessed.");
    }

    public static void showInvalidInput() {
        System.out.println("Invalid input? Happens. Adjust, refocus, try again. That’s how you grow.");
    }

    public static void showNumberError() {
        System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
    }

    public static void showBoundsError() {
        System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
    }

    public static void showEmptyTodo() {
        System.out.println("ToDo cannot be empty. Gotta put the ball in play.");
    }

    public static void showMissingDeadline() {
        System.out.println("No deadline set, can't achieve greatness like that.");
    }

    public static void showMissingEventTimes() {
        System.out.println("Gotta specify a time window. Eyes on the prize.");
    }

    public static void showInvalidDate() {
        System.out.println("Shoot me the date like this, champ: dd/MM/yyyy.");
    }

    public static void showIoError(String msg) {
        System.out.println("Error writing to file: " + msg);
    }
}
