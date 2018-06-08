package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class PlayActivity extends Activity {
    private ConstraintLayout layout_play;
    private Button btn_key1, btn_key2, btn_key3, btn_key4;
    private ImageView iv_hitbox1, iv_hitbox2, iv_hitbox3, iv_hitbox4;
    private Handler handler;

    NoteThread noteThread = new NoteThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        layout_play = findViewById(R.id.layout_play);

        btn_key1 = findViewById(R.id.btn_key1);
        btn_key2 = findViewById(R.id.btn_key2);
        btn_key3 = findViewById(R.id.btn_key3);
        btn_key4 = findViewById(R.id.btn_key4);

        iv_hitbox1 = findViewById(R.id.iv_hitbox1);
        iv_hitbox2 = findViewById(R.id.iv_hitbox2);
        iv_hitbox3 = findViewById(R.id.iv_hitbox3);
        iv_hitbox4 = findViewById(R.id.iv_hitbox4);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) { // 노트 밑으로 움직임
                    ((ImageView) msg.obj).setX(msg.arg1);
                    ((ImageView) msg.obj).setY(msg.arg2);
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);
                } else if (msg.what == 2) { // 노트 삭제
                    layout_play.removeView((View) msg.obj);
                }
            }
        };

        Beat[] beats = {
                new Beat(1000, "1"),
                new Beat(1000, "2"),
                new Beat(1000, "3"),
                new Beat(1000, "4"),
        };
        noteThread.start();

        for (int i = 0; i < beats.length; i++) {
            Note note = new Note(beats[i].getNoteName(), createNoteImage(), handler);
            noteThread.noteAdd(note);
        }

        btn_key1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_hitbox1.setVisibility(View.VISIBLE);

                    Note note = new Note("1", createNoteImage(), handler);
                    noteThread.noteAdd(note);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_hitbox1.setVisibility(View.GONE);
                }

                return false;
            }
        });

        btn_key2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_hitbox2.setVisibility(View.VISIBLE);

                    Note note = new Note("2", createNoteImage(), handler);
                    noteThread.noteAdd(note);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_hitbox2.setVisibility(View.GONE);
                }

                return false;
            }
        });

        btn_key3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_hitbox3.setVisibility(View.VISIBLE);

                    Note note = new Note("3", createNoteImage(), handler);
                    noteThread.noteAdd(note);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_hitbox3.setVisibility(View.GONE);
                }

                return false;
            }
        });
        btn_key4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_hitbox4.setVisibility(View.VISIBLE);

                    Note note = new Note("4", createNoteImage(), handler);
                    noteThread.noteAdd(note);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_hitbox4.setVisibility(View.GONE);
                }

                return false;
            }
        });
    }

    public ImageView createNoteImage() {
        ImageView iv = new ImageView(PlayActivity.this);
        iv.setImageResource(R.drawable.note);
        iv.setLayoutParams(new ViewGroup.LayoutParams(150, 30));
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setVisibility(View.GONE);
        layout_play.addView(iv);

        return iv;
    }
}
