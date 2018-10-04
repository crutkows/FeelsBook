package com.example.chase.crutkows_feelsbook;

import java.util.Date;

// Class for Sadness emotion
public class Sadness extends Emotion {

    public Sadness() {
        super();
    }

    public Sadness(Date time) {
        super(time);
    }

    public String getEmotion() {
        return "Sadness";
    }
}
