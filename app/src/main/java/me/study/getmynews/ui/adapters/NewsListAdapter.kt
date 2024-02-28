package me.study.getmynews.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.study.getmynews.data.local.entities.Article
import me.study.getmynews.databinding.FragmentNewsItemBinding

class NewsListAdapter(
    private val listener: OnNewsItemClickListener
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {
    private val newsList: MutableList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentNewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = newsList[position]

        holder.bindItem(article)
        holder.view.setOnClickListener {
            listener.onNewsSelected(position)
        }
    }

    override fun getItemCount(): Int = newsList.size

    inner class ViewHolder(private val binding: FragmentNewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val view: View = binding.root

        fun bindItem(article: Article) {
            binding.article = article
            binding.executePendingBindings()
        }
    }

    fun updateData(articleList: List<Article>) {
        newsList.clear()
        newsList.addAll(articleList)
        notifyDataSetChanged()
    }
}

interface OnNewsItemClickListener {
    fun onNewsSelected(position: Int)
}