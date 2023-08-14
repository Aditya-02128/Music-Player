package com.example.new_mp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SongsAdapter extends ArrayAdapter<Song> {
    public SongsAdapter(@NonNull Context context, @NonNull List<Song> objects) {
        super(context,0, objects);
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song,null);
        TextView tvArtist=convertView.findViewById(R.id.tvArtist);
        TextView tvTitle=convertView.findViewById(R.id.tvTitle);
        Song song=getItem(position);
        tvTitle.setText(song.getTitle());
        tvArtist.setText(song.getArtist());
        return convertView;
    }
}
