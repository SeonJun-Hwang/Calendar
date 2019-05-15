package summer_codding.gfriend_yerin.calander

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ViewPagerAdapter(var list: List<Fragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(pos: Int): Fragment {
        return list[pos]
    }

    override fun getCount(): Int {
        return list.count()
    }
}
