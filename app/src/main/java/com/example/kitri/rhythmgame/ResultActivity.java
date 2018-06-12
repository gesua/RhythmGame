package com.example.kitri.rhythmgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

public class ResultActivity extends Activity {
    private ResultNumManager resultNumManager = new ResultNumManager();

    private ImageView ivResultClear;
    private ImageView[] ivResultPerfect = new ImageView[3];
    private ImageView[] ivResultGood = new ImageView[3];
    private ImageView[] ivResultMiss = new ImageView[3];
    private ImageView[] ivResultCombo = new ImageView[3];
    private ImageView[] ivResultScore = new ImageView[4];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ivResultClear = findViewById(R.id.iv_result_clear);

        ivResultPerfect[0] = findViewById(R.id.iv_result_perfect_num_1);
        ivResultPerfect[1] = findViewById(R.id.iv_result_perfect_num_2);
        ivResultPerfect[2] = findViewById(R.id.iv_result_perfect_num_3);

        ivResultGood[0] = findViewById(R.id.iv_result_good_num_1);
        ivResultGood[1] = findViewById(R.id.iv_result_good_num_2);
        ivResultGood[2] = findViewById(R.id.iv_result_good_num_3);

        ivResultMiss[0] = findViewById(R.id.iv_result_miss_num_1);
        ivResultMiss[1] = findViewById(R.id.iv_result_miss_num_2);
        ivResultMiss[2] = findViewById(R.id.iv_result_miss_num_3);

        ivResultCombo[0] = findViewById(R.id.iv_result_combo_num_1);
        ivResultCombo[1] = findViewById(R.id.iv_result_combo_num_2);
        ivResultCombo[2] = findViewById(R.id.iv_result_combo_num_3);

        ivResultScore[0] = findViewById(R.id.iv_result_score_num_1);
        ivResultScore[1] = findViewById(R.id.iv_result_score_num_2);
        ivResultScore[2] = findViewById(R.id.iv_result_score_num_3);
        ivResultScore[3] = findViewById(R.id.iv_result_score_num_4);

        Intent get = getIntent();
        int perfect = get.getIntExtra("perfect", -1);
        int good = get.getIntExtra("good", -1);
        int miss = get.getIntExtra("miss", -1);
        int combo = get.getIntExtra("combo", -1);
        int score = get.getIntExtra("score", -1);
        boolean clear = get.getBooleanExtra("clear", false);

        // 클리어 및 풀콤보 표시
        if (clear == false) {
            ivResultClear.setImageResource(R.drawable.game_over);
        } else {
            if (miss == 0) {
                ivResultClear.setImageResource(R.drawable.full_combo);
            } else {
                ivResultClear.setImageResource(R.drawable.game_clear);
            }
        }

        // 점수 세팅
        resultNumManager.setResult(ivResultPerfect, perfect);
        resultNumManager.setResult(ivResultGood, good);
        resultNumManager.setResult(ivResultMiss, miss);
        resultNumManager.setResult(ivResultCombo, combo);
        resultNumManager.setResult(ivResultScore, score);
    }
}
