package summer_codding.gfriend_yerin.calander

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(){

    private val TAG : String = "MainActivity"
    private var context : Context? = null
    private var backPressTime = 0L
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        context = this
        
        initTabLayout()
        initViewPager()
        initFirstPage()

    }

    private fun initTabLayout() {
        main_tablayout.addTab(main_tablayout.newTab().setIcon(R.drawable.ic_monthly).setText(R.string.item_monthly))
        main_tablayout.addTab(main_tablayout.newTab().setIcon(R.drawable.ic_weekly).setText(R.string.item_weekly))
        main_tablayout.addTab(main_tablayout.newTab().setIcon(R.drawable.ic_daily).setText(R.string.item_daily))

        main_tablayout.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener{
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

        val fragmentList : MutableList<Fragment> = ArrayList()

        fragmentList.add(MonthlyFragment.newInstance())
        fragmentList.add(WeeklyFragment.newInstance())
        fragmentList.add(DailyFragment.newInstance())

        val viewPager = ViewPagerAdapter(fragmentList, supportFragmentManager)

        main_viewpager.adapter = viewPager

        main_viewpager.addOnPageChangeListener( TabLayout.TabLayoutOnPageChangeListener(main_tablayout))
    }

    private fun initFirstPage(){
        val pref = getSharedPreferences(BuildConfig.PREF_NAME, MODE_PRIVATE)
        val lastPage = pref.getInt(BuildConfig.PREF_FIELD_NAME, 0)

        main_viewpager.currentItem = lastPage
    }

    override fun onStop() {
        val pref = getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE);
        val editor = pref.edit()
        editor.putInt(BuildConfig.PREF_FIELD_NAME, main_viewpager.currentItem)
        editor.apply()

        super.onStop()
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressTime > 2000){
            backPressTime = System.currentTimeMillis()
            Toast.makeText(context, "한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show()
        }
        else{
            super.onBackPressed()
            finish()
        }
    }
}
