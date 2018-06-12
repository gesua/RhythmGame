package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class NumManager {
    private int[] numImage = new int[10]; // 숫자
    private ImageView[] ivCombo = new ImageView[3]; // 콤보
    private ImageView[] ivScore = new ImageView[4]; // 스코어

    public NumManager(Activity context) {
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

        ivCombo[0] = context.findViewById(R.id.tv_combo_1);
        ivCombo[1] = context.findViewById(R.id.tv_combo_2);
        ivCombo[2] = context.findViewById(R.id.tv_combo_3);

        ivScore[0] = context.findViewById(R.id.iv_score_1);
        ivScore[1] = context.findViewById(R.id.iv_score_2);
        ivScore[2] = context.findViewById(R.id.iv_score_3);
        ivScore[3] = context.findViewById(R.id.iv_score_4);
    }

    public void setCombo(int combo) {
        int temp, cnt = 0;
        while (combo > 0) {
            temp = combo;
            temp %= 10;
            ivCombo[cnt++].setImageResource(numImage[temp]);

            combo /= 10;
        }

        int i;
        for (i = 0; i < cnt; i++) {
            ivCombo[i].setVisibility(View.VISIBLE);
        }

        for (int j = i; j < ivCombo.length; j++) {
            ivCombo[j].setVisibility(View.GONE);
        }
    }

    public void setScore(int score) {
        if (score == 0) {
            ivScore[0].setImageResource(numImage[0]);

            for (int i = 1; i < ivScore.length; i++) {
                ivScore[i].setVisibility(View.GONE);
            }
            return;
        }

        int temp, cnt = 0;
        while (score > 0) {
            temp = score;
            temp %= 10;
            ivScore[cnt++].setImageResource(numImage[temp]);

            score /= 10;
        }

        int i;
        for (i = 0; i < cnt; i++) {
            ivScore[i].setVisibility(View.VISIBLE);
        }

        for (int j = i; j < ivScore.length; j++) {
            ivScore[j].setVisibility(View.GONE);
        }
    }
}
