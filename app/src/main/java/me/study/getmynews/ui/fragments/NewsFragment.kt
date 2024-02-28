package me.study.getmynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.study.getmynews.R
import me.study.getmynews.databinding.FragmentNewsBinding
import me.study.getmynews.ui.adapters.NewsListAdapter
import me.study.getmynews.ui.adapters.OnNewsItemClickListener
import me.study.getmynews.ui.viewmodels.NewsViewModel
import me.study.getmynews.utils.Constants
import me.study.getmynews.utils.setToolbarTitle

@AndroidEntryPoint
class NewsFragment : Fragment(), OnNewsItemClickListener {

    private val viewModel by hiltNavGraphViewModels<NewsViewModel>(R.id.news_graph)
    private lateinit var adapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsBinding.inflate(inflater)
        val view = binding.root
        val newsList = binding.list

        setToolbarTitle(Constants.NEWS_API_NEWS_SOURCE)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = NewsListAdapter(this@NewsFragment)
        newsList.apply {
            this.adapter = this@NewsFragment.adapter
            this.layoutManager = LinearLayoutManager(context)
        }

        initObservers()

        return view
    }

    private fun initObservers() {
        viewModel.newsListLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.updateData(it)
            }
        }
    }

    override fun onNewsSelected(position: Int) {
        findNavController().navigate(
            NewsFragmentDirections.actionNewsFragmentToArticleFragment(
                viewModel.newsListLiveData.value?.get(position)?.articleId!!
            )
        )
    }

}