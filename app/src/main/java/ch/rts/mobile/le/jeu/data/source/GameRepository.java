package ch.rts.mobile.le.jeu.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.rts.mobile.le.jeu.data.source.remote.GameRemoteDataSource;
import ch.rts.mobile.le.jeu.model.Question;
import ch.rts.mobile.le.jeu.model.Questions;

/**
 * Created by npietri on 05.07.16.
 */
public class GameRepository implements GameDataSource {

    private final GameRemoteDataSource gameRemoteDataSource;

    private List<Question> questionList = new ArrayList<>();

    public GameRepository(@NonNull GameRemoteDataSource gameRemoteDataSource) {
        this.gameRemoteDataSource = gameRemoteDataSource;
    }

    @Nullable
    @Override
    public void getQuestions(@NonNull final GetQuestionsCallback callback) {
        gameRemoteDataSource.getQuestions(new GetQuestionsCallback() {
            @Override
            public void onQuestionsLoaded(Questions questions) {
                if (questions != null && !questions.getQuestions().isEmpty()) {
                    questionList.clear();
                    questionList.addAll(questions.getQuestions());
                    callback.onQuestionsLoaded(questions);
                }
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Nullable
    @Override
    public void getRelatedMedias(@NonNull String query, @NonNull GetRelatedMediasCallback callback) {

    }

    public List<Question> startNewGameSession(){
        Collections.shuffle(questionList);
        return questionList;
    }

    public List<Question> getCurrentGameSession(){
        return questionList;
    }

}
