package com.example.chase.crutkows_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class EditEmotion extends AppCompatActivity {
    public static final int REQUEST_CODE = 3;
    private ArrayList<Emotion> emotions;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emotion);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        this.emotions = (ArrayList<Emotion>) intent.getSerializableExtra(MainActivity.EMOTION_MESSAGE);
        this.index = intent.getIntExtra(HistoryActivity.INDEX_EMOTION_MESSAGE, 0);

        buildUI();

    }

    private void buildUI() {
        Emotion emotion = this.emotions.get(this.index);

        TextView emotionTitle = (TextView) findViewById(R.id.emotionTypeText);
        EditText emotionDate = (EditText) findViewById(R.id.dateTextEdit);
        EditText emotionComment = (EditText) findViewById(R.id.commentTextEdit);

        emotionTitle.setText(emotion.getEmotion());
        emotionDate.setText(emotion.getDateString());
        emotionComment.setText(emotion.getComment());
    }

    public void onDeleteClick(View view){
        emotions.remove(this.index);

        overwriteFile();

        Intent intent = new Intent();
        intent.putExtra(MainActivity.EMOTION_MESSAGE, this.emotions);
        setResult(0, intent);
        finish();
    }

    public void onSaveClick(View view) {
        Emotion emotion = this.emotions.get(this.index);

        EditText emotionDate = (EditText) findViewById(R.id.dateTextEdit);
        EditText emotionComment = (EditText) findViewById(R.id.commentTextEdit);

        emotion.setComment(emotionComment.getText().toString());
        emotion.setDate(emotionDate.getText().toString());

        this.emotions.set(this.index, emotion);

        overwriteFile();

        Intent intent = new Intent();
        intent.putExtra(MainActivity.EMOTION_MESSAGE, this.emotions);
        setResult(0, intent);
        finish();
    }

    private void overwriteFile() {
        try {
            FileOutputStream fos = openFileOutput(MainActivity.FILENAME,
                    Context.MODE_PRIVATE);
            for (Emotion emotion: this.emotions) {
                fos.write(new String(Long.toString(emotion.getDate()) + "|" + emotion.getEmotion() + "|" + emotion.getComment() + "\n")
                        .getBytes());
            }
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
