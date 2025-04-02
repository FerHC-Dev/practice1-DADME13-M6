package diplomado.practice1.application

import android.app.Application
import diplomado.practice1.data.MovieRepository
import diplomado.practice1.data.db.MovieDataBase

class MoviesDBApp: Application() {

    private val database by lazy {
        MovieDataBase.getDataBase(this@MoviesDBApp)
    }

    val repository by lazy {
        MovieRepository(database.movieDao())
    }

}