package com.example.m17_nasa_api.api

import com.example.m17_nasa_api.models.PagedPhotosList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.Date

interface PhotosListApi {
    @GET("rovers/curiosity/photos")
    suspend fun photosList(
        @Query("sol") sol: Int,
        @Query("api_key") api_key: String = "LmdY1YaroejIZJaJ191AgvqT17s9hnXtWSsNfeR8"
    ): PagedPhotosList
}

val retrofit = Retrofit
    .Builder()
    .client(
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }).build()
    )
    .baseUrl("https://api.nasa.gov/mars-photos/api/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(PhotosListApi::class.java)