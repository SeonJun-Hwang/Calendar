package summer_codding.gfriend_yerin.calander.Decorator


import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import android.graphics.drawable.Drawable

class ScheduleDecorator(
    val dates: HashMap<CalendarDay, String>,
    val drawable: Drawable
) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.setSelectionDrawable(drawable)
        //view.addSpan(new DotSpan(5, color)); // 날자밑에 점
    }
}