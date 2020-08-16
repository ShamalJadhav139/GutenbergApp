package com.etpl.newbase.networkContracter

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @GET("books")
    fun getBooks(
        @Query("topic")topic:String,
        @Query("mime_type")mime_type:String,
        @Query("page")page:String
    ): Call<JsonObject>
}
