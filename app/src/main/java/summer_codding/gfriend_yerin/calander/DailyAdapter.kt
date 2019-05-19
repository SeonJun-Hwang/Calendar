package summer_codding.gfriend_yerin.calander

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.item_pager.view.*
import summer_codding.gfriend_yerin.calander.Data.ScheduleVO
import kotlin.collections.ArrayList

class DailyAdapter(val context: Context, private val scheduleList: ArrayList<ScheduleVO>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun getCount(): Int {
        return scheduleList.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layout = LayoutInflater.from(context).inflate(R.layout.item_pager, container, false)

        val cur = scheduleList[position].date

        layout.pager_item_date.text = String.format("%04d/%02d/%02d", cur.year, cur.month, cur.day)
        layout.pager_item_contents.text = scheduleList[position].contents
        container.addView(layout)

        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}