package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.content.Context;
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
    private Button ok, syncbtn;
    private ImageView spdbtn1, spdbtn2, syncbtn1, syncbtn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        setSpd = findViewById(R.id.spd_edit);
        ok = findViewById(R.id.ok);
        setSync = findViewById(R.id.sync_edit2);
        syncbtn = findViewById(R.id.ok2);
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
                    if (Integer.parseInt(setSpd.getText().toString()) > 0 && Integer.parseInt(setSpd.getText().toString()) < 16) { //1~15까지만 DB업데이트
                        String ss = setSpd.getText().toString().trim();
                        int sss = Integer.parseInt(ss) * 5;

                        setDB.update(sss + "");
                        Toast.makeText(SettingActivity.this, "속도 : " + (sss / 5), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SettingActivity.this, "1~15이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(SettingActivity.this, "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });

        setSpd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == android.view.KeyEvent.KEYCODE_ENTER) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(setSpd.getWindowToken(), 0);    //hide keyboard

                }
                return false;
            }
        });

        syncbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Integer.parseInt(setSync.getText().toString()) > 0 && Integer.parseInt(setSync.getText().toString()) < 13) { //1~12까지만 싱크조절
                        String ss = setSync.getText().toString().trim();
                        setDB.updateS(ss);
                        Toast.makeText(SettingActivity.this, "싱크 : " + ss, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(SettingActivity.this, "1~12이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(SettingActivity.this, "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setSync.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == android.view.KeyEvent.KEYCODE_ENTER) {


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
