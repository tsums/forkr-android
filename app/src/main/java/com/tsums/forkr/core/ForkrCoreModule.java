package com.tsums.forkr.core;

import android.content.Context;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tsums.forkr.R;
import com.tsums.forkr.network.ForkrAuthService;
import com.tsums.forkr.network.ForkrNetworkService;

import java.io.IOException;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.GsonConverterFactory;
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
    @Named("UnauthenticatedOK")
    OkHttpClient provideOkHttp() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Named("AuthInterceptor")
    Interceptor provideAuthInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                return null; // TODO interceptor add oauth token header.
            }
        };
    }

    @Provides
    @Named("AuthenticatedOK")
    OkHttpClient provideAuthenticatedOkHttp(@Named("AuthInterceptor") Interceptor interceptor) {
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Provides
    @Named("UnauthenticatedRetro")
    Retrofit provideRetrofit(@Named("UnauthenticatedOK") OkHttpClient client, @Named("endpoint") String endpoint) {
        return new Retrofit.Builder().baseUrl(endpoint).client(client).addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Provides
    @Named("AuthenticatedRetro")
    Retrofit provideAuthenticatedRetrofit(@Named("AuthenticatedOK") OkHttpClient client, @Named("endpoint") String endpoint) {
        return new Retrofit.Builder().baseUrl(endpoint).client(client).addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Provides
    public ForkrNetworkService provideNetworkService(@Named("AuthenticatedRetro") Retrofit retrofit) {
        return retrofit.create(ForkrNetworkService.class);
    }

    @Provides
    public ForkrAuthService provideAuthService(@Named("UnauthenticatedRetro") Retrofit retrofit) {
        return retrofit.create(ForkrAuthService.class);
    }

    @Provides
    public Picasso providePicasso() {
        return new Picasso.Builder(context).indicatorsEnabled(true).loggingEnabled(true).build();

    }
}
