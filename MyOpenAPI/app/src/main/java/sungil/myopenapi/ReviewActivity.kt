package sungil.myopenapi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import sungil.myopenapi.databinding.ActivityReviewBinding
import sungil.myopenapi.model.BookNaver
import sungil.myopenapi.model.BookReview

class ReviewActivity : AppCompatActivity() {
	private lateinit var binding: ActivityReviewBinding
	private lateinit var db: AppDatabase
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityReviewBinding.inflate(layoutInflater)
		db = getAppDatabase(this)
		setContentView(binding.root)

		val bookNaver = intent.getParcelableExtra<BookNaver>("bookNaver")
		bookNaver?.let { book ->
			binding.apply {
				tvTitle.text = book.title
				tvAuthor.text = book.author
				tvDescription.text = book.description
				Glide.with(ivCover.context)
					.load(book.image)
					.into(ivCover)
				btSave.setOnClickListener {
					Thread {
						db.bookReviewDao().saveReview(
							BookReview(
								book.isbn,
								etReview.text.toString()
							)
						)
					}.start()
					Toast.makeText(applicationContext, "저장했습니다", Toast.LENGTH_SHORT).show()
				}

				Thread {
					val bookReview: BookReview? = db.bookReviewDao().getReviewByIsbn(
						book.isbn
					)
					if (bookReview != null) {
						runOnUiThread {
							bookReview.reviewText?.let {
								etReview.setText(bookReview.reviewText)
							}
						}
					}

				}.start()
			}
		}

	}
}