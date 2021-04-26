package example.com.newapp.Utils;


import java.util.concurrent.TimeUnit;

import example.com.newapp.BuildConfig;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import okhttp3.logging.HttpLoggingInterceptor;

/*
    Bu class'da API'mizin belli olan URL'sine hook atmak için tanımlamalar yapıyoruz.
 */
/*
    In this class, we are making definitions to hook the specific URL of our API.
 */

public class Api {
    private static final String BASE_URL = "https://soccer.sportmonks.com/api/v2.0/";
    private static final String TAG = "API";
    public static <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS)
                .connectTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .cache(null);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        builder.client(httpClient.build());
        Retrofit retrofit = builder.build();
        return  retrofit.create(serviceClass);
    }

}