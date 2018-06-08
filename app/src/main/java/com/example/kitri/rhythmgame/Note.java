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

    public Note(String noteType, ImageView iv, Handler handler) {
        switch (noteType) {
            case "1":
                x = 70;
                break;
            case "2":
                x = 320;
                break;
            case "3":
                x = 560;
                break;
            case "4":
                x = 800;
                break;
        }
        this.iv = iv;
        this.handler = handler;

        Message msg = new Message();
        msg.what = 3; // 3:노트 생성
        msg.obj = iv;
        handler.sendMessage(msg);
    }

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
