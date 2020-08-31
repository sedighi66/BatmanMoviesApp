package org.msfox.batmanmoviesapp.api


import com.google.gson.annotations.SerializedName
import org.msfox.batmanmoviesapp.model.Movie

data class SearchMovies(
    @SerializedName("Search")
    val movies: List<Movie>,
    @SerializedName("totalResults")
    val totalResults: String,
    @SerializedName("Response")
    val response: String
)