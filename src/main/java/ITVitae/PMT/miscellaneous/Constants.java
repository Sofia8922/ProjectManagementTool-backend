package ITVitae.PMT.miscellaneous;

public class Constants {
    public static String noEdit = "#";
    public static Long ignoreVerification = 666L;
    public enum UserRole { CUSTOMER, DEVELOPER, OWNER}
    public enum TaskStatus { PENDING, IN_PROGRESS, ON_HOLD, UNDER_REVIEW, COMPLETED, SCRAPPED}
    public enum Colour { RED, ORANGE, YELLOW, GREEN, BLUE, PURPLE, WHITE, BLACK}
    public enum Errors { DENIED("Denied"),
        NOT_FOUND(" not found"),
        ALREADY_EXISTS(" already added"),
        WRONG_PASSWORD("Wrong password"),
        WRONG_PROJECT(" no part of project"),
        CUSTOM("");
        private final String name;
        private Errors(String s) {
            name = s;
        }
        public String toString() {
            return this.name;
        }}
}
