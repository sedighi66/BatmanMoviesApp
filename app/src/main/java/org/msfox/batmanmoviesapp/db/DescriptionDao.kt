package org.msfox.batmanmoviesapp.db

import androidx.room.*
import org.msfox.batmanmoviesapp.model.Description
import org.msfox.batmanmoviesapp.model.Movie
import org.msfox.batmanmoviesapp.model.QueryIds


@Dao
abstract class DescriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(description: Description)

    @Query("SELECT * FROM description_table WHERE `imdbID` IN (:imdbId)")
    abstract suspend fun getDescription(imdbId: String): Description
}