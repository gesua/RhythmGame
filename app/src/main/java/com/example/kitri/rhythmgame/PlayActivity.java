package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
    private static MediaPlayer mp;
    private List<NoteVO> noteVOS = new ArrayList<>();
    private int deldteNoteCnt = 0;
    private Intent get;
    private int lineP = 0;
    private int noteSpd = 0;
    private double startT = 0;
    private int startTime = 0;
    private List<Integer> deleteNoteList = new ArrayList<>();
    private boolean dupl = false;
    private int delay = 0;
    private int sync = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        SetDB setDB = new SetDB(PlayActivity.this);
        noteSpd = Integer.parseInt(setDB.select());

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

        get = getIntent();
        String lp = get.getStringExtra("111");
        String sy = setDB.selectS();
        sync = Integer.parseInt(sy.trim());

        delay = noteSpd * sync + 20;


        if (lp != null) {
            lineP = Integer.parseInt(lp) - delay;

        }

        String relp = get.getStringExtra("333"); //음악 시작타이밍 잡기위한 값
        if (relp != null) {
            lineP = Integer.parseInt(relp.trim());
        }

        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bar.getProgress() > 0) {
                    mp.pause();
                    note.setWait(true);
                    down.setWait(true);
                    custom();
                } else {
                    finish();
                }
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
                    if (!PlayActivity.this.isFinishing()) { //액티비티가 살아있는지 체크
                        alertDialog = new AlertDialog.Builder(PlayActivity.this);
                        mp.stop();

                        resultOn(PlayActivity.this, true);
                    }
                } else if (msg.what == 999) {

                    for (int j = 0; j < deldteNoteCnt; j++) {
                        if (layout_play.getViewById(j).getVisibility() != View.GONE) {
                            layout_play.getViewById(j).setVisibility(View.GONE); //혹시 이미지 남아있으면 제거용
                        }
                    }

                    for (int i = 0; i < noteVOS.size(); i++) {

                        for (int y = 0; y < deleteNoteList.size(); y++) {
                            if (deleteNoteList.get(y) == i) {
                                dupl = true;
                            }
                        }

                        if (dupl) {
                            dupl = false;
                            continue; //이미 판정 끝난노트일시 컨티뉴

                        }

                        if (layout_play.getViewById(i) != null) {
                            float y = layout_play.getViewById(i).getY();
                            y += noteSpd; //노트스피드 기본 25 최소5 최대 75 (설정창에서는 /5해서 1~15
                            layout_play.getViewById(i).setY(y);
                            if (y < loca.getBottom()+100) {
                                if (y > loca.getTop() - 150 && y < loca.getBottom()+100 && layout_play.getViewById(i).getX() == (btn_key1.getX() + (btn_key1.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit1) {

                                    hit1 = false;
                                    deleteNoteList.add(i);
                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    if (y > loca.getTop() -30 && y < loca.getBottom() +30) { //퍼펙트판정
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

                                } else if (y > loca.getTop() - 150 && y < loca.getBottom()+100 && layout_play.getViewById(i).getX() == (btn_key2.getX() + (btn_key2.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit2) {
                                    hit2 = false;
                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    deleteNoteList.add(i);

                                    if (y > loca.getTop() -30 && y < loca.getBottom() +30) {
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
                                } else if (y > loca.getTop() - 150 && y < loca.getBottom()+100 && layout_play.getViewById(i).getX() == (btn_key3.getX() + (btn_key3.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit3) {

                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    hit3 = false;
                                    deleteNoteList.add(i);
                                    if (y > loca.getTop() + -30 && y < loca.getBottom() + 30) {
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
                                } else if (y > loca.getTop() - 150 && y < loca.getBottom()+100 && layout_play.getViewById(i).getX() == (btn_key4.getX() + (btn_key4.getWidth() / 2) - (NOTE_WIDTH / 2)) && hit4) {
                                    hit4 = false;
                                    deleteNoteList.add(i);
                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    if (y > loca.getTop() - 30 && y < loca.getBottom() + 30) {
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
                                if (layout_play.getViewById(i).getVisibility() == View.VISIBLE) {
                                    deleteNoteList.add(i);
                                    layout_play.getViewById(i).setVisibility(View.GONE);
                                    if (bar.getProgress() > 0) {
                                        bar.setProgress(bar.getProgress() - 3
                                        ); //미스 1당 체력바 감소량
                                    } else {
                                        down.interrupt();
                                        note.interrupt();
                                        mp.stop();

                                        resultOn(PlayActivity.this, false);
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

        mp = MediaPlayer.create(this, R.raw.romancecut);

//        mp.setLooping(true);


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

        startT = ((double) lineP / (double) noteSpd) / 50 * 1000;
        startTime = (int) startT + note.getSetNoteTime(); //노트시작후 n초 후 결과값(노트가 화면 맨위에서 판정선까지 닿는데 걸리는 시간)+노트시작값(=첫노트 나오는시간)
        Log.d("숫자2", noteSpd + "dd");
        Log.d("숫자3", startTime + "dd");

        MusicThead musicThead = new MusicThead(handler, startTime);
        musicThead.start();

        note.setWaitTime(startTime + 1000); //마지막노트 판정선 지나는시간+n초 여유 후 결과창 다이얼로그

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
        if (bar.getProgress() > 0) {
            mp.pause();
            note.setWait(true);
            down.setWait(true);
            custom();
        } else {
            finish();
        }
    }


    // 결과창 띄움
    void resultOn(Activity context, boolean clear) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra("score", score2);
        intent.putExtra("perfect", perfectcnt);
        intent.putExtra("good", goodcnt);
        intent.putExtra("miss", misscnt);
        intent.putExtra("combo", maxcom);
        intent.putExtra("clear", clear);
        intent.putExtra("222", lineP + "");
        startActivity(intent);

        finish();
    }

    public void custom() {
        final Dialog dialog = new Dialog(PlayActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pause);

        ImageView exit = dialog.findViewById(R.id.iv_pause_exit);
        ImageView conti = dialog.findViewById(R.id.iv_pause_continue);
        ImageView rest = dialog.findViewById(R.id.iv_pause_restart);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note.interrupt();
                down.interrupt();
                mp.stop();
                finish();
                dialog.dismiss();
            }
        });

        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down.setWait(false);
                note.setWait(false);
                mp.start();
                dialog.cancel();
            }
        });

        rest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                down.interrupt();
                note.interrupt();
                Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                intent.putExtra("333", lineP + "");
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
