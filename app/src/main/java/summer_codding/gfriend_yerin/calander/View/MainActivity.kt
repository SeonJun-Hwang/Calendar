package summer_codding.gfriend_yerin.calander.View

import android.app.Activity
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
import summer_codding.gfriend_yerin.calander.ViewPagerAdapter
import com.facebook.stetho.Stetho.initializeWithDefaults
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_monthly.*
import kotlinx.android.synthetic.main.fragment_weekly.*
import summer_codding.gfriend_yerin.calander.Data.ScheduleDatabase
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"
    private lateinit var context: Context
    private var backPressTime = 0L

    companion object {
        var today = 0L
        private const val SCHEDULE_CODE: Int = 100
        val schedules: HashMap<CalendarDay, String> = HashMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Stetho
        initializeWithDefaults(this)

        context = this
        today = System.currentTimeMillis()

        initScheduleArray()
        initTabLayout()
        initViewPager()
        initFirstPage()
        initFloating()
    }

    private fun initScheduleArray() {

        val thread = Thread(Runnable {
            val dataList = ScheduleDatabase.getInstance(context)!!
                .getScheduleDAO().getAllData()

            // Arraylist to HashMap
            for (data in dataList) {
                Log.e(TAG, data.date + " - " + data.contents)
                val toInt = data.date.toInt()
                schedules[CalendarDay.from(toInt / 1000, (toInt / 100) % 100, toInt % 100)] = data.contents
            } } )
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
                if (tab != null)
                    main_viewpager.currentItem = tab.position
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

        })
    }

    private fun initViewPager() {
        val fragmentList: MutableList<Fragment> = ArrayList()

        fragmentList.add(MonthlyFragment())
        fragmentList.add(WeeklyFragment())
        fragmentList.add(DailyFragment())

        val viewPager = ViewPagerAdapter(fragmentList, supportFragmentManager)

        main_viewpager.adapter = viewPager

        main_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(main_tablayout))
    }

    private fun initFirstPage() {
        val pref = getSharedPreferences(BuildConfig.PREF_NAME, MODE_PRIVATE)
        val lastPage = pref.getInt(BuildConfig.PREF_FIELD_NAME, 0)

        main_viewpager.currentItem = lastPage
    }

    private fun initFloating() {
        main_add_schedule.setOnClickListener {

            val pos = main_tablayout.selectedTabPosition
            val intent = Intent(this, ScheduleActivity::class.java)

            if (pos == 0) {
                val fragment: Fragment = supportFragmentManager.fragments[0]
                intent.putExtra("year", fragment.monthly_calendar.selectedDate?.year)
                intent.putExtra("month", fragment.monthly_calendar.selectedDate?.month)
                intent.putExtra("day", fragment.monthly_calendar.selectedDate?.day)

            } else if (pos == 1) {
                val fragment: Fragment = supportFragmentManager.fragments[1]
                intent.putExtra("year", fragment.weekly_calendar.selectedDate?.year)
                intent.putExtra("month", fragment.weekly_calendar.selectedDate?.month)
                intent.putExtra("day", fragment.weekly_calendar.selectedDate?.day)
            }

            startActivityForResult(intent, SCHEDULE_CODE)
        }

    }

    override fun onStop() {
        val pref = getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt(BuildConfig.PREF_FIELD_NAME, main_viewpager.currentItem)
        editor.apply()

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

            }
        }
    }
}
