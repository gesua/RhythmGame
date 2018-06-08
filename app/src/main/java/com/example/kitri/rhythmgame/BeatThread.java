package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.media.MediaPlayer;

public class BeatThread extends Thread {
    private MediaPlayer music;
    private long startTime = 0;
    private PlayActivity context;

    private Beat[] beats = {
            new Beat(600, "1"),
            new Beat(700, "3"),
            new Beat(800, "4"),
            new Beat(1200, "1"),
            new Beat(1300, "3"),
            new Beat(1400, "4"),
            new Beat(1800, "1"),
            new Beat(1900, "3"),
            new Beat(2000, "4"),

            new Beat(2400, "1"),
            new Beat(2500, "4"),
            new Beat(2600, "3"),
            new Beat(3600, "2"),
            new Beat(3700, "4"),
            new Beat(3800, "3"),
            new Beat(4200, "3"),
            new Beat(4300, "1"),
            new Beat(4400, "2"),

            new Beat(4800, "3"),
            new Beat(4900, "1"),
            new Beat(5000, "2"),
            new Beat(5400, "4"),
            new Beat(5500, "2"),
            new Beat(5600, "1"),
            new Beat(6000, "3"),
            new Beat(6100, "1"),
            new Beat(6200, "2"),

            new Beat(6600, "3"),
            new Beat(6700, "1"),
            new Beat(6800, "2"),
            new Beat(7200, "1"),
            new Beat(7300, "4"),
            new Beat(7400, "3"),
            new Beat(7800, "4"),
            new Beat(7900, "2"),
            new Beat(8000, "1")
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
