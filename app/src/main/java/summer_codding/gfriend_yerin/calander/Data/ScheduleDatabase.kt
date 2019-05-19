package summer_codding.gfriend_yerin.calander.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Schedule::class], version = 1)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract fun getScheduleDAO(): ScheduleDao

    companion object {
        private var INSTANCE: ScheduleDatabase? = null

        fun getInstance(context: Context): ScheduleDatabase? {

            if (INSTANCE == null) {
                synchronized(ScheduleDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ScheduleDatabase::class.java,
                        "scheduel.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}