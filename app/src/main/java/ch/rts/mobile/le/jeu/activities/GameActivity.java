package ch.rts.mobile.le.jeu.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.meetic.dragueur.Direction;
import com.meetic.dragueur.DraggableView;
import com.meetic.shuffle.Shuffle;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.rts.mobile.le.jeu.R;
import ch.rts.mobile.le.jeu.RTSGameApplication;
import ch.rts.mobile.le.jeu.adapters.QuestionsAdapter;
import ch.rts.mobile.le.jeu.data.source.GameRepository;
import ch.rts.mobile.le.jeu.model.Answer;
import ch.rts.mobile.le.jeu.model.Question;

/**
 * Created by npietri on 02.02.17.
 */

public class GameActivity extends AppCompatActivity implements Shuffle.Listener {

    private static final String TAG = "GameActivity";
    private static final String KEY_QUESTIONS = "Questions";
    private static final String KEY_POSITION = "Position";
    private static final int QUESTIONS_BY_GAME = 10;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shuffle)
    Shuffle shuffle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.result)
    TextView result;
    @BindView(R.id.result_layout)
    FrameLayout resultLayout;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.answer_result_layout)
    FrameLayout answerResult;
    @BindView(R.id.answer_result)
    ImageView answerImage;

    @Inject
    GameRepository gameRepository;

    private QuestionsAdapter adapter;

    private ArrayList<Question> questions = new ArrayList<>();
    private int position = 0;
    private int currentScore = 0;

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
    protected void onPause() {
        super.onPause();
        adapter.endOfGame();
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
        result.setText("" + currentScore);
        revealResult();
    }

    private void revealAnswer(boolean isCorrect) {
        int colorId = isCorrect ? R.color.green : R.color.red;
        int icon = isCorrect ? R.drawable.bonnereponse : R.drawable.mauvaisereponse;
        answerImage.setImageResource(icon);
        answerResult.setBackgroundColor(ContextCompat.getColor(this, colorId));
        revealAnswerAnim();
    }

    public void revealAnswerAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = answerResult.getMeasuredWidth() / 2;
            int cy = answerResult.getMeasuredHeight() / 2;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(answerResult.getWidth(), answerResult.getHeight());

            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(answerResult, cx, cy, 0, finalRadius);

            // make the view visible and start the animation
            anim.setDuration(550);
            answerResult.setVisibility(View.VISIBLE);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    revertAnswerAnim();
                }
            });
            anim.start();
        } else {
            answerResult.setVisibility(View.VISIBLE);
        }
    }

    public void revertAnswerAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = answerResult.getMeasuredWidth() / 2;
            int cy = answerResult.getMeasuredHeight() / 2;

            // get the initial radius for the clipping circle
            int initialRadius = Math.max(answerResult.getWidth(), answerResult.getHeight());

            // create the animation (the final radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(answerResult, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    next();
                    answerResult.setVisibility(View.INVISIBLE);
                }
            });

            // start the animation
            anim.start();
        } else {
            answerResult.setVisibility(View.INVISIBLE);
        }
    }

    private void revealResult() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = resultLayout.getMeasuredWidth() / 2;
            int cy = resultLayout.getMeasuredHeight() / 2;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(resultLayout.getWidth(), resultLayout.getHeight());

            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(resultLayout, cx, cy, 0, finalRadius);

            // make the view visible and start the animation
            anim.setDuration(550);
            resultLayout.setVisibility(View.VISIBLE);
            anim.start();
        } else {
            resultLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewChanged(int position) {
        if (position >= questions.size()) {
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
        int position = shuffle.getCurrentAdapterPosition();
        if (position < questions.size()) {
            Answer answer = adapter.getAnswer(position - 1);
            if ((answer.getIsCorrect() && direction == Direction.RIGHT)
                    || (!answer.getIsCorrect() && direction == Direction.LEFT)) {
                currentScore++;
                revealAnswer(true);
                Log.d(TAG, "onViewExited: Correct");
            } else {
                currentScore -= 2;
                revealAnswer(false);
                Log.e(TAG, "onViewExited: Incorrect");
            }
            score.setText("Score: " + currentScore);
        }
    }

    private void next() {
        int position = shuffle.getCurrentAdapterPosition();
        if (position < questions.size()) {
            displayCurrentQuestion(position);
        }
    }

    @Override
    public void onViewScrolled(DraggableView draggableView, float percentX, float percentY) {

    }

    @OnClick(R.id.result)
    public void backResult(View view){
        if (view.getVisibility() == View.VISIBLE){
            onSupportNavigateUp();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(KEY_POSITION, shuffle.getCurrentAdapterPosition());
        super.onSaveInstanceState(outState);
    }
}
