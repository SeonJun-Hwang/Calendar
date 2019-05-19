package summer_codding.gfriend_yerin.calander.Data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScheduleDao {

    @Query("Select (contents) From Schedule Where date = (:date)")
    fun getData(date : String) : List<String>

    @Query("Select * From Schedule")
    fun getAllData() : List<Schedule>

    @Insert
    fun pushData(schedule : Schedule)
}