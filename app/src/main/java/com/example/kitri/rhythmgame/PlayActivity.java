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
    private int NOTE_WIDTH = 200, NOTE_HEIGHT = 50;

    private ConstraintLayout layout_play;
    private Button btn_key1, btn_key2, btn_key3, btn_key4;
    private ImageView iv_backbox1, iv_backbox2, iv_backbox3, iv_backbox4, loca, iv_setting, iv_hit;
    private Handler handler;
    private NumManager numManager;
    private int score2 = 0;
    private int com = 0;
    private int goodcnt = 0;
    private int misscnt = 0;
    private int maxcom = 0;
    private int perfectcnt = 0;
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
    private int deldteNoteCnt = 0;

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

        iv_setting = findViewById(R.id.iv_setting);

        loca = findViewById(R.id.loca);
        bar = findViewById(R.id.progress_1);
        numManager = new NumManager(this);

        iv_hit = findViewById(R.id.iv_hit);
        score2 = 0;

        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuOn();
            }
        });

        Beat beat = new Beat();
        noteVOS = beat.list();

        numManager.setScore(0);
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
                    ((ImageView) msg.obj).setY(bar.getBottom());
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);
                } else if (msg.what == 2) {
                    ((ImageView) msg.obj).setX(btn_key2.getX() + (btn_key2.getWidth() / 2) - (NOTE_WIDTH / 2));
                    ((ImageView) msg.obj).setY(bar.getBottom());
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);
                } else if (msg.what == 3) {
                    ((ImageView) msg.obj).setX(btn_key3.getX() + (btn_key3.getWidth() / 2) - (NOTE_WIDTH / 2));
                    ((ImageView) msg.obj).setY(bar.getBottom());
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);
                } else if (msg.what == 4) {
                    ((ImageView) msg.obj).setX(btn_key4.getX() + (btn_key4.getWidth() / 2) - (NOTE_WIDTH / 2));
                    ((ImageView) msg.obj).setY(bar.getBottom());
                    ((ImageView) msg.obj).setVisibility(View.VISIBLE);

                } else if (msg.what == 5) {
                    if (misscnt == 0) {

                        if (goodcnt == 0) {
                            mm = "Perfect";

                        } else {
                            mm = "Full Combo";
                        }

                    }
                    if (!PlayActivity.this.isFinishing()) { //액티비티가 살아있는지 체크
                        alertDialog = new AlertDialog.Builder(PlayActivity.this);
                        mp.stop();

                        alertDialog.setTitle("연주완료     " + mm).setMessage("SCORE : " + score2 + "\n" + "PERFECT : " + perfectcnt + "\n" + "GOOD : " + goodcnt + "\n" + "MISS : " + misscnt + "\n" + "MAXCOMBO : " + maxcom)
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

                    for (int j = 0; j < deldteNoteCnt; j++) {
                        if (layout_play.getViewById(j).getVisibility() != View.GONE) {
                            layout_play.getViewById(j).setVisibility(View.GONE); //혹시 이미지 남아있으면 제거용
                        }
                    }
                    for (int i = deldteNoteCnt; i < noteVOS.size(); i++) {


                        if (layout_play.getViewById(i) != null) {
                            float y = layout_play.getViewById(i).getY();
                            y += 25; //노트스피드 기본 25 최소5 최대 50
                            layout_play.getViewById(i).setY(y);
                            if (y < btn_key1.getBottom() + 200) {
                                if (y > loca.getTop() - 100 && y < loca.getBottom() + 200 && layout_play.getViewById(i).getX() == (btn_key1.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit1) {

                                    hit1 = false;
                                    deldteNoteCnt++;
                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    if (y > loca.getTop() - 10 && y < loca.getBottom() + 10) { //퍼펙트판정
                                        perfectcnt++;
                                        score2 += 15;
                                        numManager.setScore(score2);
                                        iv_hit.setImageResource(R.drawable.perfect);
                                        com++;
                                        numManager.setCombo(com);
                                    } else {
                                        goodcnt++;
                                        score2 += 10;
                                        numManager.setScore(score2);
                                        iv_hit.setImageResource(R.drawable.good);
                                        com++;
                                        numManager.setCombo(com);
                                    }
                                    layout_play.getViewById(i).setY(y + 500);

                                } else if (y > loca.getTop() - 100 && y < loca.getBottom() + 200 && layout_play.getViewById(i).getX() == (btn_key2.getX() + (btn_key2.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit2) {
                                    hit2 = false;
                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    deldteNoteCnt++;

                                    if (y > loca.getTop() - 10 && y < loca.getBottom() + 10) {
                                        perfectcnt++;
                                        score2 += 15;

                                        numManager.setScore(score2);
                                        iv_hit.setImageResource(R.drawable.perfect);
                                        com++;
                                        numManager.setCombo(com);
                                    } else {
                                        goodcnt++;
                                        score2 += 10;
                                        numManager.setScore(score2);
                                        iv_hit.setImageResource(R.drawable.good);
                                        com++;
                                        numManager.setCombo(com);
                                    }
                                    layout_play.getViewById(i).setY(y + 500);
                                } else if (y > loca.getTop() - 100 && y < loca.getBottom() + 200 && layout_play.getViewById(i).getX() == (btn_key3.getX() + (btn_key3.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit3) {

                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    hit3 = false;
                                    deldteNoteCnt++;
                                    if (y > loca.getTop() - 10 && y < loca.getBottom() + 10) {
                                        perfectcnt++;
                                        score2 += 15;
                                        numManager.setScore(score2);
                                        iv_hit.setImageResource(R.drawable.perfect);
                                        com++;
                                        numManager.setCombo(com);
                                    } else {
                                        goodcnt++;
                                        score2 += 10;
                                        numManager.setScore(score2);
                                        iv_hit.setImageResource(R.drawable.good);
                                        com++;
                                        numManager.setCombo(com);
                                    }
                                    layout_play.getViewById(i).setY(y + 500);
                                } else if (y > loca.getTop() - 100 && y < loca.getBottom() + 200 && layout_play.getViewById(i).getX() == (btn_key4.getX() + (btn_key4.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit4) {
                                    hit4 = false;
                                    deldteNoteCnt++;
                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    if (y > loca.getTop() - 10 && y < loca.getBottom() + 10) {
                                        perfectcnt++;
                                        score2 += 15;
                                        numManager.setScore(score2);
                                        iv_hit.setImageResource(R.drawable.perfect);
                                        com++;
                                        numManager.setCombo(com);
                                    } else {
                                        goodcnt++;
                                        score2 += 10;
                                        numManager.setScore(score2);
                                        iv_hit.setImageResource(R.drawable.good);
                                        com++;
                                        numManager.setCombo(com);

                                    }
                                    layout_play.getViewById(i).setY(y + 500); //판정되면 바로 밑으로 내려가버리게
                                } else {
                                    layout_play.getViewById(i).setY(y);
                                }

                            } else { //미스 판정
                                if (layout_play.getViewById(i).getVisibility() != View.GONE) {
                                    deldteNoteCnt++;
                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    if (bar.getProgress() > 0) {
                                        bar.setProgress(bar.getProgress() - 1); //미스 1당 체력바 감소량
                                    } else {
                                        down.interrupt();
                                        note.interrupt();
                                        mp.stop();
                                        Toast.makeText(PlayActivity.this, "Game Over", Toast.LENGTH_SHORT).show();
                                    }

                                    misscnt++;
                                    iv_hit.setImageResource(R.drawable.miss);
                                    com = 0;
                                    numManager.setCombo(com);
                                }

                            }


                        }
                    }
                    hit1 = false; //누르고있을때 판정되는거 막기
                    hit2 = false;
                    hit3 = false;
                    hit4 = false;

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
        down.interrupt();
        note.interrupt();
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        menuOn();
    }

    public void menuOn() {
        if (bar.getProgress() > 0) {
            mp.pause();
            note.setWait(true);
            down.setWait(true);
            final AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("설정")
                    .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            down.interrupt();
                            note.interrupt();
                            finish();

                        }
                    })
                    .setNegativeButton("계속하기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            down.setWait(false);
                            note.setWait(false);
                            mp.start();

                        }
                    })
                    .show();
            alertDialog.setCancelable(false);

        } else {
            finish();
        }
    }
}
