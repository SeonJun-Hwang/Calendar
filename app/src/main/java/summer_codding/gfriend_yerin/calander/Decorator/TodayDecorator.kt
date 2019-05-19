package summer_codding.gfriend_yerin.calander.Decorator

import android.graphics.Color
import android.graphics.Typeface
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*

class TodayDecorator : DayViewDecorator {

    private val calendar = Calendar.getInstance()

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == CalendarDay.today()
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(StyleSpan(Typeface.BOLD))
        view.addSpan(RelativeSizeSpan(1.2f))
        view.addSpan(ForegroundColorSpan(Color.MAGENTA))
    }
}