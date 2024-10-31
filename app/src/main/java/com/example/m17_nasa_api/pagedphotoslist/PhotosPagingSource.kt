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

class PhotosPagingSource : PagingSource<Date, PhotoResponse>() {

    private val repository = PhotosListRepository()

    @RequiresApi(Build.VERSION_CODES.O)
    val localDate: LocalDate = LocalDate.of(2023, 1, 7)
    @RequiresApi(Build.VERSION_CODES.O)
    val userDate: Date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    override fun getRefreshKey(state: PagingState<Date, PhotoResponse>): Date? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey ?: state.closestPageToPosition(
                anchorPosition
            )?.nextKey
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Date>): LoadResult<Date, PhotoResponse> {
        // Получаем текущую дату для загрузки данных. Если null, используем введенную пользователем.
        val currentDate = params.key ?: userDate

        return kotlin.runCatching {

            // Запрашиваем фотографии для даты, введенной пользователем
            val response = repository.getPhotosList(currentDate)
            val photos = response.photos

            // Определяем следующую и предыдущую даты
            val nextDate = if (photos.isNotEmpty()) getNextDate(currentDate) else null
            val prevDate = if (currentDate != userDate) getPreviousDate(currentDate) else null

            LoadResult.Page(
                data = photos, prevKey = prevDate, nextKey = nextDate
            )
        }.fold(
            onSuccess = { result -> result },
            onFailure = { exception -> LoadResult.Error(exception) }
        )
    }

    private fun getNextDate(currentDate: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        return calendar.time
    }

    private fun getPreviousDate(currentDate: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        return calendar.time
    }
}

