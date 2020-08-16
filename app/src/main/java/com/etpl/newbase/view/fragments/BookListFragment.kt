package com.etpl.newbase.view.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.etpl.newbase.R
import com.etpl.newbase.adapters.BookListAdapter
import com.etpl.newbase.constants.ApiConstants
import com.etpl.newbase.databinding.FragmentBookListBinding
import com.etpl.newbase.model.BookListResponse
import com.etpl.newbase.networkContracter.MainActivityPresenter
import com.etpl.newbase.networkContracter.MainContractor
import com.etpl.newbase.view.appBase.BaseFragment
import com.google.gson.Gson
import org.json.JSONObject


class BookListFragment : BaseFragment(), MainContractor.View {
    private var fragmentBookListBinding: FragmentBookListBinding? = null
    private var presenter: MainContractor.Presenter? = null
    private var bookListAdapter: BookListAdapter? = null
    var name: String = ""
    var nextUrl: String = ""
    var page: Int = 1
    private var visibleThreshold = 3
    private var lastVisibleItem = 0
    private var totalItemCount = 32
    var notLoading = true
    lateinit var gridlayoutManager: GridLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBookListBinding =
            FragmentBookListBinding.inflate(LayoutInflater.from(context), container, false)
        presenter = MainActivityPresenter(this)
        return fragmentBookListBinding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments != null) {
            name = arguments!!.getString("name", "")
        }
        callGetBookApi()

        bookListAdapter = BookListAdapter()
        fragmentBookListBinding!!.bookListRecycleView.setHasFixedSize(true)
        gridlayoutManager = GridLayoutManager(activity, 3)
        fragmentBookListBinding!!.bookListRecycleView.layoutManager = gridlayoutManager
        fragmentBookListBinding!!.bookListRecycleView.setAdapter(bookListAdapter)

        addScrollerListener()

        fragmentBookListBinding!!.searchText.setOnClickListener {
            fragmentBookListBinding!!.llSearch.setBackgroundResource(R.drawable.button_border_blue_bg)
        }

        bookListAdapter!!.callback = {
            val uri = Uri.parse(it)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        fragmentBookListBinding!!.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                bookListAdapter!!.filterDataOnSearch(s)

            }

            override fun afterTextChanged(s: Editable) {}
        })


    }


    private fun addScrollerListener() {
        fragmentBookListBinding!!.bookListRecycleView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = gridlayoutManager.itemCount
                lastVisibleItem = gridlayoutManager.findLastCompletelyVisibleItemPosition()
                if (notLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    page++
                    notLoading = false
                    if(nextUrl!=null && nextUrl.isNotEmpty()){
                    callGetBookApi()
                    }
                }
            }
        })
    }


    fun callGetBookApi() {
        val paramArray = arrayOf<String>(
            name,
            "image/jpeg", page.toString()
        )
        presenter!!.onClick(
            ApiConstants.getBooks,
            paramArray,
            requireContext(),
            true
        )
    }

    override fun setViewData(data: String, view: ApiConstants) {
        when (view) {
            ApiConstants.getBooks -> {
                val response = Gson().fromJson(data, BookListResponse::class.java)
                if (response != null) {
                    nextUrl = response.next
                    notLoading = true
                    bookListAdapter!!.setData(response.results)
                    val jsonObject = JSONObject(data)
                    var jsonArray = jsonObject.getJSONArray("results")

                    for (i in response.results.indices) {
                        jsonArray.get(i)
                        var url = jsonArray.getJSONObject(i).getJSONObject("formats")
                            .getString("image/jpeg")
                        response.results[i].image_url = url

                        if (jsonArray.getJSONObject(i).getJSONObject("formats")
                                .has("application/pdf")
                        ) {
                            var pdf_url_text = jsonArray.getJSONObject(i).getJSONObject("formats")
                                .getString("application/pdf")
                            response.results[i].pdf_url = pdf_url_text
                        }

                        var mimeTypes = jsonArray.getJSONObject(i).getJSONObject("formats").keys()

                        for (st in mimeTypes) {
                            if (st.length > 8 && st.substring(0, 9) == "text/html") {
                                var html_url = jsonArray.getJSONObject(i).getJSONObject("formats")
                                    .getString(st)
                                response.results[i].html_url = html_url
                            }

                            if (st.length > 9 && st.substring(0, 10) == "text/plain") {
                                var plainText = jsonArray.getJSONObject(i).getJSONObject("formats")
                                    .getString(st)
                                response.results[i].text_url = plainText
                            }
                        }
                    }

                } else {
                    Toast.makeText(context, "Data not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}