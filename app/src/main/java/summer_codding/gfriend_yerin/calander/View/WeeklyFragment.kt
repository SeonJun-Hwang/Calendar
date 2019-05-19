package summer_codding.gfriend_yerin.calander.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_weekly.*
import org.threeten.bp.LocalDate
import summer_codding.gfriend_yerin.calander.Data.ScheduleVO
import summer_codding.gfriend_yerin.calander.Decorator.ScheduleDecorator
import summer_codding.gfriend_yerin.calander.Decorator.TodayDecorator
import summer_codding.gfriend_yerin.calander.R
import summer_codding.gfriend_yerin.calander.WeeklyListAdapter
import java.util.*
import kotlin.collections.ArrayList

class WeeklyFragment : Fragment() {

    companion object {
        private const val TAG = "WeeklyFragment"
    }

    private var items: ArrayList<ScheduleVO> = ArrayList()
    private lateinit var listAdapter: WeeklyListAdapter

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

        initCalendar()
        initListView()
    }

    private fun initCalendar() {
        weekly_calendar.apply {
            addDecorators(
                TodayDecorator(context.getDrawable(R.drawable.schedule_selector)),
                ScheduleDecorator(MainActivity.schedules, context.getDrawable(R.drawable.schedule_selector))
            )
            setDateSelected(CalendarDay.today(), true)
        }

        weekly_calendar.state().edit()
            .setMinimumDate(CalendarDay.from(Date().year - 1 + 1900, 1, 1))
            .setMaximumDate(CalendarDay.from(Date().year + 1 + 1900, 12, 31))
            .commit()

        weekly_calendar.setOnMonthChangedListener { _, day ->
            refreshListView(day)
        }
    }

    private fun initListView() {
        listAdapter = WeeklyListAdapter(context!!, items)
        weekly_schedule_list.adapter = listAdapter

        // Init listview
        refreshListView(findFirstDateForInit(CalendarDay.today()))
    }

    private fun findFirstDateForInit(day: CalendarDay): CalendarDay {
        val firstDay = Calendar.getInstance()
        return CalendarDay.from(day.year, day.month, day.day - firstDay.get(Calendar.DAY_OF_WEEK) + 1)
    }

    private fun refreshListView(startDay: CalendarDay) {
        items.clear()
        for (i in 0..7) {
            val date = LocalDate.of(startDay.year, startDay.month, startDay.day).plusDays(i.toLong())
            val checkDay = CalendarDay.from(date)

            val content = MainActivity.schedules[checkDay]
            if (content != null) {
                items.add(ScheduleVO(checkDay, content))
            }
        }

        listAdapter.notifyDataSetChanged()
    }

}
