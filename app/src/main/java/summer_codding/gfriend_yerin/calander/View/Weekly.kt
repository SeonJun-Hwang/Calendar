package summer_codding.gfriend_yerin.calander.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_weekly.*
import summer_codding.gfriend_yerin.calander.Decorator.ScheduleDecorator
import summer_codding.gfriend_yerin.calander.Decorator.TodayDecorator
import summer_codding.gfriend_yerin.calander.R
import java.util.*

class WeeklyFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weekly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        weekly_calendar.apply {
            addDecorators(
                TodayDecorator(),
                ScheduleDecorator(MainActivity.schedules, context.getDrawable(R.drawable.has_schedule))
            )
            setDateSelected(CalendarDay.today(), true)
        }

        weekly_calendar.state().edit()
            .setMinimumDate(CalendarDay.from(Date().year - 1 + 1900, 1, 1))
            .setMaximumDate(CalendarDay.from(Date().year + 1 + 1900, 12, 31))
            .commit()
    }

}
