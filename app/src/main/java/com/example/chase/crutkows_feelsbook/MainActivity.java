package com.example.chase.crutkows_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    public static final String EMOTION_MESSAGE = "com.example.myfirstapp.EMOTION";
    public static final int UNIQUE_ID = 1;
    public static final String ID_MESSAGE = "com.example.myfirstapp.ID";
    public static final String FILENAME = "emotions.sav";
    private Map<String, Integer> feelingsCount = new HashMap<String, Integer>();
    private ArrayList<Emotion> emotions = new ArrayList<Emotion>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFromFile();
        initiateDict();
        updateCount();
    }


    /*
    @Override
    public void onStart() {
        super.onStart();
        loadFromFile();
        initiateDict();
        updateCount();
    }
    */


    // Taken from: https://developer.android.com/guide/topics/resources/menu-resource#java
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(this.EMOTION_MESSAGE, this.emotions);
        intent.putExtra(this.ID_MESSAGE, this.UNIQUE_ID);
        startActivity(intent);
        return true;
    }

    public void buttonClicked(View view) {
        TextView feelingCount;
        TextView comment;
        Emotion emotion;

        switch (view.getId()) {
            case R.id.love_button:
                this.feelingsCount.put("Love", this.feelingsCount.get("Love") + 1);
                feelingCount = findViewById(R.id.love_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("Love")));
                emotion = new Love();
                break;

            case R.id.joy_button:
                this.feelingsCount.put("Joy", this.feelingsCount.get("Joy") + 1);
                feelingCount = findViewById(R.id.joy_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("Joy")));
                emotion = new Joy();
                break;

            case R.id.surprise_button:
                this.feelingsCount.put("Surprise", this.feelingsCount.get("Surprise") + 1);
                feelingCount = findViewById(R.id.surprise_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("Surprise")));
                emotion = new Surprise();
                break;

            case R.id.anger_button:
                this.feelingsCount.put("Anger", this.feelingsCount.get("Anger") + 1);
                feelingCount = findViewById(R.id.anger_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("Anger")));
                emotion = new Anger();
                break;

            case R.id.sadness_button:
                this.feelingsCount.put("Sadness", this.feelingsCount.get("Sadness") + 1);
                feelingCount = findViewById(R.id.sadness_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("Sadness")));
                emotion = new Sadness();
                break;

            case R.id.fear_button:
                this.feelingsCount.put("Fear", this.feelingsCount.get("Fear") + 1);
                feelingCount = findViewById(R.id.fear_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("Fear")));
                emotion = new Fear();
                break;

            // This will never hit but emotion needs to be defined in all cases
            default:
                emotion = new Love();
        }

        comment = findViewById(R.id.comment_text);
        emotion.setComment(comment.getText().toString());
        this.emotions.add(emotion);
        comment.setText("");
        saveInFile(emotion);
    }

    private void initiateDict(){
        this.feelingsCount.put("Love", 0);
        this.feelingsCount.put("Joy", 0);
        this.feelingsCount.put("Surprise", 0);
        this.feelingsCount.put("Anger", 0);
        this.feelingsCount.put("Sadness", 0);
        this.feelingsCount.put("Fear", 0);

        for (Emotion emotion: this.emotions) {
            this.feelingsCount.put(emotion.getEmotion(), this.feelingsCount.get(emotion.getEmotion()) + 1);
        }
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            while (line != null) {
                Emotion emotion;
                Date date;
                String[] splitLine = line.split("[|]", 3);

                switch (splitLine[1]){
                    case "Love":
                        emotion = new Love();
                        break;
                    case "Joy":
                        emotion = new Joy();
                        break; 
                    case "Surprise":
                        emotion = new Surprise();
                        break;
                    case "Anger":
                        emotion = new Anger();
                        break;
                    case "Sadness":
                        emotion = new Sadness();
                        break;
                    case "Fear":
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
            FileOutputStream fos = openFileOutput(FILENAME,
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

    private void updateCount() {
        TextView feelingCount;

        feelingCount = findViewById(R.id.love_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("Love")));

        feelingCount = findViewById(R.id.joy_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("Joy")));

        feelingCount = findViewById(R.id.surprise_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("Surprise")));

        feelingCount = findViewById(R.id.anger_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("Anger")));

        feelingCount = findViewById(R.id.sadness_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("Sadness")));

        feelingCount = findViewById(R.id.fear_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("Fear")));
    }

}
