package com.example.kitri.rhythmgame;

import android.os.Handler;
import android.os.Message;


public class MusicThead extends Thread {
    private Handler handler;

    public MusicThead(Handler handler) {
        this.handler=handler;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(1400); //노래시작 지연시간
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message message =new Message();
        message.what=777;
        handler.sendMessage(message);

    }
}
