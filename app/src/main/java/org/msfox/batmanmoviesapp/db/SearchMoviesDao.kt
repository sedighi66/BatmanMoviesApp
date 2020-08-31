package org.msfox.batmanmoviesapp.db

import androidx.room.*
import org.msfox.batmanmoviesapp.model.Movie
import org.msfox.batmanmoviesapp.model.QueryIds


/**
 * Created by mohsen on 28,June,2020
 */
@Dao
abstract class SearchMoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movie_table WHERE `imdbID` IN (:imdbIds)")
    abstract suspend fun getMoviesByIds(imdbIds: List<String>): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertQueryIds(queryIds: QueryIds)

    @Query("SELECT * FROM query_ids_table WHERE `query` = :query")
    abstract suspend fun getQueryIds(query: String): QueryIds

    @Transaction
    open suspend fun getMoviesByQuery(query: String): List<Movie>{
        val ids = getQueryIds(query).imdbIds
        return getMoviesByIds(ids)
    }

    @Transaction
    open suspend fun insertQueryIdsAndMovies(query: String, movies: List<Movie>){
        val queryIds = QueryIds(query, movies.map { it.imdbID })

        insertQueryIds(queryIds)
        insertMovies(movies)
    }
}