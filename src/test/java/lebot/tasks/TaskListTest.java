package lebot.tasks;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void delete_ArrayIndexOutOfBoundsException_exceptionThrown() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.delete("1");
    }

    @Test
    public void delete_NumeberFormatException_exceptionThrown() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.delete("Not a number");
    }

    @Test
    public void mark_NumberFormatException_exceptionThrown() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.delete("Not a number");
    }

    @Test
    public void mark_IndexOutOfBoundsException_exceptionThrown() {
        TaskList taskList = new TaskList(new ArrayList<>());
        taskList.delete("65");
    }

}
