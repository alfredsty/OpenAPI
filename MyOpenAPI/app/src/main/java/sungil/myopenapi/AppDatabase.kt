package sungil.myopenapi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import sungil.myopenapi.dao.BookReviewDAO
import sungil.myopenapi.model.BookReview

@Database(entities = [BookReview::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookReviewDao(): BookReviewDAO
}

fun getAppDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "BookReview"
    ).build()
}