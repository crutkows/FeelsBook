package com.example.chase.crutkows_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {
    public static final String INDEX_EMOTION_MESSAGE = "com.example.myfirstapp.indexEmotion";
    private ArrayList<Emotion> emotions = new ArrayList<Emotion>();
    private ListView listView;
    private EmotionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        int id = intent.getIntExtra(MainActivity.ID_MESSAGE, 0);
        if (id == MainActivity.UNIQUE_ID) {
            this.emotions = (ArrayList<Emotion>) intent.getSerializableExtra(MainActivity.EMOTION_MESSAGE);
        }

        Collections.sort(this.emotions, Collections.<Emotion>reverseOrder());

        this.adapter = new EmotionListAdapter(this, this.emotions);
        listView = (ListView) findViewById(R.id.ListView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), EditEmotion.class);
                intent.putExtra(MainActivity.EMOTION_MESSAGE, HistoryActivity.this.emotions);
                intent.putExtra(HistoryActivity.INDEX_EMOTION_MESSAGE, position);
                startActivityForResult(intent, EditEmotion.REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EditEmotion.REQUEST_CODE && resultCode == 0 && data != null) {
            this.emotions = (ArrayList<Emotion>) data.getSerializableExtra(MainActivity.EMOTION_MESSAGE);
            Collections.sort(this.emotions, Collections.<Emotion>reverseOrder());
            this.adapter.refresh(this.emotions);

        }
    }

}
