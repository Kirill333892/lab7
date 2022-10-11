package command;

public enum CommandsEnum {
    ADD("add"), ADD_IF_MIN("add_if_min"), CLEAR("clear"), EXECUTE_SCRIPT("execute_script"), EXIT("exit"), HELP("help"), INFO("info"),
    PRINT_DESCENDING("print_descending"), REMOVE_BY_ID("remove_by_id"), REMOVE_GREATER("remove_greater"), REMOVE_LOWER("remove_lower"), SHOW("show"),
    UPDATE("update"), AUTHORIZATION("authorization");

    public String title;

    CommandsEnum(String name) {
        this.title = name;
    }
}
