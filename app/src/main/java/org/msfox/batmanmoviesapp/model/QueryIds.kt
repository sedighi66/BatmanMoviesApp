package org.msfox.batmanmoviesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.msfox.batmanmoviesapp.db.converters.StringListToStringConverter

/**
 * A table to save movie ids for a query. In this case, all queries are batman
 */
@Entity(tableName = "query_ids_table")
@TypeConverters(StringListToStringConverter::class)
data class QueryIds(
    @PrimaryKey
    val query: String,
    val imdbIds: List<String>
)