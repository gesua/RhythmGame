package com.example.kitri.rhythmgame;

import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;

import java.util.List;

public class Note extends Thread {
    private ConstraintLayout iv;
    private Handler handler;
    private List<NoteVO> number;
    private boolean wait = false;
    private int setNoteTime=3000;
    private int waitTime=0;

    public int getSetNoteTime() {
        return setNoteTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }



    public Note(ConstraintLayout iv, Handler handler, List<NoteVO> number) {
        this.iv = iv;
        this.handler = handler;
        this.number = number;

    }


    @Override
    public void run() {

        try {
            Thread.sleep(setNoteTime); //시작시 첫노트 대기시간
            for (int i = 0; i < number.size(); i++) { //size=노트개수
                Message msg = new Message();
                msg.what = number.get(i).getType(); //노트 라인
                msg.obj = iv.getViewById(i);
                msg.arg2 = 0;

                handler.sendMessage(msg);
                while(wait){

                }

                Thread.sleep(number.get(i).getTime()); //다음 노트 간격
            }
            Thread.sleep(waitTime); //마지막 노트 나온후 대기시간
            Message msg = new Message();
            msg.what = 5;  //노트  다 나왔을시 5번

            handler.sendMessage(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
