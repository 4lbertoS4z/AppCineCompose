package com.example.movieandserieswiki.wiki.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieandserieswiki.core.domain.util.Result
import com.example.movieandserieswiki.core.domain.util.DomainError
import com.example.movieandserieswiki.wiki.domain.Movie
import com.example.movieandserieswiki.wiki.domain.MovieDataSource

class MoviePagingSource(
    private val movieDataSource: MovieDataSource,
    private val movieType: MovieType  // Enum para diferenciar entre popular, upcoming, etc.
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val result: Result<List<Movie>, DomainError> = when (movieType) {
            MovieType.POPULAR -> movieDataSource.getPopularMovies(page)
            MovieType.UPCOMING -> movieDataSource.getUpcomingMovies(page)
            MovieType.NOW_PLAYING -> movieDataSource.getNowPlayingMovies(page)
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

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}

enum class MovieType {
    POPULAR,
    UPCOMING,
    NOW_PLAYING
}