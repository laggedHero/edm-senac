package net.laggedhero.finalapp.dagger.components;

import net.laggedhero.finalapp.dagger.modules.AppModule;
import net.laggedhero.finalapp.dagger.modules.PersistenceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, PersistenceModule.class})
public interface PersistenceComponent {
}
