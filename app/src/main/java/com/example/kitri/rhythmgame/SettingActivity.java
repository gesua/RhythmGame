package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SettingActivity extends Activity {
    private EditText setSpd, setSync;
    private Button ok, no;
    private ImageView spdbtn1, spdbtn2, syncbtn1, syncbtn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration config = getResources().getConfiguration(); //현재 가로인지 세로인지 받아오기

        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE){ //가로일시
            setContentView(R.layout.custom_dialog2);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }else {
            setContentView(R.layout.custom_dialog);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        setSpd = findViewById(R.id.spd_edit);
        ok = findViewById(R.id.ok);
        setSync = findViewById(R.id.sync_edit2);
        no = findViewById(R.id.ok2);
        spdbtn1 = findViewById(R.id.backimg);
        spdbtn2 = findViewById(R.id.forimg);
        syncbtn1 = findViewById(R.id.backimg2);
        syncbtn2 = findViewById(R.id.forimg2);
        final SetDB setDB = new SetDB(SettingActivity.this);


        String spds = setDB.select();
        int spdss = Integer.parseInt(spds);
        String set = (spdss / 5) + "";
        setSpd.setText(set);

        String sy = setDB.selectS();
        int syn = Integer.parseInt(sy);
        String sets = syn + "";
        setSync.setText(sets);

        spdbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    int a = Integer.parseInt(setSpd.getText().toString()) - 1;
                    if(a>0) {
                        setSpd.setText(a + "");
                    }
                } catch (Exception e) {
                    Toast.makeText(SettingActivity.this, "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spdbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int a = Integer.parseInt(setSpd.getText().toString()) + 1;
                    if(a<16) {
                        setSpd.setText(a + "");
                    }
                } catch (Exception e) {
                    Toast.makeText(SettingActivity.this, "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        syncbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int a = Integer.parseInt(setSync.getText().toString()) - 1;
                    if(a>0) {
                        setSync.setText(a + "");
                    }

                } catch (Exception e) {
                    Toast.makeText(SettingActivity.this, "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


        syncbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int a = Integer.parseInt(setSync.getText().toString()) + 1;
                    if(a<13) {
                        setSync.setText(a + "");
                    }
                } catch (Exception e) {
                    Toast.makeText(SettingActivity.this, "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Integer.parseInt(setSpd.getText().toString()) > 0 && Integer.parseInt(setSpd.getText().toString()) < 16 && Integer.parseInt(setSync.getText().toString()) > 0 && Integer.parseInt(setSync.getText().toString()) < 13) {
                        String ss = setSpd.getText().toString().trim();
                        String sy =setSync.getText().toString().trim();
                        int sss = Integer.parseInt(ss) * 5;


                        setDB.update(sss + "");
                        setDB.updateS(sy);
                        Toast.makeText(SettingActivity.this, "속도 : " + (sss / 5) +" / " + "싱크 : "+setSync.getText().toString(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SettingActivity.this, "속도(1~15)/싱크(1~12) 이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(SettingActivity.this, "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSpd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(setSpd.getWindowToken(), 0);    //hide keyboard

                }
                return false;
            }
        });



        setSync.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {


                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(setSync.getWindowToken(), 0);    //hide keyboard
                }
                return false;
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
