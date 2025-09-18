package lebot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.Test;

//These tests were generated using ChatGPT.

public class EventTest {

    private static final DateTimeFormatter DISP =
            DateTimeFormatter.ofPattern("MMM d yyyy", Locale.getDefault());

    @Test
    public void constructor_parsesDates_success() {
        Event e = new Event("Basketball Bootcamp", "03/12/2025", "01/12/2025");
        String fromStr = LocalDate.parse("01/12/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(DISP);
        String toStr = LocalDate.parse("03/12/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(DISP);

        assertEquals("[E][ ] Basketball Bootcamp(from: " + fromStr + " to: " + toStr + ") ", e.toString());
        assertEquals("E|0|Basketball Bootcamp|`|03/12/2025|01/12/2025", e.saveString());
    }

    @Test
    public void mark_unmark_success() {
        Event e = new Event("Conference", "10/01/2026", "08/01/2026");

        e.markAsDone();
        assertEquals("E|1|Conference|`|10/01/2026|08/01/2026", e.saveString());

        e.unmarkAsDone();
        assertEquals("E|0|Conference|`|10/01/2026|08/01/2026", e.saveString());
    }

    @Test
    public void display_save_success() {
        Event e = new Event("Hackathon", "05/05/2025", "03/05/2025");
        e.addTag("code");
        e.addTag("team");

        String fromStr = LocalDate.parse("03/05/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(DISP);
        String toStr = LocalDate.parse("05/05/2025", DateTimeFormatter.ofPattern("dd/MM/yyyy")).format(DISP);

        assertEquals("[E][ ] Hackathon(from: " + fromStr + " to: " + toStr + ") #code, #team", e.toString());
        assertEquals("E|0|Hackathon|code`team|05/05/2025|03/05/2025", e.saveString());
    }
}
