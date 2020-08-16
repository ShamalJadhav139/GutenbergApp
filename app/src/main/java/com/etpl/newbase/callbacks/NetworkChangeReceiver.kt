package com.etpl.newbase.callbacks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.etpl.newbase.constants.COMMON_RECEIVER
import com.etpl.newbase.constants.EVENT_IDENTIFIER
import com.etpl.newbase.constants.IS_NETWORK_AVAILABLE
import com.etpl.newbase.constants.NETWORK

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent!!.action == ConnectivityManager.CONNECTIVITY_ACTION){

            val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            if(isConnected){
                Log.d("Network", "Internet Available")
                sendNetworkEvent(context, true)
            }else{
                sendNetworkEvent(context, false)
                Log.d("Network", "No internet :(")
            }

        }
    }

    private fun sendNetworkEvent(context: Context?, status: Boolean) {
       val intent = Intent(COMMON_RECEIVER)
        val bundle = Bundle()
         bundle.putBoolean(IS_NETWORK_AVAILABLE,status)
         intent.putExtra(EVENT_IDENTIFIER, NETWORK)
         intent.putExtra(COMMON_RECEIVER,bundle)
        LocalBroadcastManager.getInstance(context!!).sendBroadcast(intent)
    }
}