package lebot.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import lebot.tasks.Deadline;
import lebot.tasks.Event;
import lebot.tasks.Task;
import lebot.tasks.ToDo;
import lebot.ui.Ui;

/**
 * File-based persistence for {@link Task} objects.
 * <p>
 * Stores tasks in {@code data/lebot.LeBot.txt} as one line per task using a
 * pipe-delimited format compatible with {@link Task#formatString()}:
 * <ul>
 *   <li>{@code T|<0-or-1>|<description>}</li>
 *   <li>{@code D|<0-or-1>|<description>|<due dd/MM/yyyy>}</li>
 *   <li>{@code E|<0-or-1>|<description>|<to dd/MM/yyyy>|<from dd/MM/yyyy>}</li>
 * </ul>
 * A value of {@code 1} means done; {@code 0} means not done.
 * <p>
 * On save, parent directories are created if needed and the file is truncated.
 * On load, a missing file results in an empty list. I/O errors are reported via {@link Ui}.
 */
public class Storage {
    private static final Path DEFAULT_PATH = Path.of("data/lebot.LeBot.txt");
    /**
     * Persists the given list of tasks to disk at {@code data/lebot.LeBot.txt}.
     * <p>
     * The file is created if it does not exist and truncated if it does.
     * Any {@link IOException} is caught and reported via {@link Ui#showIoError(String)}.
     *
     * @param list the tasks to save
     */
    public static void saveList(ArrayList<Task> list) {
        try {
            if (DEFAULT_PATH.getParent() != null) {
                Files.createDirectories(DEFAULT_PATH.getParent());
            }
            String content = list.stream()
                    .map(Task::formatString)
                    .collect(Collectors.joining(System.lineSeparator()));

            Files.writeString(
                    DEFAULT_PATH,
                    content,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );
        } catch (IOException e) {
            Ui.showIoError(e.getMessage());
        }
    }

    /**
     * Loads tasks from {@code data/lebot.LeBot.txt}.
     * <p>
     * Each line is parsed into a {@link ToDo}, {@link Deadline}, or {@link Event}
     * based on the leading type code ({@code T}, {@code D}, {@code E}). If the
     * file is not found, an empty list is returned.
     *
     * @return a list of tasks reconstructed from disk; never {@code null}
     */
    public static ArrayList<Task> loadList() {
        ArrayList<Task> list = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(DEFAULT_PATH.toString()));
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
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
