package tk.t0bi.tickets.data.repository.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tk.t0bi.tickets.data.repository.remote.events.RetrofitEventsRepository

private const val API_BASE_URL = "http://192.168.178.2:3000"

object RetrofitServiceLocator {

    val eventsRepository by lazy {
        retrofitClient.create(RetrofitEventsRepository::class.java)
    }

    private val retrofitClient by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API_BASE_URL)
            .build()
    }

}