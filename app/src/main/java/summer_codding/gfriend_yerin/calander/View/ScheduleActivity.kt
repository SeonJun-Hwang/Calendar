package summer_codding.gfriend_yerin.calander.View

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_schedule.*
import summer_codding.gfriend_yerin.calander.Data.Schedule
import summer_codding.gfriend_yerin.calander.Data.ScheduleDatabase
import summer_codding.gfriend_yerin.calander.R

class ScheduleActivity : AppCompatActivity() {

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        context = this

        val year = intent.getIntExtra("year", 2019)
        val month = intent.getIntExtra("month", 5)
        val day = intent.getIntExtra("day", 19)

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

                val schedule = Schedule(System.nanoTime(), String.format("%4d%2d%2d", year, month, day), contents)

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
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("확인")
            .setMessage("현재 작성한 내용이 지워집니다.\n계속하시겠습니까?")
            .setPositiveButton("예") { _, _ ->
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
            .setNegativeButton("취소") { di, _ -> di.cancel() }
            .show()
    }
}
