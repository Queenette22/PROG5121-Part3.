package com.part3;

import java.io.Serializable;

public class Message implements Serializable {
    private String messageID;
    private String messageHash;
    private String sender;
    private String recipient;
    private String messageText;
    private String flag;

    public Message(String messageID, String sender, String recipient, String messageText, String flag) {
        this.messageID = messageID;
        this.sender = sender;
        this.recipient = recipient;
        this.messageText = messageText;
        this.flag = flag;
        this.messageHash = createMessageHash();
    }

    private String createMessageHash() {
        if (messageText == null || messageText.isEmpty()) return "EMPTY";
        String firstTwoID = messageID.substring(0, 2).toUpperCase();
        String[] words = messageText.split(" ");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "").toUpperCase();
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "").toUpperCase();
        return firstTwoID + ":" + firstWord + lastWord;
    }

    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getSender() { return sender; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getFlag() { return flag; }
}