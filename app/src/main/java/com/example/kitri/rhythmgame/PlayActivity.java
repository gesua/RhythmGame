package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
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
    private int NOTE_WIDTH = 200, NOTE_HEIGHT = 30;

    private ConstraintLayout layout_play;
    private Button btn_key1, btn_key2, btn_key3, btn_key4;
    private ImageView iv_backbox1, iv_backbox2, iv_backbox3, iv_backbox4, loca;
    private Handler handler;
    private TextView tv, tv_score, combo;
    private int score2 = 0;
    private int com = 0;
    private int goodcnt = 0;
    private int misscnt = 0;
    private int maxcom = 0;
    private ProgressBar bar;
    private Down down;
    private Note note;
    private AlertDialog.Builder alertDialog;
    private AlertDialog dialog;
    private boolean hit1 = false;
    private boolean hit2 = false;
    private boolean hit3 = false;
    private boolean hit4 = false;
    private String mm = "";
    private static MediaPlayer mp;
    private List<NoteVO> noteVOS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        layout_play = findViewById(R.id.layout_play);

        btn_key1 = findViewById(R.id.btn_key1);
        btn_key2 = findViewById(R.id.btn_key2);
        btn_key3 = findViewById(R.id.btn_key3);
        btn_key4 = findViewById(R.id.btn_key4);

        iv_backbox1 = findViewById(R.id.iv_backbox1);
        iv_backbox2 = findViewById(R.id.iv_backbox2);
        iv_backbox3 = findViewById(R.id.iv_backbox3);
        iv_backbox4 = findViewById(R.id.iv_backbox4);

        loca = findViewById(R.id.loca);
        combo = findViewById(R.id.tv_combo);
        bar = findViewById(R.id.progress_1);

        tv = findViewById(R.id.tv_hit);
        tv_score = findViewById(R.id.tv_score);
        score2 = 0;

        Beat beat = new Beat();
        noteVOS = beat.list();

        tv_score.setText("SCORE : 0");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 777) {

                    try {
                        mp.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mp.start();
                }

                // 노트 위치 세팅
                if (msg.what == 1) {
                    ((ImageView) msg.obj).setX(btn_key1.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2));
                    ((ImageView) msg.obj).setY(msg.arg2);
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);
                } else if (msg.what == 2) {
                    ((ImageView) msg.obj).setX(btn_key2.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2));
                    ((ImageView) msg.obj).setY(msg.arg2);
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);
                } else if (msg.what == 3) {
                    ((ImageView) msg.obj).setX(btn_key3.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2));
                    ((ImageView) msg.obj).setY(msg.arg2);
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);
                } else if (msg.what == 4) {
                    ((ImageView) msg.obj).setX(btn_key4.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2));
                    ((ImageView) msg.obj).setY(msg.arg2);
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);

                } else if (msg.what == 5) {
                    if (misscnt == 0) {
                        mm = "Full Combo";
                    }
                    if (!PlayActivity.this.isFinishing()) { //액티비티가 살아있는지 체크
                        alertDialog = new AlertDialog.Builder(PlayActivity.this);
                        mp.stop();

                        alertDialog.setTitle("연주완료     " + mm).setMessage("SCORE : " + score2 + "\n" + "GOOD : " + goodcnt + "\n" + "MISS : " + misscnt + "\n" + "MAXCOMBO : " + maxcom)
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        finish();

                                    }
                                })
                                .setNegativeButton("다시하기", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                                        startActivity(intent);

                                        dialog.cancel();
                                        finish();

                                    }
                                });
                        dialog = alertDialog.show();
                        dialog.setCancelable(false);
                    }
                } else if (msg.what == 999) {

                    for (int i = 0; i < noteVOS.size(); i++) {


                        if (layout_play.getViewById(i) != null) {
                            float y = layout_play.getViewById(i).getY();
                            if (y < btn_key1.getTop() + 150) {
                                y += 15; //노트스피드
                                layout_play.getViewById(i).setY(y);
                                if (y > loca.getTop() - 50 && y < loca.getBottom() + 300 && layout_play.getViewById(i).getX() == (btn_key1.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit1)
                                {

                                    hit1 = false;

                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    goodcnt++;
                                    score2 += 10;
                                    tv_score.setText("SCORE : " + score2);
                                    tv.setText("Good");
                                    com++;
                                    combo.setText(com + " combo");

                                } else if (y > loca.getTop() - 50 && y < loca.getBottom() + 300 && layout_play.getViewById(i).getX() == (btn_key2.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit2) {
                                    hit2 = false;
                                    layout_play.getViewById(i).setVisibility(View.GONE);

                                    goodcnt++;
                                    score2 += 10;
                                    tv_score.setText("SCORE : " + score2);
                                    tv.setText("Good");
                                    com++;
                                    combo.setText(com + " combo");

                                } else if (y > loca.getTop() - 50 && y < loca.getBottom() + 300 && layout_play.getViewById(i).getX() == (btn_key3.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit3) {

                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    hit3 = false;

                                    goodcnt++;
                                    score2 += 10;
                                    tv_score.setText("SCORE : " + score2);
                                    tv.setText("Good");
                                    com++;
                                    combo.setText(com + " combo");

                                } else if (y > loca.getTop() - 50 && y < loca.getBottom() + 300 && layout_play.getViewById(i).getX() == (btn_key4.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit4) {

                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    hit4 = false;
                                    goodcnt++;
                                    score2 += 10;
                                    tv_score.setText("SCORE : " + score2);
                                    tv.setText("Good");
                                    com++;
                                    combo.setText(com + " combo");

                                } else {
                                    layout_play.getViewById(i).setY(y);
                                }

                            } else { //미스 판정선 넘어가면
                                if (layout_play.getViewById(i).getVisibility() != View.GONE) {
                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    if (bar.getProgress() > 0) {
                                        bar.setProgress(bar.getProgress() - 3);
                                    } else {
                                        down.interrupt();
                                        note.interrupt();
                                        mp.stop();
                                        Toast.makeText(PlayActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                                    }

                                    misscnt++;
                                    tv.setText("Miss");
                                    com = 0;
                                    combo.setText("");
                                }

                            }


                        }
                    }
                } //msg.999 end
                if (maxcom < com) {
                    maxcom = com;
                }

            }
        };

        mp = MediaPlayer.create(this, R.raw.romance);

        mp.setLooping(true);

        MusicThead musicThead = new MusicThead(handler);
        musicThead.start();


        for (int i = 0; i < noteVOS.size(); i++) {
            ImageView iv = new ImageView(PlayActivity.this);
            iv.setImageResource(R.drawable.note);
            iv.setLayoutParams(new ViewGroup.LayoutParams(NOTE_WIDTH, NOTE_HEIGHT));
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setVisibility(View.GONE);
            iv.setId(i);
            layout_play.addView(iv);
        }


        note = new Note(layout_play, handler, noteVOS);
        note.start();

        down = new Down(handler);
        down.start();

        btn_key1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_backbox1.setVisibility(View.VISIBLE);
                    hit1 = true;


                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_backbox1.setVisibility(View.GONE);
                    hit1 = false;

                }

                return false;
            }
        });

        btn_key2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_backbox2.setVisibility(View.VISIBLE);
                    hit2 = true;


                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_backbox2.setVisibility(View.GONE);
                    hit2 = false;
                }

                return false;
            }
        });

        btn_key3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_backbox3.setVisibility(View.VISIBLE);
                    hit3 = true;


                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_backbox3.setVisibility(View.GONE);
                    hit3 = false;
                }

                return false;
            }
        });
        btn_key4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    iv_backbox4.setVisibility(View.VISIBLE);

                    hit4 = true;

                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    iv_backbox4.setVisibility(View.GONE);
                    hit4 = false;
                }

                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog.cancel();
            dialog = null;
        }
        mp.stop();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        mp.pause();


        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("설정")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        down.interrupt();
                        note.interrupt();
                        finish();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("계속하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.cancel();
                    }
                })
                .show();

    }
}
