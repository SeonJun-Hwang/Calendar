package summer_codding.gfriend_yerin.calander

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(var list: List<Fragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(pos: Int): Fragment {
        return list[pos]
    }

    override fun getCount(): Int {
        return list.count()
    }
}
