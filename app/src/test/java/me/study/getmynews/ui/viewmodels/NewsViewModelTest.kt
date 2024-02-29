package me.study.getmynews.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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

class NewsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val mockNewsRepository: NewsRepository = mockk()

    private val appStateObserver: Observer<DataState> = mockk(relaxed = true)
    private val appStateValues = mutableListOf<DataState>()

    private lateinit var viewModel: NewsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxed = true)

        justRun {
            appStateObserver.onChanged(capture(appStateValues))
        }

        coEvery {
            mockNewsRepository.getNewsData()
        } returns Result.failure(Throwable())

        viewModel = NewsViewModel(mockNewsRepository)

        viewModel.appState.observeForever(appStateObserver)
        appStateValues.clear()
    }

    @Test
    fun `getNewsData should change DataState to Success when NewsRepository has data`() = runTest {
        // Given
        coEvery {
            mockNewsRepository.getNewsData()
        } returns Result.success(listOf(Article()))

        // When
        viewModel.getNewsData()

        // Then
        assertThat(appStateValues).isEqualTo(listOf(DataState.LOADING, DataState.SUCCESS))
    }

    @Test
    fun `getNewsData should change DataState to Error when NewsRepository has error`() = runTest {
        // Given
        coEvery {
            mockNewsRepository.getNewsData()
        } returns Result.failure(Throwable())

        // When
        viewModel.getNewsData()

        // Then
        assertThat(appStateValues).isEqualTo(listOf(DataState.LOADING, DataState.ERROR))
    }

    @Test
    fun `getNewsData should update list when NewsRepository has data`() = runTest {
        // Given
        val movieList = listOf(Article())
        coEvery {
            mockNewsRepository.getNewsData()
        } returns Result.success(movieList)

        // When
        viewModel.getNewsData()

        // Then
        assertThat(viewModel.newsListLiveData.value).isEqualTo(movieList)
    }

    @Test
    fun `getNewsData should return failure result when NewsRepository has error`() = runTest {
        // Given
        coEvery {
            mockNewsRepository.getNewsData()
        } returns Result.failure(Throwable())

        // When
        viewModel.getNewsData()

        // Then
        assertThat(viewModel.newsListLiveData.value).isNull()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown(){
        Dispatchers.resetMain()
        viewModel.appState.removeObserver(appStateObserver)
        appStateValues.clear()
    }
}