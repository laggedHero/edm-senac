package net.laggedhero.finalapp.dagger.components;

import net.laggedhero.finalapp.ApodService;
import net.laggedhero.finalapp.dagger.modules.AppModule;
import net.laggedhero.finalapp.dagger.modules.NetworkModule;
import net.laggedhero.finalapp.dagger.modules.PersistenceModule;
import net.laggedhero.finalapp.ui.main.MainActivity;
import net.laggedhero.finalapp.ui.splash.SplashActivity;
import net.laggedhero.finalapp.ui.tutorial.TutorialActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, PersistenceModule.class, NetworkModule.class})
public interface ApplicationComponent {
    void inject(SplashActivity splashActivity);

    void inject(TutorialActivity tutorialActivity);

    void inject(MainActivity mainActivity);

    void inject(ApodService apodService);
}
