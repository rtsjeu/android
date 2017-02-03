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
import java.util.List;

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
        letterboxWeakReference.get().stopMedia();
    }

    @Override
    public void onBindViewHolder(QuestionHolder holder, int position) {
        Question question = questions.get(position);
        Answer answer = question.getAnswers().get(0);
        holder.answer.setText(answer.getText());
        letterboxWeakReference.get().play(answer.getPlayurn(), null);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionHolder extends Shuffle.ViewHolder {

        @BindView(R.id.answer)
        TextView answer;

        QuestionHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
