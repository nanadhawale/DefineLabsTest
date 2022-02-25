package com.definelabs.definelabstest.network

import com.definelabs.definelabstest.interfaces.RetrofitCallInterface
import com.definelabs.definelabstest.utils.ApiEndpoints
import com.definelabs.definelabstest.utils.ApiEndpoints.BASE_URL
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitIClient {
    var retrofit: Retrofit? = null


    fun getRetrofitClient(): RetrofitCallInterface {
        if (retrofit == null) {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            retrofit = Retrofit.Builder()
                .baseUrl(ApiEndpoints.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        }
        return  retrofit!!.create(RetrofitCallInterface::class.java)

    }
}