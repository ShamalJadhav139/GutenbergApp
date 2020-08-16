package com.etpl.newbase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.etpl.newbase.R
import com.etpl.newbase.adapters.GenreListAdapter
import com.etpl.newbase.databinding.FragmentGenreListBinding
import com.etpl.newbase.model.GenreListResponse
import com.etpl.newbase.view.appBase.BaseFragment
import com.etpl.newbase.view.appBase.MainActivity


class GenreListFragment : BaseFragment() {
    private var fragmentGenreListBinding: FragmentGenreListBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentGenreListBinding = FragmentGenreListBinding.inflate(LayoutInflater.from(context), container, false)
        return fragmentGenreListBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragmentGenreListBinding!!.genreRv.layoutManager = LinearLayoutManager(activity)
        val dataList1 = ArrayList<GenreListResponse>()

        dataList1.add(GenreListResponse(R.drawable.ic_fiction, resources.getString(R.string.fiction)))
        dataList1.add(GenreListResponse(R.drawable.ic_drama, resources.getString(R.string.drama)))
        dataList1.add(GenreListResponse(R.drawable.ic_humour, resources.getString(R.string.humor)))
        dataList1.add(GenreListResponse(R.drawable.ic_politics, resources.getString(R.string.politics)))
        dataList1.add(GenreListResponse(R.drawable.ic_philosophy, resources.getString(R.string.philosophy)))
        dataList1.add(GenreListResponse(R.drawable.ic_history, resources.getString(R.string.history)))
        dataList1.add(GenreListResponse(R.drawable.ic_adventure, resources.getString(R.string.adventure)))
        val adapter1 = GenreListAdapter(dataList1){
            (context as MainActivity).launchBookListFragment(it)
        }
        fragmentGenreListBinding!!.genreRv.adapter = adapter1
    }


}