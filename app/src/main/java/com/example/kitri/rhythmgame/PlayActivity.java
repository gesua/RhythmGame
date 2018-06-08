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
import android.widget.TextView;

public class PlayActivity extends Activity {
    private ConstraintLayout layout_play;
    private Button btn_key1, btn_key2, btn_key3, btn_key4;
    private ImageView iv_hitbox1, iv_hitbox2, iv_hitbox3, iv_hitbox4, loca,iv_backbox1,iv_backbox2,iv_backbox3,iv_backbox4;
    private TextView tv_combo, tv_hit, tv_score;
    private Handler handler;
    private int idValue = 0;
    private int score = 0;
    private int comboCnt = 0;

    NoteThread noteThread = new NoteThread();

    public ImageView testNote() {
        ImageView iv = new ImageView(PlayActivity.this);
        iv.setImageResource(R.drawable.note);
        iv.setLayoutParams(new ViewGroup.LayoutParams(200, 30));
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        iv.setVisibility(View.GONE);
        layout_play.addView(iv);
        iv.setId(idValue);
        idValue++;

        return iv;
    }

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
        loca = findViewById(R.id.loca);
        iv_backbox1=findViewById(R.id.iv_backbox1);
        iv_backbox2=findViewById(R.id.iv_backbox2);
        iv_backbox3=findViewById(R.id.iv_backbox3);
        iv_backbox4=findViewById(R.id.iv_backbox4);

        tv_combo = findViewById(R.id.tv_combo);
        tv_hit = findViewById(R.id.tv_hit);
        tv_score = findViewById(R.id.tv_score);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) { // 노트 밑으로 움직임
                    ((ImageView) msg.obj).setX(msg.arg1);
                    ((ImageView) msg.obj).setY(msg.arg2);
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);
                    Float y = ((ImageView) msg.obj).getY();
                    for (int i = 0; i < idValue; i++) {
                        if (y > loca.getTop() + -30 && y < loca.getBottom() + 30 && ((ImageView) msg.obj).getX() == 70 && iv_hitbox1.getVisibility() == View.VISIBLE) {
                                                                layout_play.removeView((ImageView) msg.obj);
                            iv_hitbox1.setVisibility(View.GONE);

                            score += 10;
                            tv_score.setText("SCORE : " + score);
                            tv_hit.setText("Good");
                            comboCnt++;
                            tv_combo.setText(comboCnt + " combo");

                        } else if (y > loca.getTop() - 30 && y < loca.getBottom() + 30 && ((ImageView) msg.obj).getX() == 320 && iv_hitbox2.getVisibility() == View.VISIBLE) {

                            //                                layout_play.getViewById(i).setVisibility(View.GONE);
                            layout_play.removeView((ImageView) msg.obj);
                            iv_hitbox2.setVisibility(View.GONE);


                            score += 10;
                            tv_score.setText("SCORE : " + score);
                            tv_hit.setText("Good");
                            comboCnt++;
                            tv_combo.setText(comboCnt + " combo");

                        } else if (y > loca.getTop() - 30 && y < loca.getBottom() + 30 && ((ImageView) msg.obj).getX() == 560 && iv_hitbox3.getVisibility() == View.VISIBLE) {

                            //                                layout_play.getViewById(i).setVisibility(View.GONE);
                            layout_play.removeView((ImageView) msg.obj);
                            iv_hitbox3.setVisibility(View.GONE);
                            score += 10;
                            tv_score.setText("SCORE : " + score);
                            tv_hit.setText("Good");
                            comboCnt++;
                            tv_combo.setText(comboCnt + " combo");

                        } else if (y > loca.getTop() - 30 && y < loca.getBottom() + 30 && ((ImageView) msg.obj).getX() == 800 && iv_hitbox4.getVisibility() == View.VISIBLE) {

                            //                               layout_play.getViewById(i).setVisibility(View.GONE);
                            layout_play.removeView((ImageView) msg.obj);
                            iv_hitbox4.setVisibility(View.GONE);
                            score += 10;
                            tv_score.setText("SCORE : " + score);
                            tv_hit.setText("Good");
                            comboCnt++;
                            tv_combo.setText(comboCnt + " combo");


                        }else if(y>1950){
                            tv_hit.setText("Miss");
                            comboCnt=0;
                            tv_combo.setText("");
                        }
                    }
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
            Note note = new Note(beats[i].getNoteName(), testNote(), handler);
            noteThread.noteAdd(note);
        }

        btn_key1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_hitbox1.setVisibility(View.VISIBLE);
                    iv_backbox1.setVisibility(View.VISIBLE);
                    Note note = new Note("1", testNote(), handler);
                    noteThread.noteAdd(note);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_backbox1.setVisibility(View.INVISIBLE);
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
                    iv_backbox2.setVisibility(View.VISIBLE);
                    Note note = new Note("2", testNote(), handler);
                    noteThread.noteAdd(note);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_backbox2.setVisibility(View.INVISIBLE);
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
                    iv_backbox3.setVisibility(View.VISIBLE);
                    Note note = new Note("3", testNote(), handler);
                    noteThread.noteAdd(note);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_backbox3.setVisibility(View.INVISIBLE);
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
                    iv_backbox4.setVisibility(View.VISIBLE);
                    Note note = new Note("4", testNote(), handler);
                    noteThread.noteAdd(note);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_hitbox4.setVisibility(View.GONE);
                    iv_backbox4.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });
    }
}
