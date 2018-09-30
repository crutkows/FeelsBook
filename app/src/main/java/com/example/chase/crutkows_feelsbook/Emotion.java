package com.example.chase.crutkows_feelsbook;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Abstract class for the emotions
public abstract class Emotion implements Comparable<Emotion>, Serializable {
    private Date time;
    private String comment;

    public Emotion() {
        this.time = new Date();
    }
    public Emotion(Date date) {
        this.time = date;
    }

    @Override
    public int compareTo(Emotion emotion){
        if (this.time.getTime() > emotion.getDate()) { return 1;}
        else if (this.time.getTime() == emotion.getDate()) {return 0;}
        else {return -1;}
    }

    public long getDate() { return this.time.getTime(); }
    public void setDate(long time) {this.time = new Date(time);}
    public void setDate(Date date) {this.time = date;}
    public void setDate(String dateString) {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            this.time = formatter.parse(dateString);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public String getDateString() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return  formatter.format(this.time);
    }

    public String getComment() { return this.comment; }
    public void setComment(String comment) {this.comment = comment;}

    public abstract String getEmotion();
}