package viacheslav.chugunov.network.repository

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import viacheslav.chugunov.core.util.AppSecrets
import viacheslav.chugunov.core.util.AsyncResource
import viacheslav.chugunov.core.util.CoroutineDispatchers
import viacheslav.chugunov.core.util.NetworkException
import viacheslav.chugunov.network.mapper.GiphyPagingResponseDtoToPagingGifsResultMapper
import viacheslav.chugunov.network.mapper.ThrowableToNetworkExceptionMapper
import viacheslav.chugunov.network.mock.MockGiphyApi

class DefaultGifsNetworkRepositoryTest {

    private lateinit var giphyApi: MockGiphyApi
    private lateinit var repository: DefaultGifsNetworkRepository

    @Before
    fun setup() {
        giphyApi = MockGiphyApi()
        repository = DefaultGifsNetworkRepository(
            giphyApi = giphyApi,
            coroutineDispatchers = CoroutineDispatchers.Test(),
            giphyPagingResponseDtoToPagingGifsResultMapper = GiphyPagingResponseDtoToPagingGifsResultMapper.Default(),
            throwableToNetworkExceptionMapper = ThrowableToNetworkExceptionMapper.Default(),
            appSecrets = AppSecrets.Default(
                giphyApiKey = ""
            )
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `if the limit argument in the trending method is zero then an exception should be thrown`(): Unit = runBlocking {
        repository.trending(limit = 0, offset = 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `if the limit argument in the trending method is negative then an exception should be thrown`(): Unit = runBlocking {
        repository.trending(limit = -1, offset = 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `if the offset argument in the trending method is negative an exception should be thrown`(): Unit = runBlocking {
        repository.trending(limit = 10, offset = -1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `if the offset argument in the trending method is greater than 499 an exception should be thrown`(): Unit = runBlocking {
        repository.trending(limit = 10, offset = 500)
    }

    @Test
    fun `the trending method sends a request to the current feed`(): Unit = runBlocking {
        repository.trending(limit = 20, offset = 20)
        Assert.assertEquals(1, giphyApi.tendingCalledCount)
    }

    @Test
    fun `if all arguments are correct in the trending method then any exception should be converted to an asynchronous result with network exception`(): Unit = runBlocking {
        giphyApi.throwExceptionOnCallTrending = Exception()
        val result = repository.trending(limit = 20, offset = 20)
        val error = (result as? AsyncResource.Failure)?.error
        Assert.assertTrue(error is NetworkException)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `if the query argument in the search method is blank an exception should be thrown`(): Unit = runBlocking {
        repository.search(query = "   ", limit = 10, offset = 10)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `if the limit argument in the search method is zero then an exception should be thrown`(): Unit = runBlocking {
        repository.search(query = "query", limit = 0, offset = 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `if the limit argument in the search method is negative then an exception should be thrown`(): Unit = runBlocking {
        repository.search(query = "query", limit = -1, offset = 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `if the offset argument in the search method is negative an exception should be thrown`(): Unit = runBlocking {
        repository.search(query = "query", limit = 10, offset = -1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `if the offset argument in the trending method is greater than 4999 an exception should be thrown`(): Unit = runBlocking {
        repository.search(query = "query", limit = 10, offset = 5000)
    }

    @Test
    fun `the search method sends a request for gifs for the specified query`(): Unit = runBlocking {
        repository.search(query = "query", limit = 20, offset = 20)
        Assert.assertEquals(1, giphyApi.searchCalledCount)
    }

    @Test
    fun `the query argument in the find method must be processed without spaces at the beginning and end`(): Unit = runBlocking {
        repository.search(query = "   query   ", limit = 20, offset = 20)
        Assert.assertEquals("query", giphyApi.lastProcessedSearchQuery)
    }

    @Test
    fun `if all arguments are correct in the search method then any exception should be converted to an asynchronous result with network exception`(): Unit = runBlocking {
        giphyApi.throwExceptionOnCallSearch = Exception()
        val result = repository.search(query = "query", limit = 20, offset = 20)
        val error = (result as? AsyncResource.Failure)?.error
        Assert.assertTrue(error is NetworkException)
    }

}