package sungil.myopenapi.model

import com.google.gson.annotations.SerializedName
import sungil.myopenapi.book.Item

data class BookNaverDTO(
    @SerializedName("items")
    val items: List<BookNaver>
)
