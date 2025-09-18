package lebot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

//These tests were generated using ChatGPT.

public class ToDoTest {

    @Test
    public void constructor_defaults_success() {
        ToDo td = new ToDo("Buy milk");
        assertEquals("[T][ ] Buy milk ", td.toString());
        assertEquals("T|0|Buy milk|`|", td.saveString());
    }

    @Test
    public void markAsDone_success() {
        ToDo td = new ToDo("Submit report");
        td.markAsDone();
        assertEquals("[T][✓] Submit report ", td.toString());
        assertEquals("T|1|Submit report|`|", td.saveString());
    }

    @Test
    public void unmarkAsDone_success() {
        ToDo td = new ToDo("Stretch");
        td.markAsDone();
        td.unmarkAsDone();
        assertEquals("[T][ ] Stretch ", td.toString());
        assertEquals("T|0|Stretch|`|", td.saveString());
    }

    @Test
    public void addSingleTag_success() {
        ToDo td = new ToDo("Buy milk");
        td.addTag("urgent");
        assertEquals("[T][ ] Buy milk #urgent", td.toString());
        assertEquals("T|0|Buy milk|urgent|", td.saveString());
    }

    @Test
    public void addMultipleTags_success() {
        ToDo td = new ToDo("Buy milk");
        td.addTag("urgent");
        td.addTag("home");
        assertEquals("[T][ ] Buy milk #urgent, #home", td.toString());
        assertEquals("T|0|Buy milk|urgent`home|", td.saveString());
    }
}
