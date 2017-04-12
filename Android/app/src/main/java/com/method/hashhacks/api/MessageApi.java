package com.method.hashhacks.api;

import com.method.hashhacks.models.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by piyush0 on 12/04/17.
 */

public interface MessageApi {

    @POST("fuck_blah")
    Call<ArrayList<Result>> getResult(@Body String query);
}
