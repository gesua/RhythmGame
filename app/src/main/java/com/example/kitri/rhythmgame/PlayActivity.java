package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends Activity {
    private ConstraintLayout layout_play;
    private Button btn_key1, btn_key2, btn_key3, btn_key4;
    private ImageView iv_hitbox1, iv_hitbox2, iv_hitbox3, iv_hitbox4, loca, iv_backbox1, iv_backbox2, iv_backbox3, iv_backbox4;
    private TextView tv_combo, tv_hit, tv_score;
    private Handler handler;
    private List<Integer> idList=new ArrayList<>();
    private int idValue = 0;
    private int score = 0;
    private int comboCnt = 0;
    private int cnt=100;
    private ProgressBar bar;

    private final int NOTE_WIDTH = 200, NOTE_HEIGHT = 30;

    NoteThread noteThread = new NoteThread();
    BeatThread beatThread = new BeatThread(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        layout_play = findViewById(R.id.layout_play);

        bar=findViewById(R.id.progress_1);

        btn_key1 = findViewById(R.id.btn_key1);
        btn_key2 = findViewById(R.id.btn_key2);
        btn_key3 = findViewById(R.id.btn_key3);
        btn_key4 = findViewById(R.id.btn_key4);

        iv_hitbox1 = findViewById(R.id.iv_hitbox1);
        iv_hitbox2 = findViewById(R.id.iv_hitbox2);
        iv_hitbox3 = findViewById(R.id.iv_hitbox3);
        iv_hitbox4 = findViewById(R.id.iv_hitbox4);
        loca = findViewById(R.id.loca);
        iv_backbox1 = findViewById(R.id.iv_backbox1);
        iv_backbox2 = findViewById(R.id.iv_backbox2);
        iv_backbox3 = findViewById(R.id.iv_backbox3);
        iv_backbox4 = findViewById(R.id.iv_backbox4);

        tv_combo = findViewById(R.id.tv_combo);
        tv_hit = findViewById(R.id.tv_hit);
        tv_score = findViewById(R.id.tv_score);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) { // 노트 밑으로 움직임
                    ((ImageView) msg.obj).setX(msg.arg1);
                    Log.d( "아이디",((ImageView) msg.obj).getId()+"");
                    ((ImageView) msg.obj).setY(msg.arg2);
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);
                    Float y = ((ImageView) msg.obj).getY();
                    for (int i = 0; i < 10; i++) {
                        if (y > loca.getTop() + -30 && y < loca.getBottom() + 30 && ((ImageView) msg.obj).getX() == (btn_key1.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2)) && iv_hitbox1.getVisibility() == View.VISIBLE) {
                            layout_play.removeView((ImageView) msg.obj);
                            iv_hitbox1.setVisibility(View.GONE);

                            score += 10;
                            tv_score.setText("SCORE : " + score);
                            tv_hit.setText("Good");
                            comboCnt++;
                            tv_combo.setText(comboCnt + " combo");
                        } else if (y > loca.getTop() - 30 && y < loca.getBottom() + 30 && ((ImageView) msg.obj).getX() == (btn_key2.getX() + (btn_key2.getWidth() / 2) - (NOTE_WIDTH / 2)) && iv_hitbox2.getVisibility() == View.VISIBLE) {
                            //                                layout_play.getViewById(i).setVisibility(View.GONE);
                            layout_play.removeView((ImageView) msg.obj);
                            iv_hitbox2.setVisibility(View.GONE);

                            score += 10;
                            tv_score.setText("SCORE : " + score);
                            tv_hit.setText("Good");
                            comboCnt++;
                            tv_combo.setText(comboCnt + " combo");
                        } else if (y > loca.getTop() - 30 && y < loca.getBottom() + 30 && ((ImageView) msg.obj).getX() == (btn_key3.getX() + (btn_key3.getWidth() / 2) - (NOTE_WIDTH / 2)) && iv_hitbox3.getVisibility() == View.VISIBLE) {
                            //                                layout_play.getViewById(i).setVisibility(View.GONE);
                            layout_play.removeView((ImageView) msg.obj);
                            iv_hitbox3.setVisibility(View.GONE);
                            score += 10;
                            tv_score.setText("SCORE : " + score);
                            tv_hit.setText("Good");
                            comboCnt++;
                            tv_combo.setText(comboCnt + " combo");
                        } else if (y > loca.getTop() - 30 && y < loca.getBottom() + 30 && ((ImageView) msg.obj).getX() == (btn_key4.getX() + (btn_key4.getWidth() / 2) - (NOTE_WIDTH / 2)) && iv_hitbox4.getVisibility() == View.VISIBLE) {
                            //                               layout_play.getViewById(i).setVisibility(View.GONE);
                            layout_play.removeView((ImageView) msg.obj);
                            iv_hitbox4.setVisibility(View.GONE);
                            score += 10;
                            tv_score.setText("SCORE : " + score);
                            tv_hit.setText("Good");
                            comboCnt++;
                            tv_combo.setText(comboCnt + " combo");
                        }
                    }
                } else if (msg.what == 2) { // 노트 삭제
                    if(idList.size()>0) {
                        if (((ImageView) msg.obj).getId() == idList.get(0)) {
                            tv_combo.setVisibility(View.INVISIBLE);
                            tv_hit.setText("");
                            idList.remove(0);

                        } else {
                            tv_hit.setText("Miss");
                            comboCnt = 0;
                            tv_combo.setText("");
                            if(cnt>0){
                                cnt--;
                            }
                        }
                    }else{
                        tv_hit.setText("Miss");
                        comboCnt = 0;
                        tv_combo.setText("");
                        if(cnt>0){
                            cnt--;
                        }
                    }
                    bar.setProgress(cnt);
                    if(bar.getProgress()==0){
                        noteThread.interrupt();
                        Toast.makeText(PlayActivity.this,"GAME OVER",Toast.LENGTH_SHORT).show();
                    }

                    layout_play.removeView((View) msg.obj);
                } else if (msg.what == 3) { // 노트 생성
                    ((ImageView) msg.obj).setLayoutParams(new ViewGroup.LayoutParams(NOTE_WIDTH, NOTE_HEIGHT));
                    ((ImageView) msg.obj).setScaleType(ImageView.ScaleType.FIT_XY);
                    ((ImageView) msg.obj).setVisibility(View.GONE);
                    ((ImageView) msg.obj).setImageResource(R.drawable.note);

                    layout_play.addView((ImageView) msg.obj);
                    ((ImageView) msg.obj).setId(idValue);
                    idValue++;
                }
            }
        };

        noteThread.start();
        beatThread.start();

        btn_key1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_hitbox1.setVisibility(View.VISIBLE);
                    iv_backbox1.setVisibility(View.VISIBLE);
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
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_hitbox4.setVisibility(View.GONE);
                    iv_backbox4.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });
    }

    public void createNote(Beat beat) {
        ImageView iv = new ImageView(PlayActivity.this);

        float x = 0;
        switch (beat.getNoteName()) {
            case "1":
                x = btn_key1.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2);
                break;
            case "2":
                x = btn_key2.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2);
                break;
            case "3":
                x = btn_key3.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2);
                break;
            case "4":
                x = btn_key4.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2);
                break;
        }

        Note note = new Note(x, iv, handler);
        noteThread.noteAdd(note);
    }


}

