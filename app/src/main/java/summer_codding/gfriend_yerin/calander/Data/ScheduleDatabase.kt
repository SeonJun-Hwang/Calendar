package summer_codding.gfriend_yerin.calander.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Schedule::class), version = 1)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract fun getSecheduleDAO() : ScheduleDao

    companion object {

        private var INSTANCE: ScheduleDatabase? = null

        fun getInstance(context: Context): ScheduleDatabase? {

            if(INSTANCE == null) {
                synchronized(ScheduleDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ScheduleDatabase::class.java,
                        "schedule.db")
                        .build()
                }
            }

            return INSTANCE
        }

    }

}