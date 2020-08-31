package org.msfox.batmanmoviesapp.api

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

}