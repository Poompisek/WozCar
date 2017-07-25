package com.example.trainee1.wozcar.Network.Model;

import android.content.Context;

import com.example.trainee1.wozcar.Contextor;
import com.example.trainee1.wozcar.Network.APIService;

import java.io.IOException;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Trainee1 on 7/20/2017.
 */

public class ConnectionService {

    private static ConnectionService instance = null;

    private Context context = null;

    private APIService apiService = null;

    private Retrofit retrofit = null;

    private ConnectionService() {

        context = Contextor.getInstance().getContext();

//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(Date.class, new DateDeserializer())
//                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Prediction-Key", "1183fe36baf145bab59e664e4642c757")
                        //.addHeader("Content-Type", "application/octet-stream")
                        .build();

                return chain.proceed(request);
            }
        });
//        httpClient.connectTimeout(15, TimeUnit.SECONDS)
//                .writeTimeout(15, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS);
//        httpClient.addInterceptor(logging);
//        httpClient.certificatePinner(MyCertificatePinner.getInstance().getCertificatePinner());

        retrofit = new Retrofit.Builder()
                .baseUrl("https://southcentralus.api.cognitive.microsoft.com/customvision/v1.0/Prediction/e1ba9ac9-07bd-4585-ac70-6c0ff80e592c/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        apiService = retrofit.create(APIService.class);
    }

    public static ConnectionService getInstance() {
        if (instance == null) {
            instance = new ConnectionService();
        }
        return instance;
    }

    public APIService getApiService() {
        return apiService;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
