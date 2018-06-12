package com.example.kitri.rhythmgame;

import android.os.Handler;
import android.os.Message;


public class MusicThead extends Thread {
    private Handler handler;
    private int time;

    public MusicThead(Handler handler,int time) {
        this.handler=handler;
        this.time=time;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(time); //노래시작 지연시간
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message message =new Message();
        message.what=777;
        handler.sendMessage(message);

    }
}
