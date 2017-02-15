package com.shakirov.dragonsofmugloar.Entity;

/**
 * 
 * @author vadim.shakirov
 */
public class GameResult {
    private final String status;
    private final String message;
    
    public GameResult() {
        status = "";
        message = "";
    }
    
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public boolean isSuccess() { return "Victory".equals(status); }
}
