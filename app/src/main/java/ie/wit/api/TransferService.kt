package ie.wit.api

import com.google.gson.GsonBuilder
import ie.wit.models.TransferModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface TransferService {
    @GET("/donations")
    fun getall(): Call<List<TransferModel>>

    @GET("/donations/{id}")
    fun get(@Path("id") id: String): Call<TransferModel>

    @DELETE("/donations/{id}")
    fun delete(@Path("id") id: String): Call<TransferWrapper>

    @POST("/donations")
    fun post(@Body donation: TransferModel): Call<TransferWrapper>

    @PUT("/donations/{id}")
    fun put(@Path("id") id: String,
            @Body donation: TransferModel
    ): Call<TransferWrapper>

    companion object {

        val serviceURL = "https://donationweb-hdip-server.herokuapp.com"

        fun create() : TransferService {

            val gson = GsonBuilder().create()

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(serviceURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
            return retrofit.create(TransferService::class.java)
        }
    }
}