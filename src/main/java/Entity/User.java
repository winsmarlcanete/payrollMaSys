package Entity;

// This is a placeholder User class.
// You might need to add more fields and methods based on your application's requirements.
public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private int accessLevel;
    private int accountStatus;
    private String userName;
    private String securityQuestion;
    private String securityAnswerHash;

    // Constructor
    public User(String firstName, String lastName, String email, String passwordHash,
                int accessLevel, int accountStatus, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.accessLevel = accessLevel;
        this.accountStatus = accountStatus;
        this.userName = userName;
    }

    // Getter methods
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public String getUserName() {
        return userName;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswerHash() {
        return securityAnswerHash;
    }

    // Setter methods for security question and answer
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswerHash(String securityAnswerHash) {
        this.securityAnswerHash = securityAnswerHash;
    }

    // You can add more setter methods if needed for other fields
}
