package sungil.myopenapi.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import sungil.myopenapi.model.BookNaverDTO

//https://openapi.naver.com/v1/search/book.json
interface BookNaverAPI {
    @GET("/v1/search/book.json")
    fun getBooksByName(
        @Header("X-Naver-Client-Id") id:String,
        @Header("X-Naver-Client-Secret") secretKey: String,
        @Query("query") keyword : String
    ): Call<BookNaverDTO>
}