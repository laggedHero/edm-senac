package net.laggedhero.finalapp.dagger.modules;

import android.app.Application;

import com.ryanharter.auto.value.moshi.AutoValueMoshiAdapterFactory;
import com.squareup.moshi.Moshi;

import net.laggedhero.finalapp.services.NasaApodService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {

    private String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Cache providesOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder().cache(cache).build();
    }

    @Provides
    @Singleton
    Converter.Factory providesConverterFactory() {
        final Moshi moshi = new Moshi.Builder()
                .add(new AutoValueMoshiAdapterFactory())
                .build();
        return MoshiConverterFactory.create(moshi);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Converter.Factory converterFactory, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(converterFactory)
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    NasaApodService providesNasaApodService(Retrofit retrofit) {
        return retrofit.create(NasaApodService.class);
    }
}
