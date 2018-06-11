package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.media.MediaPlayer;

public class BeatThread extends Thread {
    private final int BEAT_SPEED=800;

    private MediaPlayer music;
    private long startTime = 0;
    private PlayActivity context;

    private Beat[] beats = {
            new Beat(600+BEAT_SPEED, "1"),
            new Beat(700+BEAT_SPEED, "3"),
            new Beat(800+BEAT_SPEED, "4"),
            new Beat(1300+BEAT_SPEED, "1"),
            new Beat(1400+BEAT_SPEED, "3"),
            new Beat(1500+BEAT_SPEED, "4"),
            new Beat(1900+BEAT_SPEED, "1"),
            new Beat(2000+BEAT_SPEED, "3"),
            new Beat(2100+BEAT_SPEED, "4"),
//1
            new Beat(2400+BEAT_SPEED, "1"),
            new Beat(2500+BEAT_SPEED, "4"),
            new Beat(2600+BEAT_SPEED, "3"),
            new Beat(3100+BEAT_SPEED, "2"),
            new Beat(3200+BEAT_SPEED, "4"),
            new Beat(3300+BEAT_SPEED, "3"),
            new Beat(3600+BEAT_SPEED, "3"),
            new Beat(3700+BEAT_SPEED, "1"),
            new Beat(3800+BEAT_SPEED, "2"),
//2
            new Beat(4200+BEAT_SPEED, "3"),
            new Beat(4300+BEAT_SPEED, "1"),
            new Beat(4400+BEAT_SPEED, "2"),
            new Beat(4700+BEAT_SPEED, "4"),
            new Beat(4800+BEAT_SPEED, "2"),
            new Beat(4900+BEAT_SPEED, "1"),
            new Beat(5200+BEAT_SPEED, "3"),
            new Beat(5300+BEAT_SPEED, "1"),
            new Beat(5400+BEAT_SPEED, "2"),
//3
            new Beat(5900+BEAT_SPEED, "3"),
            new Beat(6000+BEAT_SPEED, "1"),
            new Beat(6100+BEAT_SPEED, "2"),
            new Beat(6400+BEAT_SPEED, "1"),
            new Beat(6500+BEAT_SPEED, "4"),
            new Beat(6600+BEAT_SPEED, "3"),
            new Beat(7100+BEAT_SPEED, "4"),
            new Beat(7200+BEAT_SPEED, "2"),
            new Beat(7300+BEAT_SPEED, "1"),
//4
            new Beat(7800+BEAT_SPEED, "2"),
            new Beat(7900+BEAT_SPEED, "4"),
            new Beat(8000+BEAT_SPEED, "3"),
            new Beat(8400+BEAT_SPEED, "2"),
            new Beat(8500+BEAT_SPEED, "4"),
            new Beat(8600+BEAT_SPEED, "3"),
            new Beat(9000+BEAT_SPEED, "2"),
            new Beat(9100+BEAT_SPEED, "4"),
            new Beat(9200+BEAT_SPEED, "3"),
//5
            new Beat(9600+BEAT_SPEED, "2"),
            new Beat(9700+BEAT_SPEED, "4"),
            new Beat(9800+BEAT_SPEED, "3"),
            new Beat(10200+BEAT_SPEED, "4"),
            new Beat(10300+BEAT_SPEED, "3"),
            new Beat(10400+BEAT_SPEED, "2"),
            new Beat(10800+BEAT_SPEED, "2"),
            new Beat(10900+BEAT_SPEED, "3"),
            new Beat(11000+BEAT_SPEED, "4"),
//6
            new Beat(11400+BEAT_SPEED, "2"),
            new Beat(11500+BEAT_SPEED, "3"),
            new Beat(11600+BEAT_SPEED, "4"),
            new Beat(12000+BEAT_SPEED, "1"),
            new Beat(12100+BEAT_SPEED, "2"),
            new Beat(12200+BEAT_SPEED, "3"),
            new Beat(12600+BEAT_SPEED, "3"),
            new Beat(12700+BEAT_SPEED, "2"),
            new Beat(12800+BEAT_SPEED, "1"),
//7
            new Beat(13200+BEAT_SPEED, "3"),
            new Beat(13300+BEAT_SPEED, "2"),
            new Beat(13400+BEAT_SPEED, "1"),
            new Beat(13700+BEAT_SPEED, "4"),
            new Beat(13800+BEAT_SPEED, "2"),
            new Beat(13900+BEAT_SPEED, "1"),
            new Beat(14200+BEAT_SPEED, "1"),
            new Beat(14300+BEAT_SPEED, "4"),
            new Beat(14400+BEAT_SPEED, "3"),
//8
            new Beat(14800+BEAT_SPEED, "2"),
            new Beat(14900+BEAT_SPEED, "3"),
            new Beat(15000+BEAT_SPEED, "4"),
            new Beat(15300+BEAT_SPEED, "1"),
            new Beat(15400+BEAT_SPEED, "3"),
            new Beat(15500+BEAT_SPEED, "4"),
            new Beat(15800+BEAT_SPEED, "2"),
            new Beat(15900+BEAT_SPEED, "3"),
            new Beat(16000+BEAT_SPEED, "4"),
//9
            new Beat(16600+BEAT_SPEED, "4"),
            new Beat(16700+BEAT_SPEED, "1"),
            new Beat(16800+BEAT_SPEED, "2"),
            new Beat(17200+BEAT_SPEED, "2"),
            new Beat(17300+BEAT_SPEED, "3"),
            new Beat(17400+BEAT_SPEED, "4"),
            new Beat(17800+BEAT_SPEED, "1"),
            new Beat(17900+BEAT_SPEED, "3"),
            new Beat(18000+BEAT_SPEED, "4"),
//10
            new Beat(18600+BEAT_SPEED, "1"),
            new Beat(18700+BEAT_SPEED, "3"),
            new Beat(18800+BEAT_SPEED, "2"),
            new Beat(19200+BEAT_SPEED, "3"),
            new Beat(19300+BEAT_SPEED, "2"),
            new Beat(19400+BEAT_SPEED, "1"),
            new Beat(19800+BEAT_SPEED, "4"),
            new Beat(19900+BEAT_SPEED, "2"),
            new Beat(20000+BEAT_SPEED, "1"),
//11
            new Beat(20400+BEAT_SPEED, "3"),
            new Beat(20500+BEAT_SPEED, "2"),
            new Beat(20600+BEAT_SPEED, "1"),
            new Beat(21000+BEAT_SPEED, "2"),
            new Beat(21100+BEAT_SPEED, "4"),
            new Beat(21200+BEAT_SPEED, "3"),
            new Beat(21600+BEAT_SPEED, "1"),
            new Beat(21700+BEAT_SPEED, "4"),
            new Beat(21800+BEAT_SPEED, "3"),
//12
            new Beat(22200+BEAT_SPEED, "1"),
            new Beat(22300+BEAT_SPEED, "2"),
            new Beat(22400+BEAT_SPEED, "4"),
            new Beat(22800+BEAT_SPEED, "1"),
            new Beat(22900+BEAT_SPEED, "3"),
            new Beat(23000+BEAT_SPEED, "4"),
            new Beat(23400+BEAT_SPEED, "1"),
            new Beat(23500+BEAT_SPEED, "2"),
            new Beat(23600+BEAT_SPEED, "3"),
//13
            new Beat(24000+BEAT_SPEED, "2"),
            new Beat(24100+BEAT_SPEED, "1"),
            new Beat(24200+BEAT_SPEED, "3"),
            new Beat(24600+BEAT_SPEED, "1"),
            new Beat(24700+BEAT_SPEED, "3"),
            new Beat(24800+BEAT_SPEED, "2"),
            new Beat(25200+BEAT_SPEED, "2"),
            new Beat(25300+BEAT_SPEED, "3"),
            new Beat(25400+BEAT_SPEED, "1"),
//14
            new Beat(25800+BEAT_SPEED, "4"),
            new Beat(25900+BEAT_SPEED, "1"),
            new Beat(26000+BEAT_SPEED, "2"),
            new Beat(26400+BEAT_SPEED, "4"),
            new Beat(26500+BEAT_SPEED, "1"),
            new Beat(26600+BEAT_SPEED, "3"),
            new Beat(27000+BEAT_SPEED, "4"),
            new Beat(27100+BEAT_SPEED, "2"),
            new Beat(27200+BEAT_SPEED, "3"),
//15
            new Beat(27600+BEAT_SPEED, "4"),
            new Beat(27700+BEAT_SPEED, "3"),
            new Beat(27800+BEAT_SPEED, "2"),
            new Beat(27900+BEAT_SPEED, "1")
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
            Thread.sleep(2500);
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