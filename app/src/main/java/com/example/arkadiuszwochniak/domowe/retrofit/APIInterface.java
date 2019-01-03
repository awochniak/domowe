package com.example.arkadiuszwochniak.domowe.retrofit;



import com.example.arkadiuszwochniak.domowe.objects.Photos;
import com.example.arkadiuszwochniak.domowe.objects.PhotosDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {


    @GET("photos")
    Call<List<Photos>>  getPeople(@Query("format") String format);

    @GET
    Call<List<PhotosDetails>> getFilmData(@Url String url, @Query("format") String format);
}