package me.study.getmynews.repository

import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import me.study.getmynews.data.local.datasource.NewsDataSourceImpl
import me.study.getmynews.data.local.entities.Article
import me.study.getmynews.data.remote.datasource.NewsDataSourceRemoteImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NewsRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    private var mockNewsDataSource: NewsDataSourceImpl = mockk()
    private var mockNewsSourceRemote: NewsDataSourceRemoteImpl = mockk()
    private lateinit var newsRepository: NewsRepository
    private val newsList = listOf(Article())

    @Before
    fun setup() {
        newsRepository = NewsRepository(mockNewsDataSource, mockNewsSourceRemote)

        coEvery {
            mockNewsDataSource.clearNewsData()
        } returns Unit

        coEvery {
            mockNewsDataSource.saveNewsData(any())
        } returns Unit
    }

    @Test
    fun `getNewsData should persist data and return local data when when remote call succeeds`() =
        runBlocking {
            // Given
            val apiResponse = Result.success(newsList)
            val dbResponse = Result.success(newsList)
            coEvery {
                mockNewsSourceRemote.getNewsData()
            } returns apiResponse

            coEvery {
                mockNewsDataSource.getNewsData()
            } returns dbResponse

            // When
            val result = newsRepository.getNewsData()

            // Then
            assertThat(result).isEqualTo(dbResponse)
            coVerifySequence {
                mockNewsSourceRemote.getNewsData()
                mockNewsDataSource.clearNewsData()
                mockNewsDataSource.saveNewsData(newsList)
                mockNewsDataSource.getNewsData()
            }
        }

    @Test
    fun `getNewsData should return local data when remote call fails`() = runBlocking {
        // Given
        val apiResponse = Result.failure<List<Article>?>(Throwable("Test Api Error"))
        val dbResponse = Result.success(newsList)

        coEvery {
            mockNewsSourceRemote.getNewsData()
        } returns apiResponse

        coEvery {
            mockNewsDataSource.getNewsData()
        } returns dbResponse

        // When
        val result = newsRepository.getNewsData()

        // Then
        coVerify(exactly = 0) {
            mockNewsDataSource.clearNewsData()
            mockNewsDataSource.saveNewsData(any())
        }

        coVerify(exactly = 1) {
            mockNewsDataSource.getNewsData()
        }

        assertThat(result).isEqualTo(dbResponse)
    }

    @Test
    fun `getNewsData should return local data when api source throws Exception`() = runBlocking {
        // Given
        val dbResponse = Result.success(newsList)

        coEvery {
            mockNewsSourceRemote.getNewsData()
        } throws Exception("TEST: Api Exception")

        coEvery {
            mockNewsDataSource.getNewsData()
        } returns dbResponse

        // When
        val result = newsRepository.getNewsData()

        // Then
        coVerify(exactly = 0) {
            mockNewsDataSource.clearNewsData()
            mockNewsDataSource.saveNewsData(any())
        }

        coVerify(exactly = 1) {
            mockNewsDataSource.getNewsData()
        }

        assertThat(result).isEqualTo(dbResponse)
    }

    @Test
    fun `getArticleData should return success result when data is available`() = runBlocking {
        // Given
        val articleId = 1
        val article = Article()
        coEvery { mockNewsDataSource.getArticleData(articleId) } returns Result.success(article)

        // When
        val result = newsRepository.getArticleData(articleId)

        // Then
        assertThat(result).isEqualTo(Result.success(article))
    }

    @Test
    fun `getArticleData should return failure result when data is not available`() = runBlocking {
        // Given
        val articleId = 1
        coEvery { mockNewsDataSource.getArticleData(articleId) } returns Result.failure(Throwable())

        // When
        val result = newsRepository.getArticleData(articleId)

        // Then
        assertThat(result.isFailure).isTrue()
    }
}
