package com.example.fatimajannat.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fatimajannat.musicapp.Model.Track;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TrackActivity extends AppCompatActivity {

    Button buttonAddTrack;
    EditText editTextTrackName;
    SeekBar seekBarRating;
    TextView textViewRating, textViewArtist;
    ListView listViewTracks;

    DatabaseReference databaseTracks;

    List<Track> trackList;


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);


        Intent intent = getIntent();
        /*
            this time, we are not referencing to any direct node
            but inside the node track we are creating a new child with the artist id
            and inside that node we will store all the tracks with unique ids
         */
        databaseTracks = FirebaseDatabase.getInstance()
                .getReference("tracks").child(intent.getStringExtra(ArtistActivity.ARTIST_ID_KEY));

        //initialize all views
        buttonAddTrack= (Button) findViewById(R.id.buttonAddTrack);
        editTextTrackName = (EditText) findViewById(R.id.editTextTrack);
        seekBarRating = (SeekBar) findViewById(R.id.seekBarRating);
        listViewTracks = (ListView) findViewById(R.id.listViewTracks);
        textViewArtist = (TextView) findViewById(R.id.textViewArtist);
        textViewRating = (TextView) findViewById(R.id.textViewRating);

        trackList = new ArrayList<>();

        textViewArtist.setText(intent.getStringExtra(ArtistActivity.ARTIST_NAME_KEY));

        seekBarRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewRating.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTrack();
            }
        });

    }

    public void addTrack(){
        String name = editTextTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if(!TextUtils.isEmpty(name)){
            // getting unique id for adding track
            String id = databaseTracks.push().getKey();

            //creating track object
            Track track = new Track(id, name, rating);

            // saving in a child node
            databaseTracks.child(id).setValue(track);

            Toast.makeText(this, "Track Saved", Toast.LENGTH_SHORT).show();
            editTextTrackName.setText(""); // clearing the edit text again when saved

        } else {
            Toast.makeText(this, "Please enter a track name", Toast.LENGTH_SHORT).show();
        }
    }
}
