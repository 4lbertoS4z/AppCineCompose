package com.example.movieandserieswiki.wiki.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieandserieswiki.core.domain.util.DomainError
import com.example.movieandserieswiki.core.domain.util.Result
import com.example.movieandserieswiki.wiki.domain.Movie
import com.example.movieandserieswiki.wiki.domain.Tv
import com.example.movieandserieswiki.wiki.domain.TvDataSource

class TvPagingSource(
    private val tvDataSource: TvDataSource, private val tvType: TvType
) : PagingSource<Int, Tv>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tv> {
        val page = params.key ?: 1
        val result: Result<List<Tv>, DomainError> = when (tvType) {
            TvType.POPULAR -> tvDataSource.getPopularTv(page)
            TvType.ON_AIR_NOW -> tvDataSource.getOnAirNowTv(page)
            TvType.BEST_SCORE -> tvDataSource.getBestScoreTv(page)
        }
        return when (result) {
            is Result.Success -> LoadResult.Page(
                data = result.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (result.data.isEmpty()) null else page + 1
            )

            is Result.Error -> LoadResult.Error(Throwable(result.error.toString()))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Tv>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}

enum class TvType {
    POPULAR,
    ON_AIR_NOW,
    BEST_SCORE
}