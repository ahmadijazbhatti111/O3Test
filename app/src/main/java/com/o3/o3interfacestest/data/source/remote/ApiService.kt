package com.o3.o3interfacestest.data.source.remote

import com.o3.o3interfacestest.data.source.model.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("?")
    suspend fun getSingleNameAge(@Query("name") userName: String): Response<UserDto>
}
