package summer_codding.gfriend_yerin.calander

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_listview.view.*
import summer_codding.gfriend_yerin.calander.Data.ScheduleVO

class WeeklyListAdapter(private val context: Context, private var item : ArrayList<ScheduleVO> ) : BaseAdapter() {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, rowView: View?, viewGroup: ViewGroup?): View {

        val rowView = LayoutInflater.from(context).inflate(R.layout.item_listview, viewGroup, false)

        rowView.list_item_date.text = item[position].date
        rowView.list_item_contents.text = item[position].contents

        return rowView
    }

    override fun getItem(position: Int): Any {
        return item[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return item.size
    }
}
