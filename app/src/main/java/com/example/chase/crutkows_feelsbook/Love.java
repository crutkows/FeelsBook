package com.example.chase.crutkows_feelsbook;


import java.util.Date;

// Class for Love emotion
public class Love extends Emotion {

    public Love() {
        super();
    }

    public Love(Date time) {
        super(time);
    }

    public String getEmotion() {
        return "Love";
    }
}

