package sungil.myopenapi.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sungil.myopenapi.model.BookReview

@Dao
interface BookReviewDAO {
    @Query("SELECT * FROM book_review_table WHERE isbn == :isbn")
    fun getReviewByIsbn(isbn: String) : BookReview
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveReview(review: BookReview)
}