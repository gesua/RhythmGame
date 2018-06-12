package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class NumManager {
    private ImageView[] iv_combo = new ImageView[3];
    private ImageView[] iv_score = new ImageView[4];
    private int[] numImage = new int[10];

    public NumManager(Activity context) {
        iv_combo[0] = context.findViewById(R.id.tv_combo_1);
        iv_combo[1] = context.findViewById(R.id.tv_combo_2);
        iv_combo[2] = context.findViewById(R.id.tv_combo_3);

        iv_score[0] = context.findViewById(R.id.iv_score_1);
        iv_score[1] = context.findViewById(R.id.iv_score_2);
        iv_score[2] = context.findViewById(R.id.iv_score_3);
        iv_score[3] = context.findViewById(R.id.iv_score_4);

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

        for (int j = i; j < iv_combo.length; j++) {
            iv_combo[j].setVisibility(View.GONE);
        }
    }

    public void setScore(int score) {
        if (score == 0) {
            iv_score[0].setImageResource(numImage[0]);

            for (int i = 1; i < iv_score.length; i++) {
                iv_score[i].setVisibility(View.GONE);
            }
            return;
        }

        int temp, cnt = 0;
        while (score > 0) {
            temp = score;
            temp %= 10;
            iv_score[cnt++].setImageResource(numImage[temp]);

            score /= 10;
        }

        int i;
        for (i = 0; i < cnt; i++) {
            iv_score[i].setVisibility(View.VISIBLE);
        }

        for (int j = i; j < iv_score.length; j++) {
            iv_score[j].setVisibility(View.GONE);
        }
    }
}
