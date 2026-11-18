package com.part3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MessageManager {
    public static ArrayList<Message> sentMessages = new ArrayList<>();
    public static ArrayList<Message> disregardedMessages = new ArrayList<>();
    public static ArrayList<Message> storedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();

    private static final String JSON_FILE = "stored_messages.json";

    public static void addMessage(String id, String sender, String recipient, String text, String flag) {
        Message msg = new Message(id, sender, recipient, text, flag);
        messageIDs.add(msg.getMessageID());
        messageHashes.add(msg.getMessageHash());
        
        if (flag.equalsIgnoreCase("Sent")) {
            sentMessages.add(msg);
        } else if (flag.equalsIgnoreCase("Stored")) {
            storedMessages.add(msg);
            saveToJson();
        } else if (flag.equalsIgnoreCase("Disregard")) {
            disregardedMessages.add(msg);
        }
    }

    public static void saveToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(JSON_FILE)) {
            gson.toJson(storedMessages, writer);
        } catch (IOException e) {
            System.out.println("Error saving to JSON: " + e.getMessage());
        }
    }

    public static void loadFromJson() {
        File file = new File(JSON_FILE);
        if (!file.exists()) return;

        Gson gson = new Gson();
        try (Reader reader = new FileReader(JSON_FILE)) {
            Type listType = new TypeToken<ArrayList<Message>>() {}.getType();
            ArrayList<Message> loadedMessages = gson.fromJson(reader, listType);
            if (loadedMessages != null) {
                storedMessages = loadedMessages;
                for (Message msg : storedMessages) {
                    messageIDs.add(msg.getMessageID());
                    messageHashes.add(msg.getMessageHash());
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading from JSON: " + e.getMessage());
        }
    }
}
