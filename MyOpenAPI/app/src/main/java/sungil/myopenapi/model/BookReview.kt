package sungil.myopenapi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_review_table")
data class BookReview(
	@PrimaryKey
	@ColumnInfo(name = "isbn")
	val id: String,
	@ColumnInfo(name = "reviewText")
	var reviewText: String = ""
)