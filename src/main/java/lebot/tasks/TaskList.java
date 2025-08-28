package lebot.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lebot.storage.Storage;
import lebot.ui.Ui;

public class TaskList {
    private static final Pattern DEADLINE_BY = Pattern.compile("/by (\\S+)");
    private static final Pattern EVENT_TO = Pattern.compile("/to (\\S+)");
    private static final Pattern EVENT_FROM = Pattern.compile("/from (\\S+)");
    protected ArrayList<Task> list;

    public TaskList() {
        this.list = Storage.loadList();
    }

    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    private static int parseIndex(String desc) {
        return Integer.parseInt(desc) - 1;
    }

    private static String findGroup(Pattern pattern, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find() ? matcher.group(1) : null;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public Task get(int index) {
        return list.get(index);
    }

    public void markTask(String desc) {
        try {
            int number = parseIndex(desc);
            this.list.get(number).markAsDone();
            Storage.saveList(this.list);
            Ui.showMark(this.list.get(number));
        } catch (NumberFormatException e) {
            Ui.showNumberError();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            Ui.showBoundsError();
        }
    }

    public void unmarkTask(String desc) {
        try {
            int number = parseIndex(desc);
            this.list.get(number).unmarkAsDone();
            Storage.saveList(this.list);
            Ui.showUnmark(this.list.get(number));
        } catch (NumberFormatException e) {
            Ui.showNumberError();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            Ui.showBoundsError();
        }
    }

    public void add(Task task) {
        this.list.add(task);
        Storage.saveList(this.list);
        Ui.showAdd(task, this.size());
    }

    public void delete(String desc) {
        try {
            int number = parseIndex(desc);
            Task tempTask = this.list.get(number);
            this.list.remove(number);
            Storage.saveList(list);
            Ui.showDelete(tempTask, this.size());

        } catch (NumberFormatException e) {
            Ui.showNumberError();
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            Ui.showBoundsError();
        }
    }

    public void createTodo(String desc) {
        if (desc.isEmpty()) {
            Ui.showEmptyTodo();
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
            } else {
                Ui.showMissingDeadline();
            }
        } catch (DateTimeParseException e) {
            Ui.showInvalidDate();
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
                Ui.showMissingEventTimes();
            }
        } catch (DateTimeParseException e) {
            Ui.showInvalidDate();
        }


    }


}
