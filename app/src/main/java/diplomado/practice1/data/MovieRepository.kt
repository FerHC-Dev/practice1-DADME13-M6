package diplomado.practice1.data

import diplomado.practice1.data.db.MovieDao
import diplomado.practice1.data.db.model.MovieEntity

class MovieRepository(
    private val movieDao: MovieDao
) {

    suspend fun insertMovie(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    suspend fun getAllMovies(): MutableList<MovieEntity> {
        return movieDao.getAllMovies()
    }

    suspend fun getMovieById(movieId: Long): MovieEntity? {
        return movieDao.getMovieById(movieId)
    }

    suspend fun updateMovie(movie: MovieEntity) {
        movieDao.updateMovie(movie)
    }

    suspend fun deleteMovie(movie: MovieEntity) {
        movieDao.deleteMovie(movie)
    }


}