package data.remote.retrofit.builder

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import data.local.holder.RepositoriesHolder
import data.remote.http.interceptor.PreviewInterceptor
import data.remote.retrofit.api.CinderAPI
import data.remote.retrofit.api.GlanceApi
import data.remote.retrofit.api.KeystoneApi
import data.remote.retrofit.api.NeutronApi
import data.remote.retrofit.api.NovaApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalSerializationApi::class)
object OpenStackRetrofitBuilder {
    private lateinit var keystoneRetrofit: Retrofit
    lateinit var keyStoneApi: KeystoneApi

    private lateinit var cinderRetrofit: Retrofit
    lateinit var cinderApi: CinderAPI

    private lateinit var novaRetrofit: Retrofit
    lateinit var novaApi: NovaApi

    private lateinit var neutronRetrofit: Retrofit
    lateinit var neutronApi: NeutronApi

    private lateinit var glanceRetrofit: Retrofit
    lateinit var glanceApi: GlanceApi

    fun build() {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        keystoneRetrofit = Retrofit.Builder()
            .baseUrl(RepositoriesHolder.sharedPreferencesRepository.getKeystoneUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        keyStoneApi = keystoneRetrofit.create(KeystoneApi::class.java)

        cinderRetrofit = Retrofit.Builder()
        .baseUrl(RepositoriesHolder.sharedPreferencesRepository.getCinderUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        cinderApi = cinderRetrofit.create(CinderAPI::class.java)

        novaRetrofit = Retrofit.Builder()
            .baseUrl(RepositoriesHolder.sharedPreferencesRepository.getNovaRetrofitUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        novaApi = novaRetrofit.create(NovaApi::class.java)

        neutronRetrofit = Retrofit.Builder()
            .baseUrl(RepositoriesHolder.sharedPreferencesRepository.getNeutronRetrofitUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        neutronApi = neutronRetrofit.create(NeutronApi::class.java)

        glanceRetrofit = Retrofit.Builder()
            .baseUrl(RepositoriesHolder.sharedPreferencesRepository.getGlanceRetrofitUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        glanceApi = glanceRetrofit.create(GlanceApi::class.java)
    }
}