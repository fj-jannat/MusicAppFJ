package com.example.fatimajannat.musicapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.fatimajannat.musicapp.Model.Artist;
import com.example.fatimajannat.musicapp.Model.Track;

import java.util.List;

/**
 * Created by ASUS on 2017-07-30.
 */



public class ArtistListAdapter extends ArrayAdapter<Artist> {
    private Activity context;
    List<Artist> artistList;


    public ArtistListAdapter (Activity context, List<Artist> artistList) {
        super(context, R.layout.item_artist_list, artistList );

        this.context = context;
        this.artistList = artistList;
    }



    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.item_artist_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);

        Artist artist = artistList.get(position);
        textViewName.setText(artist.getArtistName());
        textViewGenre.setText(artist.getArtistGenre());

        return listViewItem;

    }

}
