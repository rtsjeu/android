package ch.rts.mobile.le.jeu.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meetic.shuffle.Shuffle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.rts.mobile.le.jeu.R;
import ch.rts.mobile.le.jeu.RTSGameApplication;
import ch.rts.mobile.le.jeu.model.Answer;
import ch.rts.mobile.le.jeu.model.Question;
import ch.srg.srgmediaplayer.fragment.SRGLetterbox;

/**
 * Created by npietri on 02.02.17.
 */

public class QuestionsAdapter extends Shuffle.Adapter<QuestionsAdapter.QuestionHolder> {

    private List<Question> questions = new ArrayList<>(10);
    WeakReference<SRGLetterbox> letterboxWeakReference;

    private Random rand = new Random();

    private ArrayList<Answer> answers = new ArrayList<>();

    public QuestionsAdapter(List<Question> questions) {
        this.questions = questions;
    }

    public void addAll(@NonNull List<Question> questions) {
        this.questions.clear();
        this.questions.addAll(questions);
        notifyDataSetChanged();
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        FragmentManager fragmentManager = ((FragmentActivity) viewGroup.getContext())
                .getSupportFragmentManager();

        if (letterboxWeakReference != null) {
            letterboxWeakReference.get().stopMedia();
            fragmentManager
                    .beginTransaction()
                    .remove(letterboxWeakReference.get())
                    .commitNow();
        }

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_question, viewGroup, false);

        letterboxWeakReference = new WeakReference<>((SRGLetterbox) fragmentManager
                .findFragmentByTag(viewGroup
                        .getContext()
                        .getString(R.string.srg_letterbox_tag)));

        RTSGameApplication.getAppComponent(viewGroup.getContext())
                .inject(letterboxWeakReference.get());

        letterboxWeakReference.get().setSegmentsEnabled(false);
        letterboxWeakReference.get().setControlsEnabled(false);
        letterboxWeakReference.get().setNotificationEnabled(false);

        return new QuestionHolder(view);
    }

    public void endOfGame() {
        if (letterboxWeakReference.get() != null) {
            letterboxWeakReference.get().stopMedia();
        }
    }

    @Override
    public void onBindViewHolder(QuestionHolder holder, int position) {
        Question question = questions.get(position);
        ArrayList<Answer> correctAnswers = new ArrayList<>(5);
        ArrayList<Answer> incorrectAnswers = new ArrayList<>(5);
        for (Answer answer : question.getAnswers()) {
            if (answer.getIsCorrect()){
                correctAnswers.add(answer);
            } else {
                incorrectAnswers.add(answer);
            }
        }

        Collections.shuffle(correctAnswers);
        Collections.shuffle(incorrectAnswers);

        Answer answer = (rand.nextBoolean() ? correctAnswers : incorrectAnswers).get(0);

        answers.add(answer);

        holder.answerText.setText(answer.getText());
        if(!answer.getPlayUrn().isEmpty()) {
            letterboxWeakReference.get().play(answer.getPlayUrn(), null);
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public Answer getAnswer(int position) {
        return answers.get(position);
    }

    class QuestionHolder extends Shuffle.ViewHolder {

        @BindView(R.id.answer)
        TextView answerText;

        QuestionHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
