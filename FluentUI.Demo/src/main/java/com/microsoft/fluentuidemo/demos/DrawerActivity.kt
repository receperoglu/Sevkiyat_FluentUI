
package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.View
import com.microsoft.fluentui.drawer.Drawer
import com.microsoft.fluentui.drawer.DrawerDialog
import com.microsoft.fluentui.drawer.OnDrawerContentCreatedListener
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
 import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.demo_drawer_content.view.*

class DrawerActivity : DemoActivity(), OnDrawerContentCreatedListener {
    override val contentLayoutId: Int
        get() = R.layout.activity_drawer

    private var drawerDialogDemo: DrawerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        show_drawer_button.setOnClickListener {
            val drawerDemo = Drawer.newInstance(R.layout.demo_drawer_content)
            drawerDemo.show(supportFragmentManager, null)
        }


    }

    private fun clickListener(v:View) {

        drawerDialogDemo?.onDrawerContentCreatedListener = this


            drawerDialogDemo?.setContentView(R.layout.demo_drawer_content)
        drawerDialogDemo?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        drawerDialogDemo?.dismiss()
    }

    override fun onDrawerContentCreated(drawerContents: View) {

    }
}