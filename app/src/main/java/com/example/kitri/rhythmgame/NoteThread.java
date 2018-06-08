package com.example.kitri.rhythmgame;

import android.util.Log;

import java.util.ArrayList;

public class NoteThread extends Thread {
    private final int SLEEP_TIME = 1;
    private ArrayList<Note> noteList = new ArrayList<>();

    public void noteAdd(Note note) {
        noteList.add(note);
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (int i = 0; i < noteList.size(); i++) {
                    if (noteList.get(i).isDead()) {
                        noteList.remove(i);
                    } else {


                        noteList.get(i).drop();

                    }
                }
                Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
