package util;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    // Load environment variables from .env file
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    // Retrieve email username from environment variables
    public static String getEmailUsername() {
        return dotenv.get("EMAIL_USERNAME", "defaultEmailUsername"); 
    }

    // Retrieve email password from environment variables
    public static String getEmailPassword() {
        return dotenv.get("EMAIL_PASSWORD", "defaultEmailPassword"); 
    }
}
