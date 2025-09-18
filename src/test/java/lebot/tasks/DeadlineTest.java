package lebot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.Test;

//These tests were generated using ChatGPT.

public class DeadlineTest {

    private static final DateTimeFormatter DISP =
            DateTimeFormatter.ofPattern("MMM d yyyy", Locale.getDefault());
    private static final DateTimeFormatter SAVE =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Test
    public void constructor_parsesDate_success() {
        Deadline d = new Deadline("Submit report", "01/01/2025");

        String byStr = LocalDate.parse("01/01/2025", SAVE).format(DISP);
        // NOTE: no space before "(by:" and trailing space when tags are empty
        assertEquals("[D][ ] Submit report(by: " + byStr + ") ", d.toString());
        assertEquals("D|0|Submit report|`|01/01/2025", d.saveString());
    }

    @Test
    public void mark_unmark_success() {
        Deadline d = new Deadline("Pay taxes", "15/04/2026");

        d.markAsDone();
        assertEquals("D|1|Pay taxes|`|15/04/2026", d.saveString());

        d.unmarkAsDone();
        assertEquals("D|0|Pay taxes|`|15/04/2026", d.saveString());
    }

    @Test
    public void display_save_success() {
        Deadline d = new Deadline("Submit report", "01/01/2025");
        d.addTag("work");
        d.addTag("urgent");

        String byStr = LocalDate.parse("01/01/2025", SAVE).format(DISP);
        assertEquals("[D][ ] Submit report(by: " + byStr + ") #work, #urgent", d.toString());
        assertEquals("D|0|Submit report|work`urgent|01/01/2025", d.saveString());
    }
}
