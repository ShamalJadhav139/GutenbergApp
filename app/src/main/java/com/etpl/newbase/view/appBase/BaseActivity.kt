package com.etpl.newbase.view.appBase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.etpl.newbase.callbacks.NetworkChangeReceiver
import com.etpl.newbase.constants.COMMON_RECEIVER
import com.etpl.newbase.constants.EVENT_IDENTIFIER
import java.util.*


open class BaseActivity : AppCompatActivity() {
    private var myEventListener: BroadcastReceiver? = null
    var networkChangeReceiver: NetworkChangeReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkChangeReceiver = NetworkChangeReceiver()
        myEventListener = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                onEventReceived(
                    intent.getStringExtra(EVENT_IDENTIFIER), intent.getBundleExtra(COMMON_RECEIVER)
                )
            }
        }
    }

    open fun onEventReceived(stringExtra: String?, bundleExtra: Bundle?) {
    }

    fun hideSoftKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            applicationContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        val networkState = networkInfo.state
        return networkState == NetworkInfo.State.CONNECTED || networkState == NetworkInfo.State.CONNECTING
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver)
        }
        if (myEventListener != null) {
            LocalBroadcastManager.getInstance(applicationContext)
                .unregisterReceiver(myEventListener!!)
        }
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(
            networkChangeReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
        LocalBroadcastManager.getInstance(applicationContext!!)
            .registerReceiver(myEventListener!!, IntentFilter(COMMON_RECEIVER))
    }

    override fun onDestroy() {
        super.onDestroy()
        /*if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver)
        }
        if (myEventListener != null) {
            unregisterReceiver(myEventListener)
        }*/
    }
}
