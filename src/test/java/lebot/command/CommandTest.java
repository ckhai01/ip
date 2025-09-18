package lebot.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;



public class CommandTest {
    @Test
    public void constructor_input_success() {
        String input = "newcommand Task Name";
        assertEquals("newcommand", new Command(input).getAction());
        assertEquals("Task Name", new Command(input).getDesc());
    }

    @Test
    public void constructor_emptyInput_success() {
        String input = "";
        assertEquals("", new Command(input).getAction());
        assertEquals("", new Command(input).getDesc());
    }

    @Test
    public void equals_input_success() {
        String input = "newcommand Task Name";
        assertTrue(new Command(input).equals("newcommand"));
        assertEquals(new Command("newcommand Other Task"), new Command(input));
    }
}
