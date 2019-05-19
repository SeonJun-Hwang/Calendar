package summer_codding.gfriend_yerin.calander.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_daily.view.*
import summer_codding.gfriend_yerin.calander.DailyAdapter
import summer_codding.gfriend_yerin.calander.R

class DailyFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daily, container, false)

        return view
    }

}
