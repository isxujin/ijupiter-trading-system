import java.security.MessageDigest;
import java.util.Base64;

public class TestPassword {
    public static void main(String[] args) {
        // Just generate some test strings to see if we can identify the password hash algorithm
        String password = "admin123";
        String salt = "ijupiter";
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPassword = password + "{" + salt + "}";
            byte[] hash = md.digest(saltedPassword.getBytes("UTF-8"));
            String encoded = Base64.getEncoder().encodeToString(hash);
            System.out.println("Salted password: " + saltedPassword);
            System.out.println("SHA-256 hash: " + encoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
