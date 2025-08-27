package lebot.storage;

import lebot.ui.Ui;
import lebot.tasks.Task;
import lebot.tasks.ToDo;
import lebot.tasks.Deadline;
import lebot.tasks.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Storage {
    public static void saveList(ArrayList<Task> list) {
        Path path = Path.of("data/lebot.LeBot.txt");
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
            Ui.showIoError(e.getMessage());
        }
    }

    public static ArrayList<Task> loadList() {
        Path path = Path.of("data/lebot.LeBot.txt");
        ArrayList<Task> list = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(path.toString()));
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
            return new ArrayList<>();
        }
    }
}
