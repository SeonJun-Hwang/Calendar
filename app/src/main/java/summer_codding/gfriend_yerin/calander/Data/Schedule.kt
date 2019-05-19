package summer_codding.gfriend_yerin.calander.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
class Schedule (
    @PrimaryKey val id: Long,
    @NotNull val date : String,
    @NotNull val contents : String
    )