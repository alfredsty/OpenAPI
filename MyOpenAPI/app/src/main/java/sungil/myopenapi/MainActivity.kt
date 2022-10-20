package sungil.myopenapi

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sungil.myopenapi.adapter.BookNaverAdapter
import sungil.myopenapi.api.BookNaverAPI
import sungil.myopenapi.databinding.ActivityMainBinding
import sungil.myopenapi.model.BookNaverDTO

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private lateinit var adapter: BookNaverAdapter
	private lateinit var service: BookNaverAPI
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		adapter = BookNaverAdapter(itemClickedListener = {
			val intent = Intent(this, ReviewActivity::class.java)
			intent.putExtra("bookNaver", it)
			startActivity(intent)
		})

		// 레트로핏으로 네이버 OPEN API 서버 연결함
		var retrofitNaver = Retrofit.Builder()
			.baseUrl("https://openapi.naver.com")
			.addConverterFactory(GsonConverterFactory.create())
			.build()
		binding.rvBooks.adapter = adapter
		// 네이버 서버에서 "책(검색어:개발자)" 관련 자료를 JSON 형태로 가져옴
		service = retrofitNaver.create(BookNaverAPI::class.java)

		binding.etSearch.setOnKeyListener { v, keyCode, event ->
			if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
				service.getBooksByName(
					"1tVlcTurguNDu3hhNh7S",
					"03P6L751KQ",
					binding.etSearch.text.toString()
				).enqueue(object : Callback<BookNaverDTO> {
					// 성공적으로 JSON을 가져옴
					override fun onResponse(
						call: Call<BookNaverDTO>,
						response: Response<BookNaverDTO>
					) {
						response.body()?.let {
							// JSON 을 가져와서 items 에 들어 있는 리스트를 for문으로 보여줌
//                    it.items.forEach { book ->
//                        Log.d("에러체크", "${book.title} ${book.author}")
//                    }
							// 리싸이클러뷰에서 사용할 리스트를 지정해줍니다
							adapter.submitList(it.items)
						}
					}

					// JSON 가져오기 실패
					override fun onFailure(call: Call<BookNaverDTO>, t: Throwable) {
					}
				})
				return@setOnKeyListener true
			}
			return@setOnKeyListener false
		}

		service.getBooksByName(
			"1tVlcTurguNDu3hhNh7S",
			"03P6L751KQ",
			"개발자"
		).enqueue(object : Callback<BookNaverDTO> {
			// 성공적으로 JSON을 가져옴
			override fun onResponse(
				call: Call<BookNaverDTO>,
				response: Response<BookNaverDTO>
			) {
				response.body()?.let {
					// JSON 을 가져와서 items 에 들어 있는 리스트를 for문으로 보여줌
//                    it.items.forEach { book ->
//                        Log.d("에러체크", "${book.title} ${book.author}")
//                    }
					// 리싸이클러뷰에서 사용할 리스트를 지정해줍니다
					adapter.submitList(it.items)
				}
			}

			// JSON 가져오기 실패
			override fun onFailure(call: Call<BookNaverDTO>, t: Throwable) {
			}
		})
	}
}