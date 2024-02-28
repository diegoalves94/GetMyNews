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

//class MovieListAdapter(
//    private val listener: OnMovieItemClickListener
//) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
//
//    private val moviesList: MutableList<Movie> = ArrayList()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//
//        return ViewHolder(
//            FragmentNewsItemBinding.inflate(
//                LayoutInflater.from(parent.context),
//                parent,
//                false
//            )
//        )
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val movie = moviesList[position]
//
//        holder.bindItem(movie)
//        holder.view.setOnClickListener {
//            listener.onMovieSelected(position)
//        }
//        holder.shareIcon.setOnClickListener {
//            listener.onShareClick(movie.id, position)
//        }
//    }
//
//    override fun getItemCount(): Int = moviesList.size
//
//    inner class ViewHolder(private val binding: FragmentMovieItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        val view: View = binding.root
//        val shareIcon: ImageView = binding.ivShare
//        fun bindItem(movie: Movie) {
//            binding.movie = movie
//            binding.executePendingBindings()
//        }
//    }
//
//    fun updateData(movieList: List<Movie>) {
//        moviesList.clear()
//        moviesList.addAll(movieList)
//        notifyDataSetChanged()
//    }
//
//}

