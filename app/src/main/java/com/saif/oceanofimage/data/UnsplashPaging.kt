package com.saif.oceanofimage.data

import android.util.Log
import androidx.paging.PagingSource
import com.saif.oceanofimage.api.UnsplashApi
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class UnsplashPaging(
    private val unsplashApi: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = unsplashApi.searchPhotos(query, position, params.loadSize)
            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            Log.d("ERROR:::", "IO: $exception")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.d("ERROR:::", "HTTP: $exception")
            LoadResult.Error(exception)
        }
    }
}