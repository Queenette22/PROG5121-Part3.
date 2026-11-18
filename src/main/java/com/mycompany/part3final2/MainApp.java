package com.part3;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        MessageManager.loadFromJson();
        
        if (MessageManager.storedMessages.isEmpty()) {
            populateTestData();
        }

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Send Messages (Part 1/2)");
            System.out.println("2. Show recently sent messages (Part 1/2)");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages (Part 3)");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println(">> [Insert your Part 1/2 Send Message logic here] <<");
                    break;
                case "2":
                    System.out.println(">> [Insert your Part 1/2 Show Messages logic here] <<");
                    break;
                case "3":
                    running = false;
                    System.out.println("Exiting...");
                    break;
                case "4":
                    storedMessagesMenu(scanner);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    private static void storedMessagesMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Stored Messages Menu ---");
            System.out.println("a. Display sender and recipient of all stored messages");
            System.out.println("b. Display the longest stored message");
            System.out.println("c. Search for a message ID");
            System.out.println("d. Search messages for a particular recipient");
            System.out.println("e. Delete a message using the message hash");
            System.out.println("f. Display full report");
            System.out.println("g. Back to main menu");
            System.out.print("Select option: ");
            
            String choice = scanner.nextLine().toLowerCase();

            switch (choice) {
                case "a": displaySendersAndRecipients(); break;
                case "b": displayLongestMessage(); break;
                case "c": searchByMessageID(scanner); break;
                case "d": searchByRecipient(scanner); break;
                case "e": deleteByHash(scanner); break;
                case "f": displayFullReport(); break;
                case "g": back = true; break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private static void displaySendersAndRecipients() {
        for (Message msg : MessageManager.storedMessages) {
            System.out.println("Sender: " + msg.getSender() + " -> Recipient: " + msg.getRecipient());
        }
    }

    private static void displayLongestMessage() {
        Message longest = null;
        for (Message msg : MessageManager.storedMessages) {
            if (longest == null || msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        if (longest != null) {
            System.out.println("Longest Message: \"" + longest.getMessageText() + "\"");
        } else {
            System.out.println("No stored messages found.");
        }
    }

    private static void searchByMessageID(Scanner scanner) {
        System.out.print("Enter Message ID to search: ");
        String id = scanner.nextLine();
        for (Message msg : MessageManager.storedMessages) {
            if (msg.getMessageID().equalsIgnoreCase(id)) {
                System.out.println("Recipient: " + msg.getRecipient() + "\nMessage: " + msg.getMessageText());
                return;
            }
        }
        System.out.println("Message ID not found.");
    }

    private static void searchByRecipient(Scanner scanner) {
        System.out.print("Enter Recipient number: ");
        String recipient = scanner.nextLine();
        boolean found = false;
        for (Message msg : MessageManager.storedMessages) {
            if (msg.getRecipient().equals(recipient)) {
                System.out.println("- " + msg.getMessageText());
                found = true;
            }
        }
        if (!found) System.out.println("No messages found for this recipient.");
    }

    private static void deleteByHash(Scanner scanner) {
        System.out.print("Enter Message Hash to delete: ");
        String hash = scanner.nextLine();
        
        boolean removed = MessageManager.storedMessages.removeIf(msg -> msg.getMessageHash().equalsIgnoreCase(hash));
        
        if (removed) {
            System.out.println("Message successfully deleted.");
            MessageManager.saveToJson();
        } else {
            System.out.println("Hash not found.");
        }
    }

    private static void displayFullReport() {
        System.out.println("\n--- Full Stored Messages Report ---");
        for (Message msg : MessageManager.storedMessages) {
            System.out.println("Hash: " + msg.getMessageHash() + 
                               " | Recipient: " + msg.getRecipient() + 
                               " | Message: " + msg.getMessageText());
        }
    }

    private static void populateTestData() {
        MessageManager.addMessage("MSG01", "Developer", "+27834557896", "Did you get the cake?", "Sent");
        MessageManager.addMessage("MSG02", "Developer", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        MessageManager.addMessage("MSG03", "Developer", "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        MessageManager.addMessage("MSG04", "Developer", "0838884567", "It is dinner time!", "Sent");
        MessageManager.addMessage("MSG05", "Developer", "+27838884567", "Ok, I am leaving without you.", "Stored");
    }
}
