package ch.rts.mobile.le.jeu.data.source.remote;

import android.support.annotation.NonNull;

import ch.rts.mobile.le.jeu.data.source.GameDataSource;
import ch.rts.mobile.le.jeu.model.Questions;
import ch.rts.mobile.le.jeu.requests.ArchiveService;
import ch.rts.mobile.le.jeu.requests.GameService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by npietri on 05.07.16.
 */
public class GameRemoteDataSource implements GameDataSource {

    private GameService gameService;

    private ArchiveService archiveService;

    public GameRemoteDataSource(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void getQuestions(@NonNull final GetQuestionsCallback callback) {
        gameService.getQuestions().enqueue(new Callback<Questions>() {
            @Override
            public void onResponse(Call<Questions> call, Response<Questions> response) {
                Questions questions = response.isSuccessful() ? response.body() : null;
                if (questions != null) {
                    callback.onQuestionsLoaded(questions);
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<Questions> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getRelatedMedias(@NonNull String query, @NonNull GetRelatedMediasCallback callback) {

    }
}
