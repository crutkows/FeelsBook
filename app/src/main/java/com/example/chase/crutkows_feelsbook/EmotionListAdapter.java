package com.example.chase.crutkows_feelsbook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

// This class is a custom ListAdpater that creates the on screen list of emotions from an
// inputted array of Emotions
public class EmotionListAdapter extends ArrayAdapter<Emotion> {
    private final Activity context;
    private final ArrayList<Emotion> emotions;

    //Constructor
    public EmotionListAdapter(Activity context, ArrayList<Emotion> emotions) {
        super(context, R.layout.listview_item, emotions);
        this.context = context;
        this.emotions = emotions;
    }

    // Called for each row/item in the ListView
    public View getView (int position, View view, ViewGroup parent) {
        Emotion emotion = getItem(position); // get emotion at current position

        // inflate and get the view layout from the listview_item.xml file
        LayoutInflater inflater = context.getLayoutInflater();
        View list_row = inflater.inflate(R.layout.listview_item, parent, false);

        TextView upperText = (TextView) list_row.findViewById(R.id.textUpper);
        TextView lowerText = (TextView) list_row.findViewById(R.id.textLower);

        // Set the two text items in the row to the emotion type and emotion date
        upperText.setText(emotion.getEmotion());
        lowerText.setText(emotion.getDateString());

        return list_row; // return the view(row)

    }

    // Recreates the ListView when changes have been made to the list of emotions
    public void refresh(ArrayList<Emotion> emotions) {
        this.clear();
        this.addAll(emotions);
        notifyDataSetChanged();
    }
}
