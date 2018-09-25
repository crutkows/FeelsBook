package com.example.chase.crutkows_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private String filename;
    private List<Emotion> emotions = new ArrayList<Emotion>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        this.filename = intent.getStringExtra(MainActivity.EMOTION_MESSAGE);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadFromFile();
        createListView();
    }

    private void createListView(){
        LinearLayout linear_layout = findViewById(R.id.LinearLayout);
        //linear_layout.setDi


        for (Emotion emotion:this.emotions) {
            TextView textview = new TextView(this);
            textview.setText(emotion.getEmotion() + " - " + emotion.getDateString());
            textview.setTextSize(20.0f);
            textview.setHeight(120);
            linear_layout.addView(textview);
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            while (line != null) {
                Emotion emotion;
                Date date;
                String[] splitLine = line.split("[|]", 3);

                switch (splitLine[1]){
                    case "love":
                        emotion = new Love();
                        break;
                    case "joy":
                        emotion = new Joy();
                        break;
                    case "surprise":
                        emotion = new Surprise();
                        break;
                    case "anger":
                        emotion = new Anger();
                        break;
                    case "sadness":
                        emotion = new Sadness();
                        break;
                    case "fear":
                        emotion = new Fear();
                        break;
                    default:
                        emotion = new Love();
                }

                emotion.setDate(Long.parseLong(splitLine[0]));

                if (splitLine.length == 3) {
                    emotion.setComment(splitLine[2]);
                }

                this.emotions.add(emotion);
                line = in.readLine();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void saveInFile(Emotion emotion) {
        try {
            FileOutputStream fos = openFileOutput(filename,
                    Context.MODE_APPEND);
            fos.write(new String(Long.toString(emotion.getDate()) + "|" + emotion.getEmotion() + "|" + emotion.getComment() + "\n")
                    .getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
