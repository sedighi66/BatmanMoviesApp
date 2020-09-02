package org.msfox.batmanmoviesapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import org.msfox.batmanmoviesapp.db.converters.RatingListToStringConverter

@Entity(tableName = "description_table")
@TypeConverters(RatingListToStringConverter::class)
data class Description(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Genre")
    val genre: String,
    @SerializedName("Director")
    val director: String,
    @SerializedName("Writer")
    val writer: String,
    @SerializedName("Actors")
    val actors: String,
    @SerializedName("Language")
    val language: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("Awards")
    val awards: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("imdbRating")
    val imdbRating: String,
    @SerializedName("imdbVotes")
    val imdbVotes: String,
    @PrimaryKey
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Response")
    val response: String
)