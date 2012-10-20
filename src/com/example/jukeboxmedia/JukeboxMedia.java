package com.example.jukeboxmedia;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

class Mp3Filter implements FilenameFilter {
        public boolean accept(File dir, String name) {
                return (name.endsWith(".mp3"));
        }
}

public class JukeboxMedia extends ListActivity {
 
        private static final String path = new String("/sdcard/");
        private List<String> songs = new ArrayList<String>();
        private MediaPlayer mp = new MediaPlayer();
        private int currentPosition = 0;
 
        @Override
        public void onCreate(Bundle icicle) {
                super.onCreate(icicle);
                setContentView(R.layout.activity_main);
                updateSongList();
        }
        
        public void updateSongList() {
        File home = new File(path);
        if (home.listFiles(new Mp3Filter()).length > 0) {
                for (File file : home.listFiles(new Mp3Filter())) {
                        songs.add(file.getName());
                }
 
                ArrayAdapter<String> songList = new ArrayAdapter<String>(this,
                                R.layout.song, songs);
                setListAdapter(songList);
        }
}

@Override
protected void onListItemClick(ListView l, View v, int position, long id) {
        currentPosition = position;
        playSong(path + songs.get(position));
}

private void playSong(String songPath) {
        try {
 
                mp.reset();
                mp.setDataSource(songPath);
                mp.prepare();
                mp.start();
 
        } catch (IOException e) {
                Log.v(getString(R.string.app_name), e.getMessage());
        }
}

private void nextSong() {
        if (++currentPosition >= songs.size()) {
                // Last song, just reset currentPosition
                currentPosition = 0;
        } else {
                // Play next song
                playSong(path + songs.get(currentPosition));
        }
}

}