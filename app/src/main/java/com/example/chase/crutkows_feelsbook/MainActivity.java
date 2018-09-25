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
    private static final String FILENAME = "emotions.sav";
    private Map<String, Integer> feelingsCount = new HashMap<String, Integer>();
    private List<Emotion> emotions = new ArrayList<Emotion>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    
    @Override
    public void onStart() {
        super.onStart();
        loadFromFile();
        initiateDict();
        updateCount();
    }


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
        intent.putExtra(this.EMOTION_MESSAGE, this.FILENAME);
        startActivity(intent);
        return true;
    }

    public void buttonClicked(View view) {
        TextView feelingCount;
        TextView comment;
        Emotion emotion;

        switch (view.getId()) {
            case R.id.love_button:
                this.feelingsCount.put("love", this.feelingsCount.get("love") + 1);
                feelingCount = findViewById(R.id.love_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("love")));
                emotion = new Love();
                break;

            case R.id.joy_button:
                this.feelingsCount.put("joy", this.feelingsCount.get("joy") + 1);
                feelingCount = findViewById(R.id.joy_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("joy")));
                emotion = new Joy();
                break;

            case R.id.surprise_button:
                this.feelingsCount.put("surprise", this.feelingsCount.get("surprise") + 1);
                feelingCount = findViewById(R.id.surprise_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("surprise")));
                emotion = new Surprise();
                break;

            case R.id.anger_button:
                this.feelingsCount.put("anger", this.feelingsCount.get("anger") + 1);
                feelingCount = findViewById(R.id.anger_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("anger")));
                emotion = new Anger();
                break;

            case R.id.sadness_button:
                this.feelingsCount.put("sadness", this.feelingsCount.get("sadness") + 1);
                feelingCount = findViewById(R.id.sadness_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("sadness")));
                emotion = new Sadness();
                break;

            case R.id.fear_button:
                this.feelingsCount.put("fear", this.feelingsCount.get("fear") + 1);
                feelingCount = findViewById(R.id.fear_count);
                feelingCount.setText(Integer.toString(this.feelingsCount.get("fear")));
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
        this.feelingsCount.put("love", 0);
        this.feelingsCount.put("joy", 0);
        this.feelingsCount.put("surprise", 0);
        this.feelingsCount.put("anger", 0);
        this.feelingsCount.put("sadness", 0);
        this.feelingsCount.put("fear", 0);

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
        feelingCount.setText(Integer.toString(this.feelingsCount.get("love")));

        feelingCount = findViewById(R.id.joy_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("joy")));

        feelingCount = findViewById(R.id.surprise_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("surprise")));

        feelingCount = findViewById(R.id.anger_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("anger")));

        feelingCount = findViewById(R.id.sadness_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("sadness")));

        feelingCount = findViewById(R.id.fear_count);
        feelingCount.setText(Integer.toString(this.feelingsCount.get("fear")));
    }

}
