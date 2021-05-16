package persistence;

import app.Reservation;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.CreatingFileException;
import core.ReadingFileException;
import core.ReservationRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryImpl implements ReservationRepository {
    List<Reservation> allReservations = null;
    ObjectMapper objectMapper = new ObjectMapper();
    String path = "src/dataStore/reservations.json";
    File file = new File(path);

    /**
     * Getting all user's reservations
     * @param email user's email
     * @return list of reservations
     */
    @Override
    public List<Reservation> getAll(String email) {
        fillUpCache();
        return allReservations.stream().filter(reservation -> reservation.getUser().getEmail().equalsIgnoreCase(email)).collect(Collectors.toList());
    }

    /**
     * Adding a new reservation
     * @param reservation new reservation
     * @return new reservation
     */
    @Override
    public Reservation add(Reservation reservation) {
        if (exists(reservation)) {
            return null;
        }

        try {
            allReservations.add(reservation);
            objectMapper.writeValue(file, allReservations);
            return reservation;
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
            allReservations = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Reservation[].class)));
        } catch (IOException ex) {
            throw new ReadingFileException("Problems with reading the file.\n" + ex.getMessage());
        }
    }

    /**
     * Checking if the reservation already exists
     * @param newReservation new reservation to be added
     * @return true if same reservation is already exists, false if does not
     */
    private boolean exists(Reservation newReservation) {
        return allReservations
                .stream()
                .anyMatch(reservation -> reservation.getDate().equals(newReservation.getDate()) && reservation.getTime().equals(newReservation.getTime()));
    }

    /**
     * Checking cache
     * @return true if cache is empty, false if does not
     */
    private boolean isEmpty() {
        return allReservations == null;
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
