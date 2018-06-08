package com.example.kitri.rhythmgame;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class Note {
    private final int NOTE_SPEED = 1;

    private int x, y = 150;
    private ImageView iv;
    private Handler handler;
    private boolean dead = false;

    public Note(float x, ImageView iv, Handler handler) {
        this.x = (int) x;
        this.iv = iv;
        this.handler = handler;

        Message msg = new Message();
        msg.what = 3; // 3:노트 생성
        msg.obj = iv;
        handler.sendMessage(msg);
    }

    // 노트 떨어짐
    public void drop() {
        Message msg = new Message();
        msg.what = 1; // 1:노트 움직이기
        msg.obj = iv;
        msg.arg1 = x;
        msg.arg2 = y;
        handler.sendMessage(msg);

        y += NOTE_SPEED;


        if (y >= 2000) {
            delete();
        }
    }

    // 노트 삭제
    public void delete() {
        Message msg = new Message();
        msg.what = 2; // 2:삭제
        msg.obj = iv;
        handler.sendMessage(msg);

        dead = true;
    }

    public boolean isDead() {
        return dead;
    }
}
