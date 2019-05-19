package summer_codding.gfriend_yerin.calander.View

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import summer_codding.gfriend_yerin.calander.BuildConfig
import summer_codding.gfriend_yerin.calander.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_monthly.*
import kotlinx.android.synthetic.main.fragment_weekly.*
import summer_codding.gfriend_yerin.calander.Data.ScheduleDatabase
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"
    private lateinit var context: Context
    private var backPressTime = 0L
    private val monthly: MonthlyFragment = MonthlyFragment()
    private val weekly: WeeklyFragment = WeeklyFragment()
    private val daily: DailyFragment = DailyFragment()

    companion object {
        var today = 0L
        private const val SCHEDULE_CODE: Int = 100
        val schedules: HashMap<CalendarDay, String> = HashMap()

        private val MONTH = 0
        private val WEEK = 1
        private val DAY = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this
        today = System.currentTimeMillis()

        initScheduleArray()
        initFloating()
        initTabLayout()
        initFirstPage()
    }

    private fun initScheduleArray() {
        val thread = Thread(Runnable {
            val dataList = ScheduleDatabase.getInstance(context)!!
                .getScheduleDAO().getAllData()

            // Arraylist to HashMap
            for (data in dataList) {
//                Log.e(TAG, data.date + " - " + data.contents)
                val toInt = data.date.toInt()

                // MONTH - DIV 10000 ( <yyyy>mmdd )
                // Week - DIV 100 % 100 ( (yyyy<mm>)dd )
                // Day - DIV % 100 ( yyyymm(dd))
                schedules[CalendarDay.from(toInt / 10000, (toInt / 100) % 100, toInt % 100)] = data.contents

            }
        })
        thread.start()
    }

    private fun initTabLayout() {
        main_tablayout.addTab(main_tablayout.newTab().setText(R.string.item_monthly))
        main_tablayout.addTab(main_tablayout.newTab().setText(R.string.item_weekly))
        main_tablayout.addTab(main_tablayout.newTab().setText(R.string.item_daily))

        main_tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val pos = tab?.position

                val transaction = supportFragmentManager.beginTransaction()

                when (pos) {
                    MONTH -> transaction.replace(R.id.main_frame_viewer, monthly).commit()
                    WEEK -> transaction.replace(R.id.main_frame_viewer, weekly).commit()
                    DAY -> transaction.replace(R.id.main_frame_viewer, daily).commit()
                }

            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

        })
    }

    private fun initFirstPage() {
        val pref = getSharedPreferences(BuildConfig.PREF_NAME, MODE_PRIVATE)
        val lastPage = pref.getInt(BuildConfig.PREF_FIELD_NAME, 0)

        Log.e(TAG, "Last Page $lastPage")

        main_tablayout.getTabAt(lastPage)?.select()

        val transaction = supportFragmentManager.beginTransaction()

        when (lastPage) {
            MONTH -> transaction.replace(R.id.main_frame_viewer, monthly).commit()
            WEEK -> transaction.replace(R.id.main_frame_viewer, weekly).commit()
            DAY -> transaction.replace(R.id.main_frame_viewer, daily).commit()
        }
    }

    private fun initFloating() {
        main_add_schedule.setOnClickListener {
            val pos = main_tablayout.selectedTabPosition
            var year = 0;
            var month = 0;
            var day = 0

            when (pos) {
                MONTH -> {
                    year = monthly.monthly_calendar.selectedDate!!.year
                    month = monthly.monthly_calendar.selectedDate!!.month
                    day = monthly.monthly_calendar.selectedDate!!.day
                }
                WEEK -> {
                    year = weekly.weekly_calendar.selectedDate!!.year
                    month = weekly.weekly_calendar.selectedDate!!.month
                    day = weekly.weekly_calendar.selectedDate!!.day
                }
                DAY -> {
                    val date = daily.getDisplayingDay()
                    year = date.year
                    month = date.month
                    day = date.day

                }
            }

            Log.e(TAG, String.format("%d - %d - %d", year, month, day))
            checkNRemove(year, month, day)

        }

    }

    override fun onStop() {
        val pref = getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt(BuildConfig.PREF_FIELD_NAME, main_tablayout.selectedTabPosition)
        editor.apply()

        Log.e(TAG, "Last Page ${main_tablayout.selectedTabPosition}")

        super.onStop()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressTime > 2000) {
            backPressTime = System.currentTimeMillis()
            Toast.makeText(context, "한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SCHEDULE_CODE) {
                when (main_tablayout.selectedTabPosition) {
                    MONTH -> monthly.monthly_calendar.refreshDrawableState()
                    WEEK -> weekly.weekly_calendar.refreshDrawableState()
                    DAY -> daily.refreshList()
                }
            }
        }
    }

    private fun checkNRemove(year: Int, month: Int, day: Int) {
        val inputDay = CalendarDay.from(year, month, day)

        if (schedules.contains(inputDay))
            AlertDialog.Builder(this)
                .setTitle("확인")
                .setMessage("일정은 하루에 한개만 등록 가능합니다.\n기존일정을 삭제하시겠습니까? ")
                .setPositiveButton("확인") { di, _ ->
                    val thread = Thread {
                        ScheduleDatabase.getInstance(context)!!
                            .getScheduleDAO().delete(String.format("%04d%02d%02d", year, month, day))

                        schedules.remove(inputDay)
                        startScheduleActivity(year, month, day)
                        di.dismiss()
                    }
                    thread.start()
                }
                .setNegativeButton("취소") { di, _ -> di.cancel() }
                .show()
        else
            startScheduleActivity(year, month, day)
    }

    private fun startScheduleActivity(year: Int, month: Int, day: Int) {

        val intent = Intent(this, ScheduleActivity::class.java)

        intent.putExtra("year", year)
        intent.putExtra("month", month)
        intent.putExtra("day", day)

        startActivityForResult(intent, SCHEDULE_CODE)
    }
}
