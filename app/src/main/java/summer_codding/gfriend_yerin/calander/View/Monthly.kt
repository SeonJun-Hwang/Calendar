package summer_codding.gfriend_yerin.calander.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_monthly.*
import summer_codding.gfriend_yerin.calander.Decorator.ScheduleDecorator
import summer_codding.gfriend_yerin.calander.Decorator.TodayDecorator
import summer_codding.gfriend_yerin.calander.R
import java.util.*

class MonthlyFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_monthly, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        monthly_calendar.apply {
            addDecorators(
                TodayDecorator(),
                ScheduleDecorator(MainActivity.schedules, context.getDrawable(R.drawable.has_schedule))
            )
            setDateSelected(CalendarDay.today(), true)
        }

        monthly_calendar.state().edit().apply {
            setMinimumDate(CalendarDay.from(Date().year - 1 + 1900, 1, 1))
            setMaximumDate(CalendarDay.from(Date().year + 1 + 1900, 12, 31))
        }.commit()

    }

}
