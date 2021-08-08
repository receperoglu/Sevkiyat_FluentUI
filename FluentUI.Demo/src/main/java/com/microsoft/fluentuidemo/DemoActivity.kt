package com.microsoft.fluentuidemo

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_demo_detail.*
import java.util.*

abstract class DemoActivity : AppCompatActivity() {
    companion object {
        const val DEMO_ID = "demo_id"
    }
    protected abstract val contentLayoutId: Int
        @LayoutRes get
    protected open val contentNeedsScrollableContainer: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val demoID = intent.getSerializableExtra(DEMO_ID) as UUID
        var demo: Demo?
        demo = DEMOS.find { it.id == demoID }
        title = demo?.title
         val container =
            if (contentNeedsScrollableContainer) demo_detail_scrollable_container else demo_detail_container
        layoutInflater.inflate(contentLayoutId, container, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, DemoListActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
