package net.laggedhero.finalapp;

import android.app.Application;

import net.laggedhero.finalapp.dagger.components.DaggerNetworkComponent;
import net.laggedhero.finalapp.dagger.components.DaggerPersistenceComponent;
import net.laggedhero.finalapp.dagger.components.NetworkComponent;
import net.laggedhero.finalapp.dagger.components.PersistenceComponent;
import net.laggedhero.finalapp.dagger.modules.AppModule;
import net.laggedhero.finalapp.dagger.modules.NetworkModule;
import net.laggedhero.finalapp.dagger.modules.PersistenceModule;

public class FinalAppApplication extends Application {

    private NetworkComponent networkComponent;
    private PersistenceComponent persistenceComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        final AppModule appModule = new AppModule(this);

        networkComponent = DaggerNetworkComponent.builder()
                .appModule(appModule)
                .networkModule(new NetworkModule("https://api.nasa.gov"))
                .build();

        persistenceComponent = DaggerPersistenceComponent.builder()
                .appModule(appModule)
                .persistenceModule(new PersistenceModule())
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }

    public PersistenceComponent getPersistenceComponent() {
        return persistenceComponent;
    }
}
