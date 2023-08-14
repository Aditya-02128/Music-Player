package com.example.new_mp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

public class MusicPlayerActivity extends AppCompatActivity {
    ImageView album_art;
    TextView tvtime,tvduration,tvArtist,tvTitle;
    SeekBar seektime,seekvol;
    Button play,next,prev,loop;
    MediaPlayer musicplayer;
    ArrayList<Song> songsarraylist;
    int pos,flag=1;
    Thread progseek;
    Bitmap alb;

    public Bitmap fetchAlbumArt(String filePath) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(filePath);

        byte[] artworkBytes = retriever.getEmbeddedPicture();

        if (artworkBytes != null && artworkBytes.length > 0) {
            return BitmapFactory.decodeByteArray(artworkBytes, 0, artworkBytes.length);
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);}catch(NullPointerException ignored){}
        songsarraylist=(ArrayList<Song>)getIntent().getSerializableExtra("musiclist");
        Song song=(Song)getIntent().getSerializableExtra("song");
        pos= getIntent().getIntExtra("point",songsarraylist.indexOf(song));

        album_art=findViewById(R.id.album);
        tvtime=findViewById(R.id.tvtime);
        tvduration=findViewById(R.id.tvduration);
        seektime=findViewById(R.id.seekBartime);
        seekvol=findViewById(R.id.seekBarvolume);
        play=findViewById(R.id.btnplay);
        tvTitle=findViewById(R.id.tvtitle);
        tvArtist=findViewById(R.id.tvartist);
        tvTitle.setText(song.getTitle());
        tvArtist.setText(song.getArtist());
        next=(Button)findViewById(R.id.next);
        prev=(Button)findViewById(R.id.prev);
        loop=(Button)findViewById(R.id.loop);
        alb=fetchAlbumArt(song.getPath());
        if (alb != null) {
            album_art.setImageBitmap(alb);
        } else {
            album_art.setImageResource(R.drawable.default_art);
        }
        musicplayer=new MediaPlayer();
        try {
            musicplayer.setDataSource(song.getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            musicplayer.prepare();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        musicplayer.setLooping(false);
        musicplayer.seekTo(0);
        musicplayer.setVolume(0.5f,0.5f);
        seektime.setProgress(0);
        loop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!musicplayer.isLooping()){
                    musicplayer.setLooping(true);
                    loop.setBackgroundResource(R.drawable.loop);
                }
                else{
                    musicplayer.setLooping(false);
                    loop.setBackgroundResource(R.drawable.loop_stop);
                }

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicplayer.isPlaying()){
                    musicplayer.pause();
                    play.setBackgroundResource(R.drawable.play);
                }
                else{
                    musicplayer.start();
                    play.setBackgroundResource(R.drawable.pause);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                musicplayer.stop();
                musicplayer.release();
                pos++;
                Log.i("MusicPlayerActivity",String.valueOf(pos));
                if (pos >= songsarraylist.size()) {
                    pos = 0;
                }

                Song sng = songsarraylist.get(pos);
                try {
                    Log.i("MusicPlayerActivity", sng.getPath());
                    musicplayer = new MediaPlayer();
                    musicplayer.setDataSource(sng.getPath());
                    musicplayer.prepare();
                    musicplayer.setLooping(false);
                    loop.setBackgroundResource(R.drawable.loop_stop);
                    musicplayer.seekTo(0);
                    musicplayer.setVolume(0.5f, 0.5f);
                    musicplayer.start();
                    play.setBackgroundResource(R.drawable.pause);
                    seektime.setProgress(0);
                    tvTitle.setText(sng.getTitle());
                    tvArtist.setText(sng.getArtist());
                    alb=fetchAlbumArt(sng.getPath());
                    if (alb != null) {
                        album_art.setImageBitmap(alb);
                    } else {
                        album_art.setImageResource(R.drawable.default_art);
                    }
                    flag=1;
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately
                }
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                musicplayer.stop();
                musicplayer.release();
                pos--;
                Log.i("MusicPlayerActivity",String.valueOf(pos));
                if (pos <0) {
                    pos = 0;
                }

                Song sng = songsarraylist.get(pos);
                try {
                    Log.i("MusicPlayerActivity", sng.getPath());
                    musicplayer = new MediaPlayer();
                    musicplayer.setDataSource(sng.getPath());
                    musicplayer.prepare();
                    musicplayer.setLooping(false);
                    loop.setBackgroundResource(R.drawable.loop_stop);
                    musicplayer.seekTo(0);
                    musicplayer.setVolume(0.5f, 0.5f);
                    musicplayer.start();
                    play.setBackgroundResource(R.drawable.pause);
                    seektime.setProgress(0);
                    tvTitle.setText(sng.getTitle());
                    tvArtist.setText(sng.getArtist());
                    alb=fetchAlbumArt(sng.getPath());
                    if (alb != null) {
                        album_art.setImageBitmap(alb);
                    } else {
                        album_art.setImageResource(R.drawable.default_art);
                    }
                    flag=1;
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately
                }
            }
        });
        String duration= milliToString(musicplayer.getDuration());
        tvduration.setText(duration);
        seekvol.setProgress(50);
        seekvol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float vol=progress/100f;
                musicplayer.setVolume(vol,vol);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seektime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    musicplayer.seekTo((musicplayer.getDuration()*progress/100));
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        progseek=new Thread(new Runnable() {
            @Override

            public void run() {
                    while (true) {
                        if(flag==1) {
                            assert musicplayer != null;
                            if (musicplayer.isPlaying() || musicplayer != null) {
                                double current = musicplayer.getCurrentPosition();
                                double dur = musicplayer.getDuration();
                                double prog = (current / dur) * 100;
                                int icur = (int) current;
                                String elapse = milliToString(icur);
                                tvtime.setText(elapse);
                                seektime.setProgress((int) prog);
                                Log.i("MusicPlayerActivity", "ruuuunin");
                                if(seektime.getProgress()==99){
                                    flag = 0;
                                    musicplayer.stop();
                                    musicplayer.release();
                                    pos++;
                                    Log.i("MusicPlayerActivity",String.valueOf(pos));
                                    if (pos >= songsarraylist.size()) {
                                        pos = 0;
                                    }

                                    Song sng = songsarraylist.get(pos);
                                    try {
                                        Log.i("MusicPlayerActivity", sng.getPath());
                                        musicplayer = new MediaPlayer();
                                        musicplayer.setDataSource(sng.getPath());
                                        musicplayer.prepare();
                                        musicplayer.setLooping(false);
                                        //loop.setBackgroundResource(R.drawable.loop_stop);
                                        musicplayer.seekTo(0);
                                        musicplayer.setVolume(0.5f, 0.5f);
                                        musicplayer.start();
                                        play.setBackgroundResource(R.drawable.pause);
                                        seektime.setProgress(0);
                                        tvTitle.setText(sng.getTitle());
                                        tvArtist.setText(sng.getArtist());
                                        alb=fetchAlbumArt(sng.getPath());
                                        if (alb != null) {
                                            album_art.setImageBitmap(alb);
                                        } else {
                                            album_art.setImageResource(R.drawable.default_art);
                                        }
                                        flag=1;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        // Handle the exception appropriately
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                        // Handle the exception appropriately
                                    }
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
            }
        });
        progseek.start();
    }




    public String milliToString(int time){
        String stime="";
        int minutes=time/1000/60;
        int seconds=time/1000%60;
        if(minutes==0 && seconds<10){
            stime+="0:";
        stime+=seconds;}
        else{
            stime=String.valueOf(minutes)+":"+String.valueOf(seconds);
        }
        return stime;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            musicplayer.stop();
            musicplayer.release();
            finish();
            if(musicplayer.isPlaying()){
                musicplayer.stop();
                musicplayer.release();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        musicplayer.stop();
        super.onBackPressed();
    }
}