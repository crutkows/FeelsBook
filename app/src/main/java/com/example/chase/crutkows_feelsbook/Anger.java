package com.example.chase.crutkows_feelsbook;

import java.util.Date;

// Class for Anger emotion
public class Anger extends Emotion {

    public Anger() {
        super();
    }

    public Anger(Date time) {
        super(time);
    }

    public String getEmotion() {
        return "Anger";
    }
}

