package com.example.fatimajannat.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fatimajannat.musicapp.Model.Artist;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private TextView textViewGenre;
    private Spinner spinnerGenres;
    private Button buttonEditArtist, buttonDeleteArtist, buttonSaveInfo;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextName = (EditText)findViewById(R.id.editTextName);
        textViewGenre = (TextView)findViewById(R.id.textViewGenre);
        spinnerGenres = (Spinner)findViewById(R.id.spinnerGenres);
        buttonEditArtist = (Button) findViewById(R.id.buttonEditArtist);
        buttonDeleteArtist = (Button) findViewById(R.id.buttonDeleteArtist);
        buttonSaveInfo = (Button) findViewById(R.id.buttonSaveInfo);

        Intent intent = getIntent();
        id=intent.getStringExtra(ArtistActivity.ARTIST_ID_KEY);

        spinnerGenres.setVisibility(View.GONE);     //(View.INVISIBLE);
        buttonSaveInfo.setVisibility(View.GONE);
        editTextName.setText(intent.getStringExtra(ArtistActivity.ARTIST_NAME_KEY));
        textViewGenre.setText(intent.getStringExtra(ArtistActivity.ARTIST_GENRE_KEY));

        buttonEditArtist.setOnClickListener(this);
        buttonDeleteArtist.setOnClickListener(this);
        buttonSaveInfo.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view==buttonEditArtist){
            editTextName.setEnabled(true);
            buttonSaveInfo.setVisibility(View.VISIBLE);
            textViewGenre.setVisibility(View.GONE);
            spinnerGenres.setVisibility(View.VISIBLE);
            buttonEditArtist.setVisibility(View.GONE);

        }

        if(view==buttonSaveInfo){
            editTextName.setEnabled(true);
            spinnerGenres.setVisibility(View.GONE);
            textViewGenre.setVisibility(View.VISIBLE);
            buttonEditArtist.setVisibility(View.VISIBLE);
            buttonSaveInfo.setVisibility(View.GONE);

            updateArtist();

        }
        if(view==buttonDeleteArtist){
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("artists").child(id);

            databaseRef.removeValue();

            Toast.makeText(this, "Artist Deleted!", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateArtist() {

        // getting the specific artist reference
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("artists").child(id);

        // getting the value
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString();

        // creating the object
        Artist artist = new Artist(id,name,genre);

        // set the value
        databaseRef.setValue(artist);

        Toast.makeText(this, "Artist Info Updated!", Toast.LENGTH_SHORT).show();
    }
}
