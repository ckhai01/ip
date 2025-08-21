import java.util.Scanner;

public class LeBot {
    public static void main(String[] args) {
        System.out.println("Yo, what’s good! It's LeBot James in the building! What can I help you with today? Let's get it!");
        Task[] toDo = new Task[100];
        String input;
        int counter = 0;
        boolean repeat = true;
        while (repeat) {
            Scanner inputScanner = new Scanner(System.in);
            System.out.print("Enter: ");
            input = inputScanner.nextLine();

            Command parsedInput = new Command(input);

            switch (parsedInput.getAction()) {
                case "list":
                    if (counter==0) {
                        System.out.println("Haven’t added anything yet? Can’t win a game if you don’t put the ball" +
                                " in play. Gotta set the goals before you chase them.");
                        break;
                    }

                    System.out.println("Here’s the list. No excuses, no shortcuts. Just the work in front of me." +
                            " One by one, we knock these down.");
                    for (int i=0; i < counter; i++) {
                        System.out.println(i+1 + "." + toDo[i].getStatusText());
                    }
                    break;
                case "mark":
                    try {
                        int number = Integer.parseInt(parsedInput.getDesc()) - 1;
                        toDo[number].markAsDone();
                        System.out.println("Checked it off the list. Another step closer to greatness: " + toDo[number].getStatusText());
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a real number... locked in, focused. No shortcuts, just the truth.");
                    } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                        System.out.println("Out of bounds?? Gotta stay within the limits, stay disciplined. Fundamentals matter.");
                    }
                    break;
                case "unmark":
                    try {
                        int number = Integer.parseInt(parsedInput.getDesc()) - 1;
                        toDo[number].unmarkAsDone();
                        System.out.println("Alright, not done yet. Back in the lab, time to finish the job: " + toDo[number].getStatusText());
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
                default:
                    toDo[counter] = new Task(input);
                    System.out.println("Got it: " + input);
                    counter++;
                    break;
            }
        }
    }

}
