package ch.rts.mobile.le.jeu;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.util.Locale;

import javax.inject.Inject;

import ch.rts.mobile.le.jeu.data.source.GameDataSource;
import ch.rts.mobile.le.jeu.data.source.GameRepository;
import ch.rts.mobile.le.jeu.dependencies.AppComponent;
import ch.rts.mobile.le.jeu.dependencies.DaggerAppComponent;
import ch.rts.mobile.le.jeu.model.Questions;
import ch.srg.dataProvider.integrationlayer.SRGConfig;
import ch.srg.dataProvider.integrationlayer.dependencies.components.DaggerIlDataProviderComponent;
import ch.srg.dataProvider.integrationlayer.dependencies.components.IlDataProviderComponent;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.IlAppModule;
import ch.srg.dataProvider.integrationlayer.dependencies.modules.SRGConfigModule;
import ch.srg.srgmediaplayer.fragment.utils.SrgApplicationUtils;

/**
 * Created by npietri on 02.02.17.
 */

public class RTSGameApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = "RTSGameApplication";

    private AppComponent appComponent;
    private IlDataProviderComponent ilDataProviderComponent;
    private SrgApplicationUtils srgApplicationUtils;
    private Questions questions;

    @Inject
    GameRepository gameRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        buildObjectGraphAndInject();
    }

    private void buildObjectGraphAndInject() {
        // Dummy SRGConfig, use your own parameters here.
        SRGConfig srgConfig = new SRGConfig("il.srgssr.ch", "2.0", "rts", "buHostName", "buVirtualSite", "buStreamSenseVirtualSite", "netmetrixUrl");

        ilDataProviderComponent = DaggerIlDataProviderComponent.builder()
                .ilAppModule(new IlAppModule(this, BuildConfig.VERSION_NAME, BuildConfig.APPLICATION_ID))
                .sRGConfigModule(new SRGConfigModule(srgConfig))
                .build();

        appComponent = DaggerAppComponent.builder()
                .ilDataProviderComponent(ilDataProviderComponent)
                .build();

        appComponent.inject(this);

        // New magic class to setup everything for you !
        srgApplicationUtils = new SrgApplicationUtils(ilDataProviderComponent.getPerformanceProfiler(), ilDataProviderComponent.getIlDataProvider(), ilDataProviderComponent.getAkamaiTokenDataProvider(), this);
        srgApplicationUtils.setup();

        gameRepository.getQuestions(new GameDataSource.GetQuestionsCallback() {


            @Override
            public void onQuestionsLoaded(Questions questions) {
                Log.d(TAG, "onQuestionsLoaded: " + questions.getQuestions().size());
                RTSGameApplication.this.questions = questions;
            }

            @Override
            public void onDataNotAvailable() {
                Log.e(TAG, "onDataNotAvailable");
            }
        });
    }

    private static RTSGameApplication get(Context context) {
        return (RTSGameApplication) context.getApplicationContext();
    }

    public static AppComponent getAppComponent(Context context) {
        return get(context).appComponent;
    }

    public Questions getQuestions() {
        return this.questions;
    }

    private void setDefaultLocale() {
        String defaultLocale = "fr";
        Locale locale = new Locale(defaultLocale);
        Locale.setDefault(locale);

        Resources resources = getResources();

        Configuration configuration = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        setDefaultLocale();
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public static void replaceFragmentInView(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, String tag, boolean root) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        if (!root) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}
