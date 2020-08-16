package com.etpl.newbase.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.etpl.newbase.R
import com.etpl.newbase.databinding.ItemGenreListBinding
import com.etpl.newbase.model.GenreListResponse
import com.squareup.picasso.Picasso


class GenreListAdapter(val genreList: ArrayList<GenreListResponse>,var callback : ((String)->Unit)) :
    RecyclerView.Adapter<GenreListAdapter.ViewHolder>() {
    lateinit var itemGenreListBinding: ItemGenreListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        itemGenreListBinding = ItemGenreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemGenreListBinding)
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemGenreListBinding = holder.itemGenreListBinding
       holder.itemView.setOnClickListener {
           callback.invoke(genreList[position].genreName)
       }
        itemGenreListBinding.genreText.text = genreList[position].genreName

        when (position) {
            0 -> {
                Picasso.get().load(R.drawable.ic_fiction).fit().centerCrop()
                    .placeholder(R.drawable.ic_fiction)
                    .into(itemGenreListBinding.genreimage)
            }
            1 -> {
                Picasso.get().load(R.drawable.ic_drama).fit().centerCrop()
                    .placeholder(R.drawable.ic_drama)
                    .into(itemGenreListBinding.genreimage)
            }
            2 -> {
                Picasso.get().load(R.drawable.ic_humour).fit().centerCrop()
                    .placeholder(R.drawable.ic_humour)
                    .into(itemGenreListBinding.genreimage)
            }
            3 -> {
                Picasso.get().load(R.drawable.ic_politics).fit().centerCrop()
                    .placeholder(R.drawable.ic_politics)
                    .into(itemGenreListBinding.genreimage)
            }
            4 -> {
                Picasso.get().load(R.drawable.ic_philosophy).fit().centerCrop()
                    .placeholder(R.drawable.ic_philosophy)
                    .into(itemGenreListBinding.genreimage)
            }
            5 -> {
                Picasso.get().load(R.drawable.ic_history).fit().centerCrop()
                    .placeholder(R.drawable.ic_history)
                    .into(itemGenreListBinding.genreimage)
            }
            6 -> {
                Picasso.get().load(R.drawable.ic_adventure).fit().centerCrop()
                    .placeholder(R.drawable.ic_adventure)
                    .into(itemGenreListBinding.genreimage)
            }
        }

    }

    class ViewHolder(val itemGenreListBinding: ItemGenreListBinding) :
        RecyclerView.ViewHolder(itemGenreListBinding.root)

}

