package com.example.demo.exception;

public class ExternalServiceException extends RuntimeException {
    
    private String serviceName;
    
    public ExternalServiceException(String serviceName, String message) {
        super(String.format("Error communicating with %s: %s", serviceName, message));
        this.serviceName = serviceName;
    }
    
    public ExternalServiceException(String serviceName, String message, Throwable cause) {
        super(String.format("Error communicating with %s: %s", serviceName, message), cause);
        this.serviceName = serviceName;
    }
    
    public String getServiceName() {
        return serviceName;
    }
}
