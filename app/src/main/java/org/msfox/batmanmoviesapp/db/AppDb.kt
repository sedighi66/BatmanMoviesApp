package org.msfox.batmanmoviesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.msfox.batmanmoviesapp.model.Movie
import org.msfox.batmanmoviesapp.model.QueryIds
import javax.inject.Singleton

/**
 * Created by mohsen on 28,June,2020
 */

@Singleton
@Database(entities = [Movie::class, QueryIds::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {

    abstract fun searchMoviesDao(): SearchMoviesDao
}
