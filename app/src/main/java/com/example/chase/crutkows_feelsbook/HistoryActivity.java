package com.example.chase.crutkows_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

/*
This activity shows the list of emotions created in descending order(newest first)
and allows the user to select an emotion for editing
*/
public class HistoryActivity extends AppCompatActivity {
    public static final String INDEX_EMOTION_MESSAGE = "com.example.myfirstapp.indexEmotion";
    private ArrayList<Emotion> emotions = new ArrayList<Emotion>();
    private EmotionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Get the variables passed from calling activity
        Intent intent = getIntent();
        int id = intent.getIntExtra(MainActivity.ID_MESSAGE, 0);
        if (id == MainActivity.UNIQUE_ID) { // Check if intent came from MainActivity
            this.emotions = (ArrayList<Emotion>) intent.getSerializableExtra(MainActivity.EMOTION_MESSAGE); // Get the list of emotions form the MainActivity intent
        }

        // Sort the emotions
        Collections.sort(this.emotions, Collections.<Emotion>reverseOrder());

        // Create the list adapter
        this.adapter = new EmotionListAdapter(this, this.emotions);
        ListView listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter(adapter);

        // If an emotion in the list is selected open the edit activity and pass the list of emotion and index of selected emotion
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), EditEmotionActivity.class);
                intent.putExtra(MainActivity.EMOTION_MESSAGE, HistoryActivity.this.emotions);
                intent.putExtra(HistoryActivity.INDEX_EMOTION_MESSAGE, position);
                startActivityForResult(intent, EditEmotionActivity.REQUEST_CODE);
            }
        });
    }

    // Called when the edit activity returns a result (updated list of emotions due to an edit)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EditEmotionActivity.REQUEST_CODE && resultCode == 0 && data != null) {
            // Get the list of emotions and sort them again
            this.emotions = (ArrayList<Emotion>) data.getSerializableExtra(MainActivity.EMOTION_MESSAGE);
            Collections.sort(this.emotions, Collections.<Emotion>reverseOrder());
            this.adapter.refresh(this.emotions); // Update the ListView

        }
    }

}
