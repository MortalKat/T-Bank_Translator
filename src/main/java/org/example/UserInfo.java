package org.example;

import java.time.LocalDateTime;

public class UserInfo {

    private long id;
    private String ipAddress;
    private String inputLine;
    private String outputLine;
    private LocalDateTime timeStamp;

    public UserInfo(String ipAddress, String inputLine, String outputLine,LocalDateTime timeStamp) {
        this.ipAddress = ipAddress;
        this.inputLine = inputLine;
        this.outputLine = outputLine;
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getInputLine() {
        return inputLine;
    }

    public void setInputLine(String inputLine) {
        this.inputLine = inputLine;
    }

    public String getOutputLine() {
        return outputLine;
    }

    public void setOutputLine(String outputLine) {
        this.outputLine = outputLine;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
