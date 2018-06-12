package com.example.kitri.rhythmgame;

import android.view.View;
import android.widget.ImageView;

public class ResultNumManager {
    private int[] numImage = new int[10]; // 숫자

    public ResultNumManager() {
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

    public void setResult(ImageView[] iv, int value) {
        if (value == 0) {
            iv[0].setImageResource(numImage[0]);

            for (int i = 1; i < iv.length; i++) {
                iv[i].setVisibility(View.GONE);
            }
            return;
        }

        int temp, cnt = 0;
        while (value > 0) {
            temp = value;
            temp %= 10;
            iv[cnt++].setImageResource(numImage[temp]);

            value /= 10;
        }

        int i;
        for (i = 0; i < cnt; i++) {
            iv[i].setVisibility(View.VISIBLE);
        }

        for (int j = i; j < iv.length; j++) {
            iv[j].setVisibility(View.GONE);
        }
    }
}
