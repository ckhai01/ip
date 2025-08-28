package lebot.command;

/**
 * Parses and represents a user command consisting of an action and an optional description.
 * <p>
 * The first whitespace-separated token is treated as the <em>action</em>
 * (e.g., {@code "list"}, {@code "todo"}, {@code "deadline"}), and the remainder
 * of the line—if any—is stored as the <em>description</em>. If only one token
 * is provided, the description is the empty string.
 */
public class Command {
    protected String action;
    protected String desc;

    /**
     * Constructs a {@code Command} by splitting the input on whitespace.
     * <p>
     * Example: {@code "deadline Submit report /by 31/12/2025"} yields
     * action {@code "deadline"} and description {@code "Submit report /by 31/12/2025"}.
     *
     * @param input the raw user input line
     */
    public Command(String input) {
        String[] splitted = input.split("\\s+");
        String description;
        if (splitted.length == 1) {
            description = "";
        }
        else {
            description = input.replaceFirst(splitted[0] + " ", "");
        }

        this.action = splitted[0];
        this.desc = description;
    }

    /**
     * Compares this command for equality.
     * <p>
     * Equality is based <em>only</em> on the action:
     * <ul>
     *   <li>If {@code obj} is a {@link Command}, returns {@code true} when both actions are equal.</li>
     *   <li>If {@code obj} is a {@link String}, returns {@code true} when it equals this action.</li>
     *   <li>Otherwise returns {@code false}.</li>
     * </ul>
     *
     * @param obj the object to compare with
     * @return whether {@code obj} matches this command's action
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Command) {
            return ((Command) obj).action.equals(this.action);
        }

        else if (obj instanceof String) {
            return this.action.equals(obj);
        }
        return false;
    }

    /**
     * Returns the command action (the first token).
     *
     * @return the action string
     */
    public String getAction() {return this.action;}

    /**
     * Returns the command description (everything after the action), or
     * the empty string if none was provided.
     *
     * @return the description string
     */
    public String getDesc() {return this.desc;}
}
