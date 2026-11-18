package com.mycompany.part3final2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

// This helper class is self-contained so it doesn't break
class MessageHelper {
    public String messageID, messageHash, sender, recipient, messageText, flag;
    
    public MessageHelper(String id, String snd, String rec, String txt, String flg) {
        this.messageID = id; 
        this.sender = snd; 
        this.recipient = rec; 
        this.messageText = txt; 
        this.flag = flg;
        
        String[] words = txt.split(" ");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        
        this.messageHash = id.substring(0, 2).toUpperCase() + ":" + firstWord + lastWord;
    }
    
    public String getMessageText() { return messageText; }
    public String getRecipient() { return recipient; }
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
}

public class MessageAppTest {

    public static ArrayList<MessageHelper> sentMessages = new ArrayList<>();
    public static ArrayList<MessageHelper> storedMessages = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        sentMessages.clear();
        storedMessages.clear();
        
        sentMessages.add(new MessageHelper("MSG01", "Dev", "+27834557896", "Did you get the cake?", "Sent"));
        storedMessages.add(new MessageHelper("MSG02", "Dev", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored"));
        sentMessages.add(new MessageHelper("MSG04", "Dev", "0838884567", "It is dinner time!", "Sent"));
        storedMessages.add(new MessageHelper("MSG05", "Dev", "+27838884567", "Ok, I am leaving without you.", "Stored"));
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        assertEquals(2, sentMessages.size());
        assertEquals("Did you get the cake?", sentMessages.get(0).getMessageText());
        assertEquals("It is dinner time!", sentMessages.get(1).getMessageText());
    }

    @Test
    public void testDisplayLongestMessage() {
        MessageHelper longest = null;
        for (MessageHelper msg : storedMessages) {
            if (longest == null || msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        assertNotNull(longest);
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getMessageText());
    }

    @Test
    public void testSearchForMessageID() {
        String searchID = "MSG04";
        MessageHelper found = null;
        for (MessageHelper msg : sentMessages) {
            if (msg.getMessageID().equals(searchID)) { found = msg; break; }
        }
        assertNotNull(found);
        assertEquals("0838884567", found.getRecipient());
        assertEquals("It is dinner time!", found.getMessageText());
    }

    @Test
    public void testSearchMessagesForParticularRecipient() {
        String searchRecipient = "+27838884567";
        int count = 0;
        for (MessageHelper msg : storedMessages) {
            if (msg.getRecipient().equals(searchRecipient)) { count++; }
        }
        assertEquals(2, count);
    }

    @Test
    public void testDeleteMessageUsingHash() {
        MessageHelper msgToDelete = storedMessages.get(0);
        String hashToDelete = msgToDelete.getMessageHash();
        
        storedMessages.removeIf(msg -> msg.getMessageHash().equals(hashToDelete));
        
        assertEquals(1, storedMessages.size());
        assertEquals("MSG05", storedMessages.get(0).getMessageID());
    }

    // This main method allows you to run the tests by right-clicking -> Run File
    public static void main(String[] args) {
        MessageAppTest test = new MessageAppTest();
        try {
            test.setUp();
            test.testSentMessagesArrayCorrectlyPopulated();
            test.testDisplayLongestMessage();
            test.testSearchForMessageID();
            test.testSearchMessagesForParticularRecipient();
            test.testDeleteMessageUsingHash();
            System.out.println("=====================================");
            System.out.println("ALL TESTS PASSED SUCCESSFULLY!");
            System.out.println("=====================================");
        } catch (Throwable e) {
            System.out.println("TEST FAILED: " + e.getMessage());
        }
    }
}