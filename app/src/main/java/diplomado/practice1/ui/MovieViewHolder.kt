package diplomado.practice1.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import diplomado.practice1.R
import diplomado.practice1.data.db.model.MovieEntity
import diplomado.practice1.databinding.MovieElementBinding

class MovieViewHolder(
    private val binding: MovieElementBinding,
    context: Context
): RecyclerView.ViewHolder(binding.root) {
    private val items = context.resources.getStringArray(R.array.spinner_items)
    fun bind(movie: MovieEntity) {
        binding.apply {
            tvTitle.text = movie.title
            tvGenre.text = movie.genre
            tvDirector.text = movie.director
            tvYear.text = String.format(movie.year.toString())
            ivIcon.setImageResource(
                when (movie.genre) {
                    items[1] -> R.drawable.accion
                    items[2] -> R.drawable.comedia
                    items[3] -> R.drawable.horror
                    items[4] -> R.drawable.misterio
                    items[5] -> R.drawable.romance
                    items[6] -> R.drawable.otro
                    else -> {
                        R.drawable.default_img
                    }
                }
            )
        }

    }

}