package ch.rts.mobile.le.jeu.dependencies.modules;

import android.support.annotation.NonNull;

import javax.inject.Named;

import ch.rts.mobile.le.jeu.data.source.GameRepository;
import ch.rts.mobile.le.jeu.data.source.remote.GameRemoteDataSource;
import ch.rts.mobile.le.jeu.dependencies.scope.AppScope;
import ch.rts.mobile.le.jeu.requests.GameService;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by npietri on 02.02.17.
 */
@Module
public class DataModule {

    private static final String RETROFIT_DATA = "Data";
    private static final String HTTP_CLIENT = "Client";

    private static final String URL_DATA = "https://srgssr-prod.apigee.net/";

    @Provides
    @AppScope
    GameRepository provideGameRepository(GameRemoteDataSource gameRemoteDataSource){
        return new GameRepository(gameRemoteDataSource);
    }

    @Provides
    GameRemoteDataSource provideGameRemoteDataSource(GameService gameService){
        return new GameRemoteDataSource(gameService);
    }

    @Provides
    GameService provideGameService(@Named(RETROFIT_DATA) Retrofit retrofit){
        return retrofit.create(GameService.class);
    }

    @NonNull
    @Provides
    @Named(RETROFIT_DATA)
    Retrofit provideRetrofitWithBu(@Named(HTTP_CLIENT) OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(URL_DATA)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }

    @NonNull
    @Provides
    @AppScope
    @Named(HTTP_CLIENT)
    OkHttpClient provideHttpClient(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        builder.addInterceptor(logging);

        return builder.build();
    }
}
