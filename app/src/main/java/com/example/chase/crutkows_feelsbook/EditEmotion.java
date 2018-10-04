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


//This activity allows the user to edit or delete the comment and/or the date contained in an emotion
public class EditEmotion extends AppCompatActivity {
    public static final int REQUEST_CODE = 3;
    private ArrayList<Emotion> emotions;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emotion);

        // Remove back button from the actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        this.emotions = (ArrayList<Emotion>) intent.getSerializableExtra(MainActivity.EMOTION_MESSAGE); // List of emotions
        this.index = intent.getIntExtra(HistoryActivity.INDEX_EMOTION_MESSAGE, 0); // Index of selected emotion in list

        buildUI(); // Set the text in the UI elements

    }

    // Set the Title text, date text and comment text in the UI
    private void buildUI() {
        Emotion emotion = this.emotions.get(this.index);

        TextView emotionTitle = (TextView) findViewById(R.id.emotionTypeText);
        EditText emotionDate = (EditText) findViewById(R.id.dateTextEdit);
        EditText emotionComment = (EditText) findViewById(R.id.commentTextEdit);

        emotionTitle.setText(emotion.getEmotion());
        emotionDate.setText(emotion.getDateString());
        emotionComment.setText(emotion.getComment());
    }

    // Called if user clicks the delete button
    public void onDeleteClick(View view){
        emotions.remove(this.index); // Remove emotion from the list of emotions

        overwriteFile(); // Apply changes to saved file of emotions

        // Close activity and return the updated emotion list
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EMOTION_MESSAGE, this.emotions);
        setResult(0, intent);
        finish();
    }

    // Called if the user clicks the save button
    public void onSaveClick(View view) {
        // Get emotion from list
        Emotion emotion = this.emotions.get(this.index);

        EditText emotionDate = (EditText) findViewById(R.id.dateTextEdit);
        EditText emotionComment = (EditText) findViewById(R.id.commentTextEdit);

        //Update the comment and date of the emotion
        emotion.setComment(emotionComment.getText().toString());
        emotion.setDate(emotionDate.getText().toString());

        // Update emotion in list
        this.emotions.set(this.index, emotion);

        overwriteFile(); // Save changes to file

        // Close activity and return updated list of emotions
        Intent intent = new Intent();
        intent.putExtra(MainActivity.EMOTION_MESSAGE, this.emotions);
        setResult(0, intent);
        finish();
    }

    // Overwrites the current saved file of emotions with the new list of emotions
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
