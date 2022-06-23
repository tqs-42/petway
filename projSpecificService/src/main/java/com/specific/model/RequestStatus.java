package com.specific.model;

public enum RequestStatus {
    PENDING, PROCESSING, DELIVERING, DELIVERED, CANCELLED;

    public static RequestStatus getEnum(String num) {
        switch (num) {
            case "0":
                return RequestStatus.PENDING;             
            case "1":
                return RequestStatus.PROCESSING; 
            case "2":
                return RequestStatus.DELIVERING; 
            case "3":
                return RequestStatus.DELIVERED; 
            case "4":
                return RequestStatus.DELIVERED; 
            default:
                return RequestStatus.DELIVERED; 
        }
    }
}
