package net.laggedhero.finalapp.dagger.components;

import net.laggedhero.finalapp.dagger.modules.AppModule;
import net.laggedhero.finalapp.dagger.modules.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {
}
