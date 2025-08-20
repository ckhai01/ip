import java.util.Scanner;

public class LeBot {
    public static void main(String[] args) {
        System.out.println("Yo, what’s good! It's LeBot James in the building! What can I help you with today? Let's get it!");
        list();
        System.out.println("Ayy, take care! Hope to see you soon! Stay blessed.");
    }

    public static void list() {
        String[] toDo = new String[100];
        String input;
        int counter = 0;
        while (true) {
            Scanner inputScanner = new Scanner(System.in);
            System.out.print("Enter: ");
            input = inputScanner.nextLine();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                for (int i=0; i < counter; i++) {
                    System.out.println(i+1 + ". " + toDo[i]);
                }
            } else {
                toDo[counter] = input;
                System.out.println("Added: " + input);
                counter++;
            }
        }
    }
}
