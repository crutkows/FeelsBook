package com.example.chase.crutkows_feelsbook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class EmotionListAdapter extends ArrayAdapter<Emotion> {
    private final Activity context;
    private final ArrayList<Emotion> emotions;

    public EmotionListAdapter(Activity context, ArrayList<Emotion> emotions) {
        super(context, R.layout.listview_item, emotions);
        this.context = context;
        this.emotions = emotions;
    }

    public View getView (int position, View view, ViewGroup parent) {
        Emotion emotion = getItem(position);

        LayoutInflater inflater = context.getLayoutInflater();
        View list_row = inflater.inflate(R.layout.listview_item, parent, false);

        TextView upperText = (TextView) list_row.findViewById(R.id.textUpper);
        TextView lowerText = (TextView) list_row.findViewById(R.id.textLower);

        upperText.setText(emotion.getEmotion());
        lowerText.setText(emotion.getDateString());

        return list_row;

    }

    public void refresh(ArrayList<Emotion> emotions) {
        this.clear();
        this.addAll(emotions);
        notifyDataSetChanged();
    }
}
