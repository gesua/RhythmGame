package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.media.MediaPlayer;

public class BeatThread extends Thread {
    private MediaPlayer music;
    private long startTime = 0;
    private PlayActivity context;

    private Beat[] beats = {
            new Beat(1000, "1"),
            new Beat(2000, "2"),
            new Beat(3000, "3"),
            new Beat(4000, "4"),
    };

    public BeatThread(PlayActivity context) {
        this.context = context;
    }

    public void startMusic(Activity context) {
        music = MediaPlayer.create(context, R.raw.romance);
        music.start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startTime = System.currentTimeMillis();
        startMusic(context);

        for (int i = 0; i < beats.length; i++) {
            while (true) {
                if (beats[i].getTime() <= System.currentTimeMillis() - startTime) {
                    context.createNote(beats[i]);
                    break;
                }
            }
        }
    }
}
