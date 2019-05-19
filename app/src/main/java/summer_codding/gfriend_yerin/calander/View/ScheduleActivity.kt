package summer_codding.gfriend_yerin.calander.View

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_schedule.*
import summer_codding.gfriend_yerin.calander.Data.Schedule
import summer_codding.gfriend_yerin.calander.Data.ScheduleDatabase
import summer_codding.gfriend_yerin.calander.R

class ScheduleActivity : AppCompatActivity() {

    companion object {
        private val TAG = "ScheduleActivity"
    }

    private lateinit var context: Context
    private var cancelDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        context = this

        val year = intent.getIntExtra("year", 1900)
        val month = intent.getIntExtra("month", 2)
        val day = intent.getIntExtra("day", 31)

        supportActionBar?.title = year.toString() + "년 " + month.toString() + "월 " + day.toString() + "일 일정 추가"

        schedule_register.setOnClickListener {
            val contents = schedule_contents.text.toString()
            if (contents.isBlank()) {
                AlertDialog.Builder(context)
                    .setTitle("확인")
                    .setMessage("일정을 입력해주세요")
                    .setPositiveButton("확인") { diface, _ -> diface.cancel() }
                    .show()
            } else {
                val date = String.format("%4d%02d%02d", year, month, day)

                val schedule = Schedule(System.nanoTime(), date, contents)
                MainActivity.schedules[CalendarDay.from(year, month, day)] = contents

                Log.e(TAG, "$date / $contents")

                val thread = Thread(Runnable {
                    ScheduleDatabase.getInstance(context)!!
                        .getScheduleDAO()
                        .pushData(schedule)

                    setResult(RESULT_OK)
                    finish()
                })
                thread.start()
            }

        }
        schedule_cancel.setOnClickListener { showCancelDialog() }
    }

    override fun onBackPressed() {
        showCancelDialog()
    }

    fun showCancelDialog() {
        if (cancelDialog == null) {
            cancelDialog = AlertDialog.Builder(this)
                .setTitle("확인")
                .setMessage("현재 작성한 내용이 지워집니다.\n계속하시겠습니까?")
                .setPositiveButton("예") { _, _ ->
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
                .setNegativeButton("취소") { di, _ ->
                    di.cancel()
                    cancelDialog = null
                }
                .create()
            cancelDialog!!.show()
        }
    }
}
