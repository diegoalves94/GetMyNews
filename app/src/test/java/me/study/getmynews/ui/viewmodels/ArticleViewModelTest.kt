package me.study.getmynews.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import me.study.getmynews.data.local.entities.Article
import me.study.getmynews.data.models.DataState
import me.study.getmynews.repository.NewsRepository
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ArticleViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val articleId = 1
    private val mockNewsRepository: NewsRepository = mockk()
    private val appStateObserver: Observer<DataState> = mockk(relaxed = true)
    private val appStateValues = mutableListOf<DataState>()

    private lateinit var viewModel: ArticleViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxed = true)

        justRun {
            appStateObserver.onChanged(capture(appStateValues))
        }

        viewModel = ArticleViewModel(mockNewsRepository)

        viewModel.appState.observeForever(appStateObserver)
        appStateValues.clear()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.appState.removeObserver(appStateObserver)
        appStateValues.clear()
    }

    @Test
    fun `setArticleData should change DataState to Success when NewsRepository has data`() = runTest {
        // Given
        coEvery {
            mockNewsRepository.getArticleData(articleId)
        } returns Result.success(Article())

        viewModel.setArticleData(1)

        assertThat(appStateValues).isEqualTo(listOf(DataState.LOADING, DataState.SUCCESS))
    }

    @Test
    fun `setArticleData should change DataState to Error when NewsRepository has error`() = runTest {
        // Given
        coEvery {
            mockNewsRepository.getArticleData(articleId)
        } returns Result.failure(Throwable())

        viewModel.setArticleData(1)

        assertThat(appStateValues).isEqualTo(listOf(DataState.LOADING, DataState.ERROR))
    }


    @Test
    fun `setArticleData should update Article LiveData on Success`() = runTest {
        // Given
        val article = Article()
        coEvery {
            mockNewsRepository.getArticleData(articleId)
        } returns Result.success(Article())

        // When
        viewModel.setArticleData(articleId)

        // Then
        coVerify {
            mockNewsRepository.getArticleData(articleId)
        }
        assertThat(viewModel.articleLiveData.value).isEqualTo(article)
    }

    @Test
    fun `setArticleData should just return DataState Error and Article LiveData Null on failure`() =
        runTest {
            // Given
            coEvery {
                mockNewsRepository.getArticleData(articleId)
            } returns Result.failure(Throwable())

            // When
            viewModel.setArticleData(articleId)

            // Then
            coVerify {
                mockNewsRepository.getArticleData(articleId)
            }
            assertThat(viewModel.appState.value).isEqualTo(DataState.ERROR)
            assertThat(viewModel.articleLiveData.value).isNull()
        }

}