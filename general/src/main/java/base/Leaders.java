package base;

public enum Leaders {
    PERVIY("Первая персона"),
    VTOROY("Вторая важная персона"),
    TRETIY("Третья очень важная персона"),
    CHETVYRTIY("Просто четвёртая персона"),
    PYTIY("Пятый человек");

    private final String title;

    Leaders(String title) {
        this.title = title;
    }

    public static String getFromNum(int number) {
        switch (number) {
            case 0:
                return PERVIY.title;
            case 1:
                return VTOROY.title;
            case 2:
                return TRETIY.title;
            case 3:
                return CHETVYRTIY.title;
            default:
                return PYTIY.title;
        }
    }

    @Override
    public String toString() {
        return title;
    }

    public static Leaders fromString(String leaderStr) {
        for (Leaders leader : Leaders.values()) {
            if (leader.toString().equalsIgnoreCase(leaderStr)) {
                return leader;
            }
        }
        return null;
    }
}
