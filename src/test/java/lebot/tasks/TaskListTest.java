package lebot.tasks;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;


public class TaskListTest {
    @Test
    public void delete_arrayIndexOutOfBoundsException_exceptionThrown() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.delete("1");
    }

    @Test
    public void delete_numberFormatException_exceptionThrown() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.delete("Not a number");
    }

    @Test
    public void mark_numberFormatException_exceptionThrown() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.delete("Not a number");
    }

    @Test
    public void mark_indexOutOfBoundsException_exceptionThrown() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.delete("65");
    }

}
