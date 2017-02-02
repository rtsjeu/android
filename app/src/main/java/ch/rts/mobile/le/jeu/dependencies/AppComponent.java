package ch.rts.mobile.le.jeu.dependencies;

import ch.rts.mobile.le.jeu.RTSGameApplication;
import ch.rts.mobile.le.jeu.activities.GameActivity;
import ch.rts.mobile.le.jeu.activities.MainActivity;
import ch.rts.mobile.le.jeu.data.source.GameRepository;
import ch.rts.mobile.le.jeu.dependencies.modules.DataModule;
import ch.rts.mobile.le.jeu.dependencies.scope.AppScope;
import ch.srg.dataProvider.integrationlayer.dependencies.components.IlDataProviderComponent;
import ch.srg.srgmediaplayer.fragment.SRGLetterbox;
import dagger.Component;

/**
 * Created by npietri on 02.02.17.
 */
@AppScope
@Component(
        dependencies = {
                IlDataProviderComponent.class
        },
        modules = {
                DataModule.class
        }
)
public interface AppComponent {

    void inject(RTSGameApplication application);

    void inject(MainActivity mainActivity);

    void inject(GameActivity gameActivity);

    void inject(SRGLetterbox fragment);

    GameRepository getGameRepository();

}
