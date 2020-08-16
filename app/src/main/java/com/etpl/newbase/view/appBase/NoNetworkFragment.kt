package com.etpl.newbase.view.appBase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etpl.newbase.R
import com.etpl.newbase.constants.IS_NETWORK_AVAILABLE
import com.etpl.newbase.constants.NETWORK

class NoNetworkFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_network, container, false)
    }

    companion object {
        private var instance: NoNetworkFragment? = null
        fun getInstance(): NoNetworkFragment {
            if (instance == null) {
                instance = NoNetworkFragment()
            }
            return instance!!
        }
    }
}
