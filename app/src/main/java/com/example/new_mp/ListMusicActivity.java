package com.example.new_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ListMusicActivity extends AppCompatActivity {
    ArrayList<Song> songarraylist;
    ListView lvsongs;
    SongsAdapter songsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_music);
        lvsongs=findViewById(R.id.lvSongs);
        songarraylist=new ArrayList<>();
        SongsAdapter songsAdapter=new SongsAdapter(this,songarraylist);
        lvsongs.setAdapter(songsAdapter);
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},99);
            return;
        }
        else
            getSongs();

        lvsongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = songarraylist.get(position);
                Intent openmusicplayer = new Intent(ListMusicActivity.this, MusicPlayerActivity.class);
                openmusicplayer.putExtra("song", song);
                openmusicplayer.putExtra("musiclist",songarraylist);
                openmusicplayer.putExtra("point", position);
                startActivity(openmusicplayer);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==99)
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                getSongs();
    }

    private void getSongs(){
        ContentResolver contentresolver=getContentResolver();
        Uri songsUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songcursor=contentresolver.query(songsUri,null,null,null);
        if(songcursor!=null && songcursor.moveToFirst()){
            int indexTitle=songcursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int indexArtist=songcursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int indexData=songcursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                String title=songcursor.getString(indexTitle);
                String artist=songcursor.getString(indexArtist);
                String path=songcursor.getString(indexData);
                songarraylist.add(new Song(title,artist,path));
            }while(songcursor.moveToNext());
        }
        try{songsAdapter.notifyDataSetChanged();}
        catch (NullPointerException e){}

    }
}