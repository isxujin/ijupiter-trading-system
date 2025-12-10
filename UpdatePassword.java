import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UpdatePassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "admin123";
        String encodedPassword = encoder.encode(password);
        System.out.println("New encoded password: " + encodedPassword);
        
        // SQL to update the password in the database
        System.out.println("UPDATE syst_user SET password = '" + encodedPassword + "' WHERE username = 'admin';");
    }
}
