package net.laggedhero.finalapp.models;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import io.requery.Entity;
import io.requery.Key;

@Entity
@AutoValue
public abstract class NasaApod {

    @AutoValue.Builder
    static abstract class Builder {
        abstract Builder setCopyright(String copyright);

        abstract Builder setApodDate(String date);

        abstract Builder setExplanation(String explanation);

        abstract Builder setHdUrl(String hdUrl);

        abstract Builder setMediaType(String mediaType);

        abstract Builder setServiceVersion(String serviceVersion);

        abstract Builder setTitle(String title);

        abstract Builder setUrl(String url);

        abstract NasaApod build();
    }

    static Builder builder() {
        return new AutoValue_NasaApod.Builder();
    }

    @Nullable
    @Json(name = "copyright")
    public abstract String getCopyright();

    @Key
    @Json(name = "date")
    public abstract String getApodDate();

    @Json(name = "explanation")
    public abstract String getExplanation();

    @Json(name = "hdurl")
    public abstract String getHdUrl();

    @Json(name = "media_type")
    public abstract String getMediaType();

    @Json(name = "service_version")
    public abstract String getServiceVersion();

    @Json(name = "title")
    public abstract String getTitle();

    @Json(name = "url")
    public abstract String getUrl();

    public static JsonAdapter<NasaApod> jsonAdapter(Moshi moshi) {
        return new AutoValue_NasaApod.MoshiJsonAdapter(moshi);
    }
}
