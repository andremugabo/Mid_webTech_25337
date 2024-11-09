package util;

import io.github.cdimascio.dotenv.Dotenv;

public class Config {
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    public static String getEmailUsername() {
        String emailUsername = dotenv.get("EMAIL_USERNAME", "defaultEmailUsername");
        System.out.println("EMAIL_USERNAME: " + emailUsername);  
        return emailUsername;
    }

    public static String getEmailPassword() {
        String emailPassword = dotenv.get("EMAIL_PASSWORD", "defaultEmailPassword");
        System.out.println("EMAIL_PASSWORD: " + emailPassword);  
        return emailPassword;
    }
    public static void main(String []args) {
    	System.out.println("Email Username: " + getEmailUsername());
    	System.out.println("Email Password: " + getEmailPassword());

    }
}

