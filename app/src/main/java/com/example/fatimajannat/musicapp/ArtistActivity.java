package com.example.fatimajannat.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fatimajannat.musicapp.Model.Artist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArtistActivity extends AppCompatActivity implements View.OnClickListener{

    public final static String ARTIST_NAME_KEY = "com.example.fatimajannat.artistName";
    public final static String ARTIST_ID_KEY = "com.example.fatimajannat.artistId";
    public final static String ARTIST_GENRE_KEY = "com.example.fatimajannat.artistGenre";

    private EditText editTextName;
    private Spinner spinnerGenres;
    private Button buttonAddArtist;

    //database reference
    DatabaseReference databaseArtists;

    // a list to store all the artists from firebase database
    List<Artist> artistList;
    ListView artistsListView;

    @Override
    protected void onStart() {
        super.onStart();


        //attaching value event listener
        databaseArtists.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artistList.clear(); //clearing the previous list


                //iterating through all the nodes
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //getting artist
                    Artist artist = snapshot.getValue(Artist.class);

                    //adding artist to the list
                    artistList.add(artist);

                }


                //creating adapter
                ArtistListAdapter artistAdapter = new ArtistListAdapter(ArtistActivity.this, artistList);

                //set the adapter to the list view
                artistsListView.setAdapter(artistAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        artistList = new ArrayList<>();

        editTextName = (EditText) findViewById(R.id.editTextName);
        spinnerGenres =(Spinner) findViewById(R.id.spinnerGenres);
        buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);

        artistsListView = (ListView) findViewById(R.id.listViewArtists);

        buttonAddArtist.setOnClickListener(this);

        // attaching listener to list view
        artistsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // getting the selected artist
                Artist artist = artistList.get(i);

                // creating an intent
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);

                //sending artist name and id to intent
                intent.putExtra(ARTIST_ID_KEY, artist.getArtistId());
                intent.putExtra(ARTIST_NAME_KEY, artist.getArtistName());
                intent.putExtra(ARTIST_GENRE_KEY, artist.getArtistGenre());

                //starting the intent
                startActivity(intent);
            }
        });



    }


    @Override
    public void onClick(View view) {
        if(view==buttonAddArtist){
            addArtist();
        }

    }

     /*
        This method will add artist to
        the realtime database
     */


    private void addArtist(){
        //getting the values
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){

            // getting a unique id using push().getKey()
            //which will create a new unique id for every object
            String id = databaseArtists.push().getKey();

            //creating an artist object to insert into firebase
            Artist artist = new Artist(id, name, genre);

            //saving the artist object
            databaseArtists.child(id).setValue(artist);

            //setting the edit text name to blank again
            editTextName.setText("");

            //display a success toast message
            Toast.makeText(this, "Artist Added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter name!", Toast.LENGTH_SHORT).show();
        }
    }
}
