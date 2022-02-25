package com.definelabs.definelabstest.interfaces

import MatchesData
import com.definelabs.definelabstest.utils.ApiEndpoints
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitCallInterface {
    @GET(ApiEndpoints.SEARCH)
    fun getMatches(): Call<MatchesData>
}