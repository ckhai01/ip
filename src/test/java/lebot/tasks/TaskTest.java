package lebot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

//These tests were generated using ChatGPT.

public class TaskTest {

    @Test
    public void constructor_defaults_success() {
        Task t = new Task("Do stuff");
        assertEquals("[ ]", t.getStatusIcon());
        assertEquals("[ ] Do stuff", t.toString());
        assertEquals("", t.formatTags());
        assertEquals("|0|Do stuff|`|", t.saveString());
    }

    @Test
    public void markAsDone_success() {
        Task t = new Task("Win the Finals");
        t.markAsDone();
        assertEquals("[✓]", t.getStatusIcon());
        assertEquals("[✓] Win the Finals", t.toString());
        assertEquals("|1|Win the Finals|`|", t.saveString());
    }

    @Test
    public void unmarkAsDone_success() {
        Task t = new Task("Practice");
        t.markAsDone();
        t.unmarkAsDone();
        assertEquals("[ ]", t.getStatusIcon());
        assertEquals("[ ] Practice", t.toString());
        assertEquals("|0|Practice|`|", t.saveString());
    }

    @Test
    public void addTag_success() {
        Task t = new Task("Train hard");
        t.addTag("fun");
        assertEquals("#fun", t.formatTags());
        assertEquals("|0|Train hard|fun|", t.saveString());

        t.addTag("win");
        assertEquals("#fun, #win", t.formatTags());
        // backtick-separated for saving, no trailing backtick and wrapped with pipes
        assertEquals("|0|Train hard|fun`win|", t.saveString());
    }

    @Test
    public void toString_success() {
        Task t = new Task("Review PRs");
        t.addTag("work");
        t.addTag("today");
        t.markAsDone();
        assertEquals("[✓] Review PRs", t.toString());
        assertEquals("#work, #today", t.formatTags());
        assertEquals("|1|Review PRs|work`today|", t.saveString());
    }
}
