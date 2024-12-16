package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class FileManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> void saveToJson(String filePath, List<T> list) {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(list, writer);
            System.out.println("Data saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving data to " + filePath + ": " + e.getMessage());
        }
    }

    public static <T> List<T> loadFromJson(String filePath, Type typeOfList) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, typeOfList);
        } catch (IOException e) {
            System.err.println("Error loading data from " + filePath + ": " + e.getMessage());
            return null;
        }
    }
}