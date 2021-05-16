package persistence;

import app.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.CreatingFileException;
import core.ReadingFileException;
import core.UserRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoMeUserRepository implements UserRepository {
    ObjectMapper objectMapper = new ObjectMapper();
    String path = "src/dataStore/users.json";
    File file = new File(path);
    List<User> allUsers = null;

    /**
     * Getting user's information
     * @param email user's email
     * @return user information if user exists, else return null
     */
    @Override
    public User get(String email) {
        fillUpCache();
        return allUsers.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

    /**
     * Adding a new user
     * @param user user
     * @return user if successfully added, else return null
     */
    @Override
    public User add(User user) {
        if (exists(user.getEmail())) {
            return null;
        }

        try {
            allUsers.add(user);
            objectMapper.writeValue(file, allUsers);
            return user;
        } catch (IOException ex) {
            throw new CreatingFileException("Problems with writing to the file." + ex.getMessage());
        }
    }

    /**
     * Getting data to cache
     */
    @Override
    public void getData() {
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    FileWriter writer = new FileWriter(file);
                    writer.write("[]");
                    writer.close();
                }
            } catch (IOException ex) {
                throw new CreatingFileException("Problems with writing to the file." + ex.getMessage());
            }
        }

        try {
            allUsers = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, User[].class)));
        } catch (IOException ex) {
            throw new ReadingFileException("Problems with reading the file.\n" + ex.getMessage());
        }
    }

    /**
     * Checking if user already exists
     * @param email user's email
     * @return true if user is already exists, false if does not
     */
    private boolean exists(String email) {
        fillUpCache();
        return allUsers
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null) != null;
    }

    /**
     * Checking if cache is empty
     * @return true if is empty, false if does not
     */
    private boolean isEmpty() {
        return allUsers == null;
    }

    /**
     * Filling up cache if it is empty
     */
    private void fillUpCache() {
        if (isEmpty()) {
            getData();
        }
    }
}
