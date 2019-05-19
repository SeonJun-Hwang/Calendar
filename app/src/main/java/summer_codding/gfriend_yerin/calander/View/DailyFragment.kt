package summer_codding.gfriend_yerin.calander.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_daily.*
import kotlinx.android.synthetic.main.fragment_daily.view.*
import summer_codding.gfriend_yerin.calander.DailyAdapter
import summer_codding.gfriend_yerin.calander.Data.ScheduleVO
import summer_codding.gfriend_yerin.calander.R

class DailyFragment : Fragment() {

    private lateinit var dailyAdapter: DailyAdapter
    private val dailyInfo: ArrayList<ScheduleVO> = ArrayList()

    companion object {
        private const val TAG = "TAG"
    }

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
        return inflater.inflate(R.layout.fragment_daily, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dailyAdapter = DailyAdapter(context!!, dailyInfo)

        refreshList()
        view.daily_viewpager.adapter = dailyAdapter
        view.daily_viewpager.currentItem = 499 // Total count / 2
    }

    fun getDisplayingDay(): CalendarDay {
        return dailyInfo[daily_viewpager.currentItem].date
    }

    fun refreshList() {
        dailyInfo.clear()

        val today = CalendarDay.today()

        for (i in 499 downTo -499) {
            val curDay = CalendarDay.from(today.date.minusDays(i.toLong()))

            val contents = MainActivity.schedules[curDay]
            if (contents != null)
                dailyInfo.add(
                    ScheduleVO(
                        curDay,
                        contents
                    )
                )
            else
                dailyInfo.add(
                    ScheduleVO(
                        curDay,
                        "일정이 없습니다."
                    )
                )
        }

        dailyAdapter.notifyDataSetChanged()
    }
}
