package diplomado.practice1.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplomado.utils.Constants

@Entity(tableName = Constants.DATABASE_MOVIE_TABLE)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.BD_MOVIE_ID)
    var id: Long = 0,
    @ColumnInfo(name = Constants.BD_MOVIE_TITLE)
    var title: String = "",
    @ColumnInfo(name = Constants.BD_MOVIE_GENRE)
    var genre: String = "",
    @ColumnInfo(name = Constants.BD_MOVIE_DIRECTOR)
    var director: String = "",
    @ColumnInfo(name = Constants.BD_MOVIE_YEAR)
    var year: Int = 0
)