package com.rj.ecommerce_email_service.contract.v1;

public enum EmailStatus {
    SENT,
    FAILED,
    DELIVERED,
    OPENED;

    public static EmailStatus fromString (String status) {
        try {
            return EmailStatus.valueOf(status);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid email status: " + status, e);
        }
    }

}
