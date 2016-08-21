package net.laggedhero.finalapp.dagger.modules;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import net.laggedhero.finalapp.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

@Module
public class PersistenceModule {

    @Provides
    @Singleton
    FirebaseDatabase providesFirebaseDatabase() {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);

        return firebaseDatabase;
    }

//    @Provides
//    @Singleton
//    EntityDataStore<Persistable> providesDataStore(Application application) {
//        DatabaseSource source = new DatabaseSource(application, Models.DEFAULT, 1);
//        if (BuildConfig.DEBUG) {
//            // use this in development mode to drop and recreate the tables on every upgrade
//            source.setTableCreationMode(TableCreationMode.DROP_CREATE);
//        }
//        Configuration configuration = source.getConfiguration();
//
//        return new EntityDataStore<Persistable>(configuration);
//    }
}
