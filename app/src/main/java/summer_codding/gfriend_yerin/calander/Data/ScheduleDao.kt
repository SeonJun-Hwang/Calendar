package summer_codding.gfriend_yerin.calander.Data

import androidx.room.Insert
import androidx.room.Query

interface ScheduleDao {

    @Query("Select (contents) From Schedule Where year = (:year) and month = (:month) and day = (:day) and isDeleted = \'false\'")
    fun getData(year : Int, month : Int, day : Int) : ArrayList<String>

    @Query("Select * From Schedule")
    fun getAllData() : ArrayList<Schedule>

    @Insert
    fun pushData(schedule : Schedule)
}