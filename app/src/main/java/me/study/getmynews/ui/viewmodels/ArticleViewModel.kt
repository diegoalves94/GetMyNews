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
class ArticleViewModel @Inject constructor(
    private var newsRepository: NewsRepository
) : ViewModel(){

    val articleLiveData: LiveData<Article?>
        get() = _articleLiveData
    private val _articleLiveData = MutableLiveData<Article?>()

    val appState: LiveData<DataState>
        get() = _appState
    private val _appState = MutableLiveData<DataState>()

    fun setArticleData(id: Int){
        _appState.postValue(DataState.LOADING)

        viewModelScope.launch {
            val article = newsRepository.getArticleData(id)
            article.fold(
                onSuccess = {
                    _articleLiveData.postValue(it)
                    _appState.postValue(DataState.SUCCESS)
                },
                onFailure = {
                    _appState.postValue(DataState.ERROR)
                }
            )
        }
    }
}