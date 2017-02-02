package ch.rts.mobile.le.jeu.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.meetic.shuffle.Shuffle;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.rts.mobile.le.jeu.R;
import ch.rts.mobile.le.jeu.RTSGameApplication;
import ch.rts.mobile.le.jeu.adapters.QuestionsAdapter;
import ch.rts.mobile.le.jeu.model.Questions;

/**
 * Created by npietri on 02.02.17.
 */

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shuffle)
    Shuffle shuffle;

    public static void start(Activity activity) {
        start(activity, null);
    }

    public static void start(Activity activity, Bundle options) {
        Intent intent = new Intent(activity, GameActivity.class);
        ActivityCompat.startActivity(activity, intent, options);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);
        Questions questions = ((RTSGameApplication) getApplication()).getQuestions();
        QuestionsAdapter adapter = new QuestionsAdapter(questions.getQuestions());
        shuffle.setShuffleAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }
}
