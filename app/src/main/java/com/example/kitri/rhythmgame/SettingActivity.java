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
import android.widget.Toast;

public class SettingActivity extends Activity {
    private EditText setSpd;
    private Button ok;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        setSpd=findViewById(R.id.spd_edit);
        ok=findViewById(R.id.ok);

        final SetDB setDB =new SetDB(SettingActivity.this);


        String spds=setDB.select();

        int spdss=Integer.parseInt(spds);

        String set=(spdss/5)+"";

        setSpd.setText(set);



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Integer.parseInt(setSpd.getText().toString()) > 0 && Integer.parseInt(setSpd.getText().toString()) < 16) { //1~15까지만 DB업데이트
                        String ss=setSpd.getText().toString().trim();
                        int sss=Integer.parseInt(ss)*5;

                        setDB.update(sss+"");
                        Toast.makeText(SettingActivity.this, "속도 : "+(sss/5), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SettingActivity.this, "1~15이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }catch (NumberFormatException e){
                    Toast.makeText(SettingActivity.this, "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        setSpd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == android.view.KeyEvent.KEYCODE_ENTER) {

                    try {
                        if (Integer.parseInt(setSpd.getText().toString()) > 0 && Integer.parseInt(setSpd.getText().toString()) < 16) { //1~15까지만 DB업데이트
                            String ss=setSpd.getText().toString().trim();
                            int sss=Integer.parseInt(ss)*5;

                            setDB.update(sss+"");
                            Toast.makeText(SettingActivity.this, "속도 : "+(sss/5), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SettingActivity.this, "1~15이내로 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }catch (NumberFormatException e){
                        Toast.makeText(SettingActivity.this, "숫자만 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    finish();
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
