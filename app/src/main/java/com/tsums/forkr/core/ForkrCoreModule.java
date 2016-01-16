package com.tsums.forkr.core;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.tsums.forkr.R;
import com.tsums.forkr.network.ForkrNetworkService;
import com.tsums.forkr.network.GithubService;

import java.io.IOException;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


@Module
public class ForkrCoreModule {

    private Context context;

    public ForkrCoreModule(Context context) {
        this.context = context;
    }

    @Provides
    @Named("UnauthenticatedOK")
    OkHttpClient provideOkHttp() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(logging).build();
    }

    @Provides
    public GithubService provideGithubService(@Named("UnauthenticatedOK") OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService.class);
    }

    // --- Authenticated Backend --- //

    @Provides
    @Named("AuthInterceptor")
    Interceptor provideAuthInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept (Chain chain) throws IOException {
                return chain.proceed(chain.request());
            }
        };
    }

    @Provides
    @Named("AuthenticatedOK")
    OkHttpClient provideAuthenticatedOkHttp(@Named("AuthInterceptor") Interceptor interceptor) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(logging).build();
    }

    @Provides
    @Named("AuthenticatedRetro")
    Retrofit provideAuthenticatedRetrofit(@Named("AuthenticatedOK") OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(context.getString(R.string.endpoint)).client(client).addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Provides
    public ForkrNetworkService provideNetworkService(@Named("AuthenticatedRetro") Retrofit retrofit) {
        return retrofit.create(ForkrNetworkService.class);
    }

    // --- Utils --- //

    @Provides
    public Picasso providePicasso() {
        return new Picasso.Builder(context).indicatorsEnabled(true).loggingEnabled(true).build();
    }
}
