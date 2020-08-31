package org.msfox.batmanmoviesapp.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.core.Is
import org.hamcrest.core.IsNull
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.msfox.batmanmoviesapp.model.SearchMovies
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MovieServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MovieService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
            .create(MovieService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getList() = runBlocking{
        enqueueResponse("list-page1.json")
        val response = service.search("batman", 1)
        val document = (response as NetworkResponse.Success).body

        val request = mockWebServer.takeRequest()
        Assert.assertThat(request.path, Is.`is`("/?apikey=3e974fca&s=batman&page=1"))

        //we have tested json format, here we just want to be sure that retrofit is
        //returning the json true
        Assert.assertThat<SearchMovies>(document, IsNull.notNullValue())
        Assert.assertThat(document.response, Is.`is`("True"))
        Assert.assertThat(document.movies.count(), Is.`is`(10))
    }


    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("api-response/$fileName")

        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}