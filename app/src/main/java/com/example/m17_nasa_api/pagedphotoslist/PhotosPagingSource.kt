package com.example.m17_nasa_api.pagedphotoslist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.m17_nasa_api.models.PhotoResponse
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

class PhotosPagingSource : PagingSource<Int, PhotoResponse>() {

    private val repository = PhotosListRepository()

    @RequiresApi(Build.VERSION_CODES.O)
    val startingSol = 4060

    override fun getRefreshKey(state: PagingState<Int, PhotoResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey ?: state.closestPageToPosition(
                anchorPosition
            )?.nextKey
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoResponse> {
        // Получаем текущую дату для загрузки данных. Если null, используем введенную пользователем.
        val currentSol = params.key ?: startingSol

        return kotlin.runCatching {

            // Запрашиваем фотографии для даты, введенной пользователем
            val response = repository.getPhotosList(currentSol)
            val photos = response.photos
            if (photos.isEmpty()) throw Exception("No photos found for SOL $currentSol")
            LoadResult.Page(
                data = photos,
                prevKey = if (currentSol > 0) currentSol - 1 else null,
                nextKey = if (photos.isNotEmpty()) currentSol + 1 else null
            )
        }.fold(
            onSuccess = { it },
            onFailure = { LoadResult.Error(it) }
        )
    }
}