package ch.rts.mobile.le.jeu.data.source;

import android.support.annotation.NonNull;

import ch.rts.mobile.le.jeu.model.Questions;

/**
 * Created by npietri on 05.07.16.
 */

public interface GameDataSource {

    interface GetQuestionsCallback {

        void onQuestionsLoaded(Questions questions);

        void onDataNotAvailable();
    }

    interface GetRelatedMediasCallback {

        void onRelatedMediasLoaded(Questions questions);

        void onDataNotAvailable();
    }

    void getQuestions(@NonNull GetQuestionsCallback callback);

    void getRelatedMedias(@NonNull String query, @NonNull GetRelatedMediasCallback callback);
}
