package com.example.maju.mazdor.Model;

import java.util.Date;

public class ChatMessage {
    private String job_title;
    private String job_rate;
    private String job_location;
    private String  job_description;

    public ChatMessage() {
    }

    public ChatMessage(String job_title, String job_rate, String job_location, String job_description) {
        this.job_title = job_title;
        this.job_rate = job_rate;
        this.job_location = job_location;
        this.job_description = job_description;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_rate() {
        return job_rate;
    }

    public void setJob_rate(String job_rate) {
        this.job_rate = job_rate;
    }

    public String getJob_location() {
        return job_location;
    }

    public void setJob_location(String job_location) {
        this.job_location = job_location;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }
}
