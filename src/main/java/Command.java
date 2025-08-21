public class Command {
    protected String action;
    protected String desc;



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

    public String getAction() {return this.action;}

    public String getDesc() {return this.desc;}
}