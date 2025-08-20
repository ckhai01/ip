import java.util.Scanner;

public class LeBot {
    public static void main(String[] args) {
        System.out.println("Yo, what’s good! It's LeBot James in the building! What can I help you with today? Let's get it!");
        echo();
        System.out.println("Ayy, take care! Hope to see you soon! Stay blessed.");
    }

    public static void echo() {
        String input;

        while (true) {
            Scanner inputScanner = new Scanner(System.in);
            System.out.print("Enter: ");
            input = inputScanner.nextLine();
            if (input.equals("bye")) {
                break;
            }
            System.out.println(input);
        }
    }
}
