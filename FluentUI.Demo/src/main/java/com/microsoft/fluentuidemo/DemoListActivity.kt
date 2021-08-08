package com.microsoft.fluentuidemo
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.microsoft.fluentuidemo.demos.AppBarLayoutActivity
import kotlinx.android.synthetic.main.activity_demo_list.*
import java.util.*
class DemoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_list)
        Initializer.init(application)
        try {
            val message = intent.getStringExtra("data")
            Toast.makeText(
                applicationContext,
                message, Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, ArticelsList::class.java)
            intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
            startActivity(intent)
        } catch (e: ArithmeticException) {
            print(e.message)
        }
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_photos -> {
                    val intent = Intent(this, CustomersList::class.java)
                    intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_news -> {
                    val intent = Intent(this, AppBarLayoutActivity::class.java)
                    intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_alerts -> {
                    val intent = Intent(this, ArticelsList::class.java)
                    intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }


}