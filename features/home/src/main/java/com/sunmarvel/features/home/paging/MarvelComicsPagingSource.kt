package com.sunmarvel.features.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sunmarvel.features.home.data.model.response.ComicsResult
import com.sunmarvel.features.home.domain.MarvelUseCase
import com.sunmarvel.network.ktor.NetworkResult
import com.sunmarvel.network.ktor.Resource

class MarvelComicsPagingSource(
    private val query: String,
    private val marvelUseCase: MarvelUseCase,
) : PagingSource<Int, ComicsResult>() {
    override fun getRefreshKey(state: PagingState<Int, ComicsResult>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ComicsResult> {
        return try {
            val pageNumber = params.key ?: 0
            val response = marvelUseCase.invokeGetMarvelComics(
                query,
                (pageNumber * params.loadSize).toString()
            )

            if (response is Resource.Success) {
                val results = response.data.data.results
                LoadResult.Page(
                    data = results,
                    prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                    nextKey = if (results.isNotEmpty()) pageNumber + 1 else null
                )
            } else {
                LoadResult.Error(
                    if(response is Resource.Error){
                        if(response.data is NetworkResult.Error<*>){
                            Throwable((response.data as NetworkResult.Error<*>).body.message)
                        }else{
                            Throwable(response.data.toString())
                        }
                    } else {
                        Throwable("Error")
                    }

                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}