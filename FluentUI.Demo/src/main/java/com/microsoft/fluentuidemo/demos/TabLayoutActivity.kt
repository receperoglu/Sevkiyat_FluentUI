package com.microsoft.fluentuidemo.demos
import android.os.Bundle
 import androidx.viewpager.widget.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.microsoft.fluentui.tablayout.TabLayout
import com.microsoft.fluentui.tablayout.TabLayout.TabType.*
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_tab_layout.*
class TabLayoutActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_tab_layout

    private var adapter: TabPagerAdapter? = null
    private lateinit var tabLayout: com.google.android.material.tabs.TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tabLayout = demo_tab_layout.tabLayout ?: return
        adapter = TabPagerAdapter()
         demo_tab_layout.visibility = View.VISIBLE
        tabLayout.removeAllTabs()
        tabLayout.setupWithViewPager(null)
        var tabType: TabLayout.TabType = SWITCH

        demo_tab_layout.tabType = tabType
        for (i in 0 until 3) {
            tabLayout.addTab(tabLayout.newTab().setText("Etiket  "+(i+1)))
        }
    }
    class TabPagerAdapter : PagerAdapter() {
        private var viewList: List<View> = ArrayList()
        private var viewTitleList: List<String> = ArrayList()

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val view = viewList[position]
            collection.addView(view)
            return view
        }
        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }
        override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj
        override fun getCount(): Int = viewList.size
        override fun getPageTitle(position: Int): CharSequence? = viewTitleList[position]
        fun setTitle(list: List<String>) {
            viewTitleList = list
            setTitle(list)
        }
    }
}