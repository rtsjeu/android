package ch.rts.mobile.le.jeu.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ch.rts.mobile.le.jeu.data.source.remote.GameRemoteDataSource;
import ch.rts.mobile.le.jeu.model.Questions;

/**
 * Created by npietri on 05.07.16.
 */
public class GameRepository implements GameDataSource {

    private final GameRemoteDataSource gameRemoteDataSource;

    public GameRepository(@NonNull GameRemoteDataSource gameRemoteDataSource) {
        this.gameRemoteDataSource = gameRemoteDataSource;
    }

    @Nullable
    @Override
    public void getQuestions(@NonNull final GetQuestionsCallback callback) {
        gameRemoteDataSource.getQuestions(new GetQuestionsCallback() {
            @Override
            public void onQuestionsLoaded(Questions questions) {
                callback.onQuestionsLoaded(questions);
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
}
