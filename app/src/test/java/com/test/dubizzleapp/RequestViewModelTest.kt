package com.test.dubizzleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.test.dubizzleapp.model.DataResponse
import com.test.dubizzleapp.repository.ListDataRepository
import com.test.dubizzleapp.repository.Resource
import com.test.dubizzleapp.viewmodel.ListDataViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.HttpURLConnection

@RunWith(MockitoJUnitRunner::class)
class RequestViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ListDataViewModel

    private lateinit var apiHelper: ListDataRepository

    @Mock
    private lateinit var apiEmployeeObserver: Observer<Resource<DataResponse>?>

    private lateinit var mockWebServer: MockWebServer


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        viewModel = ListDataViewModel()
        viewModel.getData()?.observeForever(apiEmployeeObserver)

        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @Test
    fun `read sample success json file`() {
        val reader = MockResponseFileReader("success_response.json")
        assertNotNull(reader.content)
    }

    @Test
    fun `fetch data and check it returned`() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)
        // Act
        val actualResponse = viewModel.getData()
        // Assert
        assertEquals(
            actualResponse?.value?.status == Resource.Status.SUCCESS,
            actualResponse.toString().contains("results")
        )
    }

    @Test
    fun `fetch details and check response have items`() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(MockResponseFileReader("success_response.json").content)
        mockWebServer.enqueue(response)
        val mockResponse = response.getBody()?.readUtf8()
        // Act
        val actualResponse = viewModel.getData()
        // Assert
        assertEquals(
            mockResponse?.let { `parse mocked JSON response`(it) },
            actualResponse?.value?.data?.results?.size
        )
    }

    private fun `parse mocked JSON response`(mockResponse: String): Boolean {
        val reader = JSONObject(mockResponse)
        val resultsLength = reader.getJSONArray("results").length()
        return resultsLength > 1
    }

    @Test
    fun `fetch details for failed response 400 returned`() {
        // Assign
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(MockResponseFileReader("failed_response.json").content)
        mockWebServer.enqueue(response)
        // Act
        val actualResponse = viewModel.getData()
        // Assert
        assertEquals(
            response.toString().contains("400"),
            actualResponse?.value?.status == Resource.Status.ERROR
        )
    }

    @After
    fun tearDown() {
        viewModel.getData()?.removeObserver(apiEmployeeObserver)
        mockWebServer.shutdown()
    }
}