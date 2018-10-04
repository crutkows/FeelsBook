package com.example.chase.crutkows_feelsbook;

import java.util.Date;

// Class for Surprise emotion
public class Surprise extends Emotion {

    public Surprise() {
        super();
    }

    public Surprise(Date time) {
        super(time);
    }

    public String getEmotion() {
        return "Surprise";
    }
}

