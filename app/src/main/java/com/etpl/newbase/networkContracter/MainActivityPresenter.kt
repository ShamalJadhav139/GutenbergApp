package com.etpl.newbase.networkContracter

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import com.etpl.newbase.R
import com.etpl.newbase.callbacks.OkCancelCallback
import com.etpl.newbase.constants.ApiConstants
import com.etpl.newbase.utilities.AppDetails
import com.etpl.newbase.utilities.AppDetails.Companion.context
import com.etpl.newbase.utilities.AppSharedPreferences
import com.etpl.newbase.view.appBase.MainActivity
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.Thread.sleep

class MainActivityPresenter(val mView: MainContractor.View) : MainContractor.Presenter {
    private var progressDialog: Dialog? = null
    private var apiCallingCount=0
    override fun onClick(
        caseConstants: ApiConstants,
        parameters: Array<String>,
        context: Context,
        showProgressBar: Boolean?
    ) {
        if (showProgressBar!!) {
            showProgress(AppDetails.activity,"Please wait...")
        }
        val retrofit = ApiClient.client
        val requestInterface = retrofit.create(ApiInterface::class.java)
        val accessTokenCall: Call<JsonObject>

        when (caseConstants) {
            ApiConstants.getBooks -> {
                accessTokenCall = requestInterface.getBooks(parameters[0],parameters[1],parameters[2])
                callApiWithAccessToken(accessTokenCall, context, ApiConstants.getBooks)
            }
        }
    }

    private fun callApiWithAccessToken(
        accessTokenCall: Call<JsonObject>,
        context: Context,
        view: ApiConstants
    ) {
        if (isNetworkAvailable(context)) {
            accessTokenCall.enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    apiCallingCount --
                    if (progressDialog != null && progressDialog!!.isShowing)
                        try {
                            if(apiCallingCount == 0) {
                                progressDialog!!.dismiss()
                            }
                        } catch (e: Exception) {
                            Log.e(ContentValues.TAG, "onResponse: $e")
                        }

                    if (response.code() != null) {

                        when (response.code()) {
                            200 -> {
                                mView.setViewData((response.body()!!.toString()), view)
                            }
                            403 -> {
                                showErrorDialog(
                                    response.body()!!.get("message").asString,
                                    context
                                )
                            }
                            500 -> {
                                showErrorDialog(
                                    context!!.getString(R.string.server_not_responding),
                                    context
                                )
                            }
                            else -> {
                                mView.setViewData((response.body()!!.toString()), view)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    try {
                        apiCallingCount --
                        Log.e("count",apiCallingCount.toString())
                        if (progressDialog != null && progressDialog!!.isShowing)
                            if(apiCallingCount == 0) {
                                progressDialog!!.dismiss()
                            }
                        showErrorDialog(
                            context!!.getString(R.string.server_not_responding),
                            context
                        )
                    } catch (e: Exception) {
                        Log.e(ContentValues.TAG, "onFailure: $e")
                    }
                    t.printStackTrace()
                }
            })
        } else {
            apiCallingCount --
            if (progressDialog!!.isShowing)
                try {
                    progressDialog!!.dismiss()
                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, "onResponse: $e")

                }

        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
            ConnectivityManager.TYPE_MOBILE
        ).state == NetworkInfo.State.CONNECTING || connectivityManager.getNetworkInfo(
            ConnectivityManager.TYPE_WIFI
        ).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
            ConnectivityManager.TYPE_WIFI
        ).state == NetworkInfo.State.CONNECTING
    }

    private fun showErrorDialog(msg: String, context: Context) {
        okCancelDialog(msg,
            "Ok",
            "",
            object : OkCancelCallback {
                override fun onOkClick() {
                    AppSharedPreferences.clearUserData()
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }

                override fun onCancelClick() {
                }
            })
    }

    private fun okCancelDialog(
        message: String,
        okText: String,
        cancelText: String,
        okCancel: OkCancelCallback
    ) {
        val alertDialog = android.app.AlertDialog.Builder(AppDetails.activity).create()
        val view = View.inflate(context, R.layout.dialog_ok_cancel_newtwork, null)
        alertDialog.setView(view)
        alertDialog.setCancelable(false)
        val msg = view.findViewById<TextView>(R.id.msg)
        msg.text = message
        val sureBtn = view.findViewById<TextView>(R.id.sureBtn)
        sureBtn.text = okText
        val cancelBtn = view.findViewById<TextView>(R.id.cancelBtn)
        cancelBtn.text = cancelText
        val title = view.findViewById<TextView>(R.id.title)
        title.visibility = View.GONE

        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        sureBtn.setOnClickListener { v ->
            okCancel.onOkClick()
            alertDialog.dismiss()
        }
        cancelBtn.setOnClickListener { v ->
            okCancel.onCancelClick()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    private fun  showProgress(m_Context: Context?, message: String?) {
        if(progressDialog!=null && progressDialog!!.isShowing){
            apiCallingCount++
            Log.e("In progress count",apiCallingCount.toString())
            return
        }else{
            apiCallingCount++
        }
        progressDialog = Dialog(m_Context!!)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(R.layout.progress_bar_item)
       var mProgressBar = progressDialog!!.findViewById<View>(R.id.progress_bar) as ProgressBar
        message.let {
            val progressText = progressDialog!!.findViewById<View>(R.id.progress_text) as TextView
            progressText.text = "" + message
            progressText.visibility = View.VISIBLE
        }
        mProgressBar!!.visibility = View.VISIBLE
        mProgressBar!!.isIndeterminate = true
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.show()
    }
}