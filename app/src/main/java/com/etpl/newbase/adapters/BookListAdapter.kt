package com.etpl.newbase.adapters


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.etpl.newbase.R
import com.etpl.newbase.databinding.ItemBookListBinding
import com.etpl.newbase.model.BookListResponse
import com.etpl.newbase.view.appBase.MainActivity
import com.squareup.picasso.Picasso


class BookListAdapter: RecyclerView.Adapter<BookListAdapter.ViewHolder>() {
    lateinit var itemBookListBinding: ItemBookListBinding
    var bookList: List<BookListResponse.Result> = emptyList()

    var updateList = mutableListOf<BookListResponse.Result>()
    //var updateList: List<BookListResponse.Result> = emptyList()
    var filterBookList: List<BookListResponse.Result> = emptyList()
    var callback : ((String)->Unit)?=null

    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemBookListBinding =
            ItemBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        mContext = parent.context
        return ViewHolder(itemBookListBinding)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemBookListBinding = holder.itemBookListBinding

        itemBookListBinding.bookName.text = bookList[position].title


        if (bookList[position].authors.isNotEmpty() &&
            bookList[position].authors[0].name!=null) {
            itemBookListBinding.authorName.text = bookList[position].authors[0].name
        }
        if (bookList[position].image_url.isNotEmpty()) {
            Picasso.get().load(bookList[position].image_url).fit().centerCrop()
                .placeholder(R.drawable.ic_history)
                .error(R.drawable.ic_history)
                .into(itemBookListBinding.bookImage)
        }

       holder.itemView.setOnClickListener {
           when {
               bookList[position].html_url!=null -> {
                   callback!!.invoke(bookList[position].html_url)
               }
               bookList[position].pdf_url!=null -> {
                   callback!!.invoke(bookList[position].pdf_url)
               }
               bookList[position].text_url!=null -> {
                   callback!!.invoke(bookList[position].text_url)
               }
               else -> {
                   (mContext as MainActivity).noMimeTypeDialog()
               }
           }
       }

    }

    fun setData(bookList: List<BookListResponse.Result>?)
    {
        this.updateList.addAll(bookList!!)
        this.bookList = updateList
        this.filterBookList = this.bookList
        notifyDataSetChanged()
    }

    class ViewHolder(val itemBookListBinding: ItemBookListBinding) :
        RecyclerView.ViewHolder(itemBookListBinding.root)

    fun filterData(s: CharSequence) {
        if (s.isNotEmpty()) {
            bookList =
                filterBookList.filter {
                    when (it) {
                        is BookListResponse.Result -> {

                                it.authors[0].name.contains(s, true) ||
                                        it.title.contains(s, true)

                        }
                        else -> false
                    }
                }
        } else {
            this.bookList = filterBookList
        }
        notifyDataSetChanged()
    }

    fun filterDataOnSearch(s: CharSequence){
        val temp: MutableList<BookListResponse.Result> = ArrayList()
        for (it in bookList)
        {
            if(it.authors != null && it.authors.isNotEmpty() && it.authors[0].name!=null) {
                if (it.authors[0].name.contains(s, true) ||
                    it.title.contains(s, true)
                ) {
                    temp.add(it)
                }
            }
        }

        bookList = temp
        notifyDataSetChanged()
    }



}