package summer_codding.gfriend_yerin.calander.View

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_monthly.*
import kotlinx.android.synthetic.main.fragment_weekly.*
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
        Log.e("Monthly", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        monthly_calendar.apply {
            addDecorators(
                TodayDecorator(context.getDrawable(R.drawable.schedule_selector)),
                ScheduleDecorator(MainActivity.schedules, context.getDrawable(R.drawable.schedule_selector))
            )
            setDateSelected(CalendarDay.today(), true)
        }

        monthly_calendar.state().edit().apply {
            setMinimumDate(CalendarDay.from(Date().year - 1 + 1900, 1, 1))
            setMaximumDate(CalendarDay.from(Date().year + 1 + 1900, 12, 31))
        }.commit()


    }

}
