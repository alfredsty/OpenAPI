package sungil.myopenapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sungil.myopenapi.databinding.RvBookNaverBinding
import sungil.myopenapi.model.BookNaver

class BookNaverAdapter(
    // 클래스 객체의 생성자
    // 람다 표현식(자바/코틀린) 함수를 파라미터로 넘기는 것을 람다 표현식이라고 함.
    // (BookNaver) -> Unit 의 뜻은...
    // itemClickedListener 함수의 파라미터는 BookNaver 이고
    // itemClickedListener 함수의 리턴값은 없음(Unit)을 의미합니다
    private val itemClickedListener: (BookNaver) -> Unit
) : ListAdapter<BookNaver, BookNaverAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvBookNaverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder( // 생성자
        val binding: RvBookNaverBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookNaver) {
            binding.apply {
                tvTitle.text = book.title
                tvAuthor.text = book.author
                tvDescription.text = book.description
                Glide
                    .with(ivCover.context)
                    .load(book.image)
                    .into(ivCover)
                root.setOnClickListener {
                    itemClickedListener(book)
                }
            }
        }
    }

    // 자바에서는 static 이라는 키워드가 있어서 변수를 다른 객체서 공유해서 사용합니다.
    // 코틀린에서는 static 이 없고 대신에 companion 이 있습니다.
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BookNaver>() {
            // 리싸이클러뷰에서 데이터가 많을 때 속도를 향상시켜줍니다.
            // 리싸이클러뷰 리스트의 아이템이 같은 애들은 새로고침 안하고
            // 다른 애들만 새로고침 하는 방법으로 속도를 향상시켰습니다.(구글)
            override fun areItemsTheSame(oldItem: BookNaver, newItem: BookNaver): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: BookNaver, newItem: BookNaver): Boolean {
                return oldItem.isbn == newItem.isbn
            }
        }
    }
}