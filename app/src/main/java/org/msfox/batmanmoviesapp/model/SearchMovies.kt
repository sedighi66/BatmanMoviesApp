package org.msfox.batmanmoviesapp.model


import com.google.gson.annotations.SerializedName

data class SearchMovies(
    @SerializedName("Search")
    val movies: List<Movie>,
    @SerializedName("totalResults")
    val totalResults: String,
    @SerializedName("Response")
    val response: String
)