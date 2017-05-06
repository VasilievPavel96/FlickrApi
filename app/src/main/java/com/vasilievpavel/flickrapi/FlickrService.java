package com.vasilievpavel.flickrapi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pavel on 06.05.2017.
 */

public interface FlickrService {
    @GET("?method=flickr.photos.getRecent&format=json")
    Call<ResponseBody> getRecent(@Query("api_key") String key);
}
