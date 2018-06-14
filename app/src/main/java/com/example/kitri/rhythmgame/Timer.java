package com.example.kitri.rhythmgame;

import android.os.Handler;
import android.os.Message;


public class Timer extends Thread {
    private Handler handler;

    public Timer(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            Message message=new Message();
            message.what=3333;
            handler.sendMessage(message);
            Thread.sleep(1000);
            Message message2=new Message();
            message2.what=2222;
            handler.sendMessage(message2);
            Thread.sleep(1000);
            Message message3=new Message();
            message3.what=1111;
            handler.sendMessage(message3);
            Thread.sleep(1000);
            Message message4=new Message();
            message4.what=9999;
            handler.sendMessage(message4);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
