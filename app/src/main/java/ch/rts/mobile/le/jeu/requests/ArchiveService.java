package ch.rts.mobile.le.jeu.requests;

import ch.rts.mobile.le.jeu.model.Questions;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by npietri on 02.02.17.
 */

public interface ArchiveService {

    @GET("rts-archives-public-api/archives")
    Call<Questions> getRelatedMedia(@Query("apikey") String apiKey, @Query("query") String query);

}
