package com.sunmarvel.features.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sunmarvel.features.home.data.model.response.Result
import com.sunmarvel.features.home.domain.MarvelUseCase
import com.sunmarvel.network.ktor.NetworkResult
import com.sunmarvel.network.ktor.Resource

class MarvelCharactersPagingSource(
    private val query: String,
    private val marvelUseCase: MarvelUseCase,
) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val pageNumber = params.key ?: 0
            val response = marvelUseCase.invokeGetMarvelCharacters(
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