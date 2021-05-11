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

    @Override
    public User get(String email) {
        checkCache();
        return allUsers.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst().orElse(null);
    }

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

    private boolean exists(String email) {
        checkCache();
        return allUsers
                .stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null) != null;
    }

    private boolean isEmpty() {
        return allUsers == null;
    }

    private void checkCache() {
        if (isEmpty()) {
            getData();
        }
    }
}
