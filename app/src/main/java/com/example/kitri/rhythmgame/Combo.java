package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class Combo {
    private static Combo instance;
    private ImageView[] iv_combo = new ImageView[3];
    private int[] numImage = new int[10];

    private Combo() {
    }

    private Combo(Activity context) {
        iv_combo[0] = context.findViewById(R.id.tv_combo_1);
        iv_combo[1] = context.findViewById(R.id.tv_combo_2);
        iv_combo[2] = context.findViewById(R.id.tv_combo_3);

        numImage[0] = R.drawable.num_0;
        numImage[1] = R.drawable.num_1;
        numImage[2] = R.drawable.num_2;
        numImage[3] = R.drawable.num_3;
        numImage[4] = R.drawable.num_4;
        numImage[5] = R.drawable.num_5;
        numImage[6] = R.drawable.num_6;
        numImage[7] = R.drawable.num_7;
        numImage[8] = R.drawable.num_8;
        numImage[9] = R.drawable.num_9;
    }

    public static Combo getInstance(Activity context) {
        if (instance == null) {
            instance = new Combo(context);
        }
        return instance;
    }

    public void setCombo(int combo) {
        int temp, cnt = 0;
        while (combo > 0) {
            temp = combo;
            temp %= 10;
            iv_combo[cnt++].setImageResource(numImage[temp]);

            combo /= 10;
        }

        int i;
        for (i = 0; i < cnt; i++) {
            iv_combo[i].setVisibility(View.VISIBLE);
        }

        for(int j=i;j<iv_combo.length;j++){
            iv_combo[j].setVisibility(View.GONE);
        }
    }
}
