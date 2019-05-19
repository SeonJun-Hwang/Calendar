package summer_codding.gfriend_yerin.calander

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.daily_page.view.*
import java.util.*

class DailyAdapter(val context : Context, val scheduleList : ArrayList<String>) : RecyclerView.Adapter<DailyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.daily_page, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    override fun onBindViewHolder(viewholder: ViewHolder, position: Int) {

        val schedule = scheduleList[position]

        viewholder.bind(position + 1, schedule.ifEmpty { "일정이 없습니다." })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(day : Int, plan: String) {
            itemView.daily_date.text = day.toString() + "일"
            itemView.daily_schedule.text = plan
        }

    }
}