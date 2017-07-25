package com.example.trainee1.wozcar.Network;


import com.example.trainee1.wozcar.Network.Model.Prediction;
import com.example.trainee1.wozcar.Network.Model.ApiResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ton on 6/22/2016 AD.
 */
public interface APIService {

    @Multipart
    @POST("image")
    Call<ApiResponse> getPrediction(@Part MultipartBody.Part file1);
//            (@Part("Id") RequestBody id,
//                                                        @Part("Project") RequestBody project,
//                                                        @Part("Iteration") RequestBody iteration,
//                                                        @Part("Created") RequestBody create,
//                                                        @Part("Predictions") RequestBody predict);


}
