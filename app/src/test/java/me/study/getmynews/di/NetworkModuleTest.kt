package me.study.getmynews.di

import com.google.common.truth.Truth.assertThat
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import me.study.getmynews.utils.Constants
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class NetworkModuleTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private lateinit var networkModule: NetworkModule
    private lateinit var mockRetrofit: Retrofit
    private val mockOkHttpClient: OkHttpClient = mockk()

    @Before
    fun setup() {
        networkModule = NetworkModule()
        mockRetrofit = networkModule.provideRetrofit(mockOkHttpClient)
    }

    @Test
    fun `provideRetrofit should provide Retrofit instance with correct configurations`() {
        // Given
        val retrofit = mockRetrofit

        // Then
        assertThat(retrofit.baseUrl().toString()).isEqualTo(Constants.NEWS_API_BASE_URL)
        assertThat(retrofit.callAdapterFactories()).isNotEmpty()
        assertThat(retrofit.converterFactories()).isNotEmpty()
    }

    @Test
    fun `provideOkHttpClient should provide OkHttpClient instance with correct configurations`() {
        // Given
        val okHttpClient =
            networkModule.provideOkHttpClient(networkModule.provideRequestIntercepter())

        // Then
        assertThat(okHttpClient.connectTimeoutMillis()).isEqualTo(TimeUnit.MINUTES.toMillis(1))
        assertThat(okHttpClient.interceptors().size).isEqualTo(2)
    }


    @Test
    fun `provideNewsService should provide NewsService instance`() {
        // Given
        val newsService = networkModule.provideNewsService(mockRetrofit)

        // Then
        assertThat(newsService).isNotNull()
    }

    @Test
    fun `provideRequestIntercepter should provide Interceptor instance`() {
        // Given
        val interceptor = networkModule.provideRequestIntercepter()

        // Then
        assertThat(interceptor).isNotNull()
    }
}