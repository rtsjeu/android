package ch.rts.mobile.le.jeu.requests;

import ch.rts.mobile.le.jeu.model.Questions;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by npietri on 02.02.17.
 */

public interface GameService {

    @GET("rts-lejeu")
    Call<Questions> getQuestions();

}
