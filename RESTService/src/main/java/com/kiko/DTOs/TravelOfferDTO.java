package com.kiko.DTOs;

public class TravelOfferDTO {
    
    private String userId; 
    private String msgId;
    private String location;
    private String tripStartDate;
    private String tripEndDate;
    
    public TravelOfferDTO(){}
    
    public TravelOfferDTO(String userId, String msgId, String location,String tripStartDate,String tripEndDate)
    {
        this.userId = userId;
        this.msgId = msgId;
        this.location = location;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(String tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public String getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(String tripEndDate) {
        this.tripEndDate = tripEndDate;
    }
}
