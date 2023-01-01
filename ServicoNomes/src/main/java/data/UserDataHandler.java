package data;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDataHandler {

    private static final String CSV_FILE = "src/main/java/util/usersInfo.csv";


    public static String[] getUserByNickname(String nickname) {
        List<String[]> currentUsers = readCsvFile();
        for ( String[] user : currentUsers ) {
            if (Objects.equals(nickname, user[0]) ) {
                return user;
            }
        }
        return null;
    }

    public static String[] getUserByPin(String pin) {
        List<String[]> currentUsers = readCsvFile();
        for ( String[] user : currentUsers ) {
            if (Objects.equals(pin, user[1]) ) {
                return user;
            }
        }
        return null;
    }

    // check if nickname and pin is already in use and if the pin belongs to a valid interval
    public static boolean isValidPin(String pin) {
        List<String[]> currentUsers = readCsvFile();
        for (String[] user : currentUsers) {
            if ( Objects.equals(pin, user[1]) ) {
                return false;
            }
        }
        int intPin = Integer.parseInt(pin);
        return intPin >= 8000 && intPin <= 8010;
    }

    public static boolean isValidNickname(String nickname) {
        List<String[]> currentUsers = readCsvFile();
        for ( String[] user : currentUsers ) {
            if (Objects.equals(nickname, user[0]) ) {
                return false;
            }
        }
        return true;
    }
    public static boolean registerNewUser(String nickname, String pin) {
        List<String[]> users = readCsvFile();
        String[] newUser = {nickname, pin};
        users.add(newUser);
        return saveCsvFile(users);
    }
    private static boolean saveCsvFile(List<String[]> users) {
        File csvFile = new File(CSV_FILE);
        try {
            FileWriter outputFile = new FileWriter(csvFile);
            CSVWriter writer = new CSVWriter(outputFile, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
            writer.writeAll(users);
            writer.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static List<String[]> readCsvFile() {
        List<String[]> users = new ArrayList<>();
        try {
            FileReader fr = new FileReader(CSV_FILE, StandardCharsets.UTF_8);
            CSVReader reader = new CSVReader(fr);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String nickname = nextLine[0];
                String pin = nextLine[1];
                String[] user = {nickname, pin};
                users.add(user);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

}
