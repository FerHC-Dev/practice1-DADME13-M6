package diplomado.practice1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import diplomado.practice1.data.db.model.MovieEntity
import diplomado.practice1.databinding.MovieElementBinding

class MovieAdapter(
    private val onMovieClick: (MovieEntity) -> Unit,
): RecyclerView.Adapter<MovieViewHolder>() {

    private var movie: List<MovieEntity> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movie[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            onMovieClick(movie)
        }
    }

    override fun getItemCount(): Int = movie.size

    fun updateMovies(movies: List<MovieEntity>) {
        movie = movies
        notifyDataSetChanged()
    }

}