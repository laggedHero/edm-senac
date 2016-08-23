package net.laggedhero.finalapp;

import android.app.Application;

import net.laggedhero.finalapp.dagger.components.ApplicationComponent;
import net.laggedhero.finalapp.dagger.components.DaggerApplicationComponent;
import net.laggedhero.finalapp.dagger.modules.AppModule;
import net.laggedhero.finalapp.dagger.modules.NetworkModule;
import net.laggedhero.finalapp.dagger.modules.PersistenceModule;

public class FinalAppApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule("https://api.nasa.gov"))
                .persistenceModule(new PersistenceModule())
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
