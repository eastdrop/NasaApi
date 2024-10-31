package com.example.m17_nasa_api.pagedphotoslist

import com.example.m17_nasa_api.api.retrofit
import com.example.m17_nasa_api.models.PagedPhotosList
import com.example.m17_nasa_api.models.PhotoResponse
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PhotosListRepository {
    suspend fun getPhotosList(date: Date): PagedPhotosList{
        val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.US)
        val formattedDate = dateFormat.format(date)
        delay(2000)
        return retrofit.photosList(formattedDate)
    }
}