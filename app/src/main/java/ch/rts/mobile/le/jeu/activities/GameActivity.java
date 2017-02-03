package ch.rts.mobile.le.jeu.activities;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;

import com.meetic.dragueur.Direction;
import com.meetic.dragueur.DraggableView;
import com.meetic.shuffle.Shuffle;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.rts.mobile.le.jeu.R;
import ch.rts.mobile.le.jeu.RTSGameApplication;
import ch.rts.mobile.le.jeu.adapters.QuestionsAdapter;
import ch.rts.mobile.le.jeu.data.source.GameRepository;
import ch.rts.mobile.le.jeu.model.Question;

/**
 * Created by npietri on 02.02.17.
 */

public class GameActivity extends AppCompatActivity implements Shuffle.Listener {

    private static final String TAG = "GameActivity";
    private static final String KEY_QUESTIONS = "Questions";
    private static final String KEY_POSITION = "Position";
    private static final int QUESTIONS_BY_GAME = 8;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shuffle)
    Shuffle shuffle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.result)
    TextView result;

    @Inject
    GameRepository gameRepository;

    private QuestionsAdapter adapter;

    private ArrayList<Question> questions = new ArrayList<>();
    private int position = 0;

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
        RTSGameApplication.getAppComponent(this).inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            questions.addAll(gameRepository.startNewGameSession().subList(0, QUESTIONS_BY_GAME - 1));
        } else {
            position = savedInstanceState.getInt(KEY_POSITION);
            questions.addAll(gameRepository.getCurrentGameSession().subList(position, QUESTIONS_BY_GAME - 1));
        }
        displayCurrentQuestion(0);
        adapter = new QuestionsAdapter(questions);
        shuffle.setShuffleAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        shuffle.addListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        shuffle.removeListener(this);
    }

    private void displayCurrentQuestion(int position) {
        title.setText(questions.get(position).getText());
        getSupportActionBar().setTitle("Question " + (position + 1));
    }

    private void displayEndOfQuiz() {
        adapter.endOfGame();
        title.setVisibility(View.GONE);
        shuffle.setVisibility(View.GONE);
        getSupportActionBar().setTitle("Résultat");
        result.setText("Bravo !");
        revealResult();
    }

    private void revealResult(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = result.getWidth() / 2;
            int cy = 0;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(result.getWidth(), result.getHeight());

            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(result, cx, cy, 0, finalRadius);

            // make the view visible and start the animation
            anim.setDuration(550);
            result.setVisibility(View.VISIBLE);
            anim.start();
        } else {
            result.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewChanged(int position) {
        if (position < questions.size()) {
            displayCurrentQuestion(position);
        } else {
            displayEndOfQuiz();
        }
    }

    @Override
    public void onScrollStarted() {

    }

    @Override
    public void onScrollFinished() {

    }

    @Override
    public void onViewExited(DraggableView draggableView, Direction direction) {

    }

    @Override
    public void onViewScrolled(DraggableView draggableView, float percentX, float percentY) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_POSITION, shuffle.getCurrentAdapterPosition());
        super.onSaveInstanceState(outState);
    }
}
