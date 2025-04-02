package diplomado.practice1.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import diplomado.practice1.data.db.model.MovieEntity
import diplomado.utils.Constants

@Database(entities = [MovieEntity::class]
    , version = 1
    , exportSchema = false)
abstract class MovieDataBase: RoomDatabase() {

    //DAO
    abstract fun movieDao(): MovieDao



    companion object{
        @Volatile
        private var INSTANCE: MovieDataBase? = null

        fun getDataBase(context: Context): MovieDataBase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDataBase::class.java,
                    Constants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}