package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.user.Client;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileManager {

    private static final String CLIENTS_FILE = "data/clients.json";

    public static void saveClientsToFile(List<Client> clients) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(CLIENTS_FILE)) {
            gson.toJson(clients, writer);
            System.out.println("Clients saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving clients: " + e.getMessage());
        }
    }

    public static List<Client> loadClientsFromFile() {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(CLIENTS_FILE)) {
            List<Client> clients = gson.fromJson(reader, List.class);
            return clients;
        } catch (IOException e) {
            System.err.println("Error loading clients: " + e.getMessage());
            return null;
        }
    }
}