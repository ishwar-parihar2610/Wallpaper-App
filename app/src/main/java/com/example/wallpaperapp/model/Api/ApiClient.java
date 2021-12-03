package com.example.wallpaperapp.model.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL="https://api.unsplash.com/";
    public static final String API_KEY="YaZgOmN0q7_ZA5YrGizHlqry4eeXXOEmC1276FDbLAs";

    public static Retrofit retrofit=null;
    public static ApiInterface getApiInterface(){
            if (retrofit==null){
                retrofit=new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit.create(ApiInterface.class);
    }
}
