package com.example.chase.crutkows_feelsbook;

import java.util.Date;

// Abstract class for the emotions
public abstract class Emotion {
    private long time;
    private String comment;

    public Emotion() {
        this.time = System.currentTimeMillis();
    }

    public Emotion(long time) {
        this.time = time;
    }

    public long getDate() { return this.time; }
    public void setDate(long time) {this.time = time;}
    public String getDateString() {return new Date(this.time).toString();}

    public String getComment() { return this.comment; }
    public void setComment(String comment) {this.comment = comment;}

    public abstract String getEmotion();
}