package org.msfox.batmanmoviesapp.adapter

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import org.msfox.batmanmoviesapp.AppCoroutineDispatchers
import org.msfox.batmanmoviesapp.R
import org.msfox.batmanmoviesapp.databinding.MovieItemBinding
import org.msfox.batmanmoviesapp.model.Movie

/**
 * A RecyclerView adapter for [Item List] class.
 */
class ItemListAdapter(
    appCoroutineDispatchers: AppCoroutineDispatchers,
    private val dataBindingComponent: DataBindingComponent,
    private val itemClickCallback: ((Movie) -> Unit)?
) : DataBoundListAdapter<Movie, MovieItemBinding>(
    appCoroutineDispatchers = appCoroutineDispatchers,
    diffCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imdbID == newItem.imdbID &&
            oldItem.title == newItem.title &&
            oldItem.poster == newItem.poster &&
            oldItem.type == newItem.type &&
            oldItem.year == newItem.year
        }
    }
) {

    override fun createBinding(parent: ViewGroup): MovieItemBinding {

        val binding = DataBindingUtil.inflate<MovieItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.movie_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener { _ ->
            binding.item.let {
                if(it != null) itemClickCallback?.invoke(it)
            }
        }

        return binding
    }

    override fun bind(binding: MovieItemBinding, item: Movie) {
        binding.item = item
    }
}
