package Algorithms;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class sha256 {
    public static String stringToSHA256(String input) {
        try {
            // Get a SHA-256 digest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Hash the input string and get the byte array
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            // Convert byte array into hex string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = String.format("%02x", b);
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static void main(String[] args) {
        String text = "Hello, world!";
        String hashed = stringToSHA256(text);
        System.out.println("SHA-256 Hash: " + hashed);
    }
}
