package org.msfox.batmanmoviesapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_table")
data class Movie(
    //because imdbId is not incremental in api, we save the currentTimeUtil of saving time
    //for showing the data according to the order of api
    @Expose
    var saveTime: Long,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @PrimaryKey
    @SerializedName("imdbID")
    val imdbID: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val poster: String
)