package me.study.getmynews.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.study.getmynews.data.local.entities.Article
import me.study.getmynews.data.models.DataState
import me.study.getmynews.repository.NewsRepository
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private var newsRepository: NewsRepository
) : ViewModel() {

    val newsListLiveData: LiveData<List<Article>?>
        get() = _newsListLiveData
    private val _newsListLiveData = MutableLiveData<List<Article>?>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    init {
        getNewsData()
    }

    fun getNewsData() {
        _appState.postValue(DataState.LOADING)
        viewModelScope.launch {
            val movieList = newsRepository.getNewsData()
            movieList.fold(
                onSuccess = {
                    _newsListLiveData.value = it
                    _appState.value = DataState.SUCCESS
                },
                onFailure = {
                    _appState.value = DataState.ERROR
                }
            )
        }
    }
}