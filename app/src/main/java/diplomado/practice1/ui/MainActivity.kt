package diplomado.practice1.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import diplomado.practice1.R
import diplomado.practice1.application.MoviesDBApp
import diplomado.practice1.data.MovieRepository
import diplomado.practice1.data.db.model.MovieEntity
import diplomado.practice1.databinding.ActivityMainBinding
import diplomado.utils.Constants
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var movies: MutableList<MovieEntity>  = mutableListOf()

    private lateinit var repository: MovieRepository
    private lateinit var movieAadapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = (application as MoviesDBApp).repository

        movieAadapter = MovieAdapter{ selectedGame ->
            val dialog = MovieDialog(newMovie = false,
                movie = selectedGame,
                updateUI ={
                    updateUI()
                },
                message ={ text ->
                    message(text)
                })
            dialog.show(supportFragmentManager, Constants.DIALOG_TAG)
        }

        binding.apply {
            rvMovies.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMovies.adapter = movieAadapter
        }

        updateUI()

    }

    private fun updateUI(){
        //Con room, se puede omitir el hilo principal
        lifecycleScope.launch{
            //Obtenemos todos los juegos
            movies = repository.getAllMovies()
            binding.tvSinRegistros.visibility =
                if (movies.isEmpty()) View.VISIBLE else View.GONE

            movieAadapter.updateMovies(movies)
        }
    }


    fun click(view : View){
        //Mostramos el dialogo
        val dialog = MovieDialog(
            updateUI ={
                updateUI()
            },message ={ text ->
                message(text)
            })
        dialog.show(supportFragmentManager, Constants.DIALOG_TAG)
    }

    private fun message(text : String){
        //Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT)
            .setTextColor(getColor(R.color.white))
            .setBackgroundTint(getColor(R.color.black))
            .show()
    }

}