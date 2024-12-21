package services;

import com.google.gson.*;
import models.account.Account;
import models.account.CurrentAccount;
import models.account.SavingsAccount;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileManager {
    // Method to save a list of objects to a file
    public static <T> void saveToJson(String filePath, List<T> data) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Account.class, new AccountAdapter())  // Register the custom Account adapter
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())  // Register the custom LocalDateTime adapter
                .create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);  // Serialize the data to JSON format and write to file
            System.out.println("Data saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    // Method to load data from a file into a list of objects
    public static <T> List<T> loadFromJson(String filePath, Type typeOfT) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Account.class, new AccountAdapter())  // Register the custom Account adapter
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())  // Register the custom LocalDateTime adapter
                .create();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, typeOfT);  // Deserialize JSON data from the file into the specified list type
        } catch (IOException e) {
            System.err.println("Error reading data from file: " + e.getMessage());
            return null;  // Return null if there was an error loading the data
        }
    }
}

class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.format(formatter));  // Serialize LocalDateTime to String
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return LocalDateTime.parse(json.getAsString(), formatter);  // Deserialize String to LocalDateTime
    }
}

class AccountAdapter implements JsonSerializer<Account>, JsonDeserializer<Account> {
    @Override
    public JsonElement serialize(Account account, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("accountNumber", account.accountNumber);
        jsonObject.addProperty("accountType", account.accountType);  // Serialize accountType (SAVINGS or CURRENT)
        jsonObject.addProperty("balance", account.getBalance());
        jsonObject.addProperty("status", account.getStatus());
        jsonObject.addProperty("interestRate", account.getInterestRate());
        return jsonObject;
    }

    @Override
    public Account deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("accountType").getAsString();  // Deserialize based on accountType field
        Account account = null;

        if ("SAVINGS".equals(type)) {
            account = context.deserialize(json, SavingsAccount.class);  // Deserialize to SavingsAccount
        } else if ("CURRENT".equals(type)) {
            account = context.deserialize(json, CurrentAccount.class);  // Deserialize to CurrentAccount
        }

        return account;
    }
}