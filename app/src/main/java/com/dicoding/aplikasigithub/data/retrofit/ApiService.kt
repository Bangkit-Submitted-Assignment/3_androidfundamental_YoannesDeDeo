package com.dicoding.aplikasigithub.data.retrofit

import com.dicoding.aplikasigithub.data.response.DetailUserResponse
import com.dicoding.aplikasigithub.data.response.ItemsItem
import com.dicoding.aplikasigithub.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getListUsers(@Query("q") page: String): Call<UserResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(@Path("username") keyword: String): Call<DetailUserResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>

}