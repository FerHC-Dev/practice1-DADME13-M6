package diplomado.practice1.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import diplomado.practice1.data.db.model.MovieEntity
import diplomado.utils.Constants

@Dao
interface MovieDao {
    //Create
    @Insert
    suspend fun insertMovie(movie: MovieEntity)

    @Insert
    suspend fun insertMovies(movies: MutableList<MovieEntity>)

    //Read
    @Query("SELECT * FROM ${Constants.DATABASE_MOVIE_TABLE}")
    suspend fun getAllMovies(): MutableList<MovieEntity>

    @Query("SELECT * FROM ${Constants.DATABASE_MOVIE_TABLE} WHERE movie_id = :movieId")
    suspend fun getMovieById(movieId: Long): MovieEntity?

    //Update
    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Update
    suspend fun updateMovies(movies: MutableList<MovieEntity>)

    //Delete
    @Delete
    suspend fun deleteMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovies(movies: MutableList<MovieEntity>)
}