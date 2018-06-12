package com.example.kitri.rhythmgame;

import android.os.Handler;
import android.os.Message;

public class Down extends Thread {
    boolean next=true;
    boolean wait=false;

    Handler handler;

    public Down(Handler handler) {
        this.handler = handler;

    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    @Override
    public void run() {
        try {
            while (next) {
                while (wait){
                    Thread.sleep(500);
                    //일시정지 하면 대기
                }
                Message message = new Message();
                message.what = 999;

                handler.sendMessage(message);


                Thread.sleep(20); // 프레임 1000/x

            }
        } catch (InterruptedException e) {
            next=false;

        }
    }
}
