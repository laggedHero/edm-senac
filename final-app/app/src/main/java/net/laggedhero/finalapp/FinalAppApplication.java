package net.laggedhero.finalapp;

import android.app.Application;

import net.laggedhero.finalapp.dagger.components.DaggerNetworkComponent;
import net.laggedhero.finalapp.dagger.components.NetworkComponent;
import net.laggedhero.finalapp.dagger.modules.AppModule;
import net.laggedhero.finalapp.dagger.modules.NetworkModule;

public class FinalAppApplication extends Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        networkComponent = DaggerNetworkComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule("https://api.nasa.gov"))
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }
}
