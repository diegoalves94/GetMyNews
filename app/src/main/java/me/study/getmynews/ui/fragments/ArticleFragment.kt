package me.study.getmynews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import me.study.getmynews.R
import me.study.getmynews.databinding.FragmentArticleBinding
import me.study.getmynews.ui.viewmodels.ArticleViewModel

@AndroidEntryPoint
class ArticleFragment: Fragment() {

    private lateinit var binding: FragmentArticleBinding

    private val args: ArticleFragmentArgs by navArgs()

    private val viewModel by hiltNavGraphViewModels<ArticleViewModel>(R.id.news_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_article,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.articleViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArticleDetails()
    }

    private fun getArticleDetails() {
        viewModel.setArticleData(args.argArticleId)
    }
}