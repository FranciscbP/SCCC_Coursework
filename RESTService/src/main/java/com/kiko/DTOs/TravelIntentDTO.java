/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kiko.DTOs;

/**
 *
 * @author Kiko
 */
public class TravelIntentDTO {
    private String userId; 
    private String msgId;
    private String intentMessage;

    public TravelIntentDTO(){}
    
    public TravelIntentDTO(String userId,String msgId,String intentMessage){
        this.userId = userId;
        this.msgId = msgId;
        this.intentMessage = intentMessage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getIntentMessage() {
        return intentMessage;
    }

    public void setIntentMessage(String intentMessage) {
        this.intentMessage = intentMessage;
    }
    
    
}
