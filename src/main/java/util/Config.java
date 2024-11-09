package util;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getDbUsername() {
        return dotenv.get("DB_USERNAME");
    }

    public static String getDbPassword() {
        return dotenv.get("DB_PASSWORD");
    }

    public static String getEmailUsername() {
        return dotenv.get("EMAIL_USERNAME");
    }

    public static String getEmailPassword() {
        return dotenv.get("EMAIL_PASSWORD");
    }
}
