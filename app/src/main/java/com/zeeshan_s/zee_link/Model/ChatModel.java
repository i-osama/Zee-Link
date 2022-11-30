package com.zeeshan_s.zee_link.Model;

public class ChatModel {
    String message;
    String chatKey;
    String senderID;
    String receiverID;

    long timeMillis;

    public ChatModel(String senderID, String receiverID,String message, String chatKey, long timeMillis) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
        this.chatKey = chatKey;
        this.timeMillis = timeMillis;
    }

    public ChatModel() {
    }

    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public String getMessage() {
        return message;
    }

    public String getChatKey() {
        return chatKey;
    }

    public long getTimeMillis() {
        return timeMillis;
    }
}
