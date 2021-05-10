package app;

public class User {
    private int id = 0;
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final int age;

    /**
     *
     * @param email email
     * @param password password
     * @param firstName firstname
     * @param lastName lastname
     * @param age age
     */
    public User(String email, String password, String firstName, String lastName, String userName, int age) {
        this.id = ++this.id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
     *
     * @return user id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return user email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @return user firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @return user lastname
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @return user age
     */
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, lastname: %s, age: %d", getFirstName(), getLastName(), getAge());
    }
}
