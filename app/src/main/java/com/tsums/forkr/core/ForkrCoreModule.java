package com.tsums.forkr.core;

import android.content.Context;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.tsums.forkr.R;
import com.tsums.forkr.network.ForkrNetworkService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by trevor on 1/16/16.
 */
@Module
public class ForkrCoreModule {

    private Context context;

    public ForkrCoreModule(Context context) {
        this.context = context;
    }

    @Provides
    @Named("endpoint")
    String provideEndpointString() {
        return context.getString(R.string.endpoint);
    }

    @Provides
    OkHttpClient provideOkHttp() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient client, @Named("endpoint") String endpoint) {
        return new Retrofit.Builder().baseUrl(endpoint).client(client).build();
    }

    @Provides
    public ForkrNetworkService provideNetworkService(Retrofit retrofit) {
        return retrofit.create(ForkrNetworkService.class);
    }

    @Provides
    public Picasso providePicasso(OkHttpClient client) {
        return new Picasso.Builder(context).build();

    }
}
