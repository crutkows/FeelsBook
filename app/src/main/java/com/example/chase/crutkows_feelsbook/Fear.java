package com.example.chase.crutkows_feelsbook;

import java.util.Date;

public class Fear extends Emotion {

    public Fear() {
        super();
    }

    public Fear(Date time) {
        super(time);
    }

    public String getEmotion() {
        return "Fear";
    }
}

