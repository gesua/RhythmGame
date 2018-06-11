package com.example.kitri.rhythmgame;

public class NoteVO {
    private int time;
    private int type;

    public NoteVO(int time, int type) {
        this.time = time;
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
