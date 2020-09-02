package org.msfox.batmanmoviesapp.api

import org.msfox.batmanmoviesapp.model.Description
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    /**
     * search for movies.
     * @param query is the query of your search.
     * @param page is the page of your search. Each page has 10 the data of 10 movies.
     */
    @GET("?apikey=3e974fca")
    suspend fun search(@Query("s") query: String,
                       @Query("page") page: Int): NetworkResponse<SearchMovies, Any>

    @GET("?apikey=3e974fca")
    suspend fun description(@Query("i") imdbId: String): NetworkResponse<Description, Any>


    companion object{
        /**
         * each page that api returns have OFFSET number of movie in it.
         */
        const val OFFSET = 10
    }

}