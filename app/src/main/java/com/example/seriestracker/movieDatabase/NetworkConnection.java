package com.example.seriestracker.movieDatabase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.seriestracker.utils.GlobalValues.BASE_URL;

public class NetworkConnection {
    private static Retrofit retrofit;

    public static MovieApi getRetrofit(){
        return getRetrofitClient().create(MovieApi.class);
    }

    private static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
