package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.media.MediaPlayer;

public class BeatThread extends Thread {
    private final int BEAT_SPEED=1500;

    private MediaPlayer music;
    private long startTime = 0;
    private PlayActivity context;

    private Beat[] beats = {
            new Beat(600+BEAT_SPEED, "1"),
            new Beat(700+BEAT_SPEED, "3"),
            new Beat(800+BEAT_SPEED, "4"),
            new Beat(1200+BEAT_SPEED, "1"),
            new Beat(1300+BEAT_SPEED, "3"),
            new Beat(1400+BEAT_SPEED, "4"),
            new Beat(1800+BEAT_SPEED, "1"),
            new Beat(1900+BEAT_SPEED, "3"),
            new Beat(2000+BEAT_SPEED, "4"),

            new Beat(2400+BEAT_SPEED, "1"),
            new Beat(2500+BEAT_SPEED, "4"),
            new Beat(2600+BEAT_SPEED, "3"),
            new Beat(3600+BEAT_SPEED, "2"),
            new Beat(3700+BEAT_SPEED, "4"),
            new Beat(3800+BEAT_SPEED, "3"),
            new Beat(4200+BEAT_SPEED, "3"),
            new Beat(4300+BEAT_SPEED, "1"),
            new Beat(4400+BEAT_SPEED, "2"),

            new Beat(4800+BEAT_SPEED, "3"),
            new Beat(4900+BEAT_SPEED, "1"),
            new Beat(5000+BEAT_SPEED, "2"),
            new Beat(5400+BEAT_SPEED, "4"),
            new Beat(5500+BEAT_SPEED, "2"),
            new Beat(5600+BEAT_SPEED, "1"),
            new Beat(6000+BEAT_SPEED, "3"),
            new Beat(6100+BEAT_SPEED, "1"),
            new Beat(6200+BEAT_SPEED, "2"),

            new Beat(6600+BEAT_SPEED, "3"),
            new Beat(6700+BEAT_SPEED, "1"),
            new Beat(6800+BEAT_SPEED, "2"),
            new Beat(7200+BEAT_SPEED, "1"),
            new Beat(7300+BEAT_SPEED, "4"),
            new Beat(7400+BEAT_SPEED, "3"),
            new Beat(7800+BEAT_SPEED, "4"),
            new Beat(7900+BEAT_SPEED, "2"),
            new Beat(8000+BEAT_SPEED, "1"),

            new Beat(8400+BEAT_SPEED, "2"),
            new Beat(8500+BEAT_SPEED, "4"),
            new Beat(8600+BEAT_SPEED, "3"),
            new Beat(9000+BEAT_SPEED, "4"),
            new Beat(9100+BEAT_SPEED, "3"),
            new Beat(9200+BEAT_SPEED, "2"),
            new Beat(9600+BEAT_SPEED, "2"),
            new Beat(9700+BEAT_SPEED, "3"),
            new Beat(9800+BEAT_SPEED, "4"),

            new Beat(10200+BEAT_SPEED, "2"),
            new Beat(10300+BEAT_SPEED, "3"),
            new Beat(10400+BEAT_SPEED, "4"),
            new Beat(10800+BEAT_SPEED, "1"),
            new Beat(10900+BEAT_SPEED, "2"),
            new Beat(11000+BEAT_SPEED, "3"),
            new Beat(11600+BEAT_SPEED, "3"),
            new Beat(11700+BEAT_SPEED, "2"),
            new Beat(11800+BEAT_SPEED, "1")
    };

    public BeatThread(PlayActivity context) {
        this.context = context;
    }

    public void startMusic(Activity context) {
        music = MediaPlayer.create(context, R.raw.romance);
        music.start();
    }

    public void stopMusic(){
        music.stop();
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
