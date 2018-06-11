package com.example.kitri.rhythmgame;

import android.os.Handler;
import android.os.Message;

public class Down extends Thread {
    boolean next=true;
    Handler handler;

    public Down(Handler handler) {
        this.handler = handler;

    }


    @Override
    public void run() {
        try {
            while (next) {
                Message message = new Message();
                message.what = 999;

                handler.sendMessage(message);

                Thread.sleep(3); // 프레임 1000/x

            }
        } catch (InterruptedException e) {
            next=false;

        }
    }
}
