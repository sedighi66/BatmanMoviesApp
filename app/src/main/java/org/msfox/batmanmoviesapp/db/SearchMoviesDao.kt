package org.msfox.batmanmoviesapp.db

import androidx.room.*
import org.msfox.batmanmoviesapp.model.Movie
import org.msfox.batmanmoviesapp.model.QueryIds


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

    @Query("DELETE FROM query_ids_table")
    abstract suspend fun deleteAllQueryIds()

    @Query("DELETE FROM query_ids_table WHERE `query` = :query")
    abstract suspend fun deleteQuery(query: String)

    @Transaction
    open suspend fun getMovies(query: String): List<Movie>{
        val queryIds = getQueryIds(query)
        val ids = mutableListOf<String>()
        if(queryIds != null)
            ids.addAll(queryIds.imdbIds)

        return getMoviesByIds(ids)
    }

    @Transaction
    open suspend fun insertQueryAndMovies(query: String, movies: List<Movie>){
        val previousIds = getQueryIds(query)
        val newIds =  movies.map { it.imdbID }
        val totalIds = mutableListOf<String>().apply {
            if(previousIds != null && previousIds.imdbIds.isNotEmpty())
                this.addAll(previousIds.imdbIds)

            this.addAll(newIds)
        }
        val queryIds = QueryIds(query, totalIds)

        insertQueryIds(queryIds)
        insertMovies(movies)
    }
}