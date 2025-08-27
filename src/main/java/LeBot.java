import java.util.Scanner;

public class LeBot {


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
            default:
                System.out.println("Invalid input? Happens. Adjust, refocus, try again. That’s how you grow.");
                return true;
        }
    }

}
