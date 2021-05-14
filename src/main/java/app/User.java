package app;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final int age;

    /**
     * Constructor for mapping objects from json file
     * @param email email
     * @param password password
     * @param firstName firstname
     * @param lastName lastname
     * @param age age
     */
    public User(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("age") int age
    ) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    /**
     *
     * @return user information
     */
    @Override
    public String toString() {
        return String.format("Name: %s\nLastname: %s\nAge: %d", getFirstName(), getLastName(), getAge());
    }
}
