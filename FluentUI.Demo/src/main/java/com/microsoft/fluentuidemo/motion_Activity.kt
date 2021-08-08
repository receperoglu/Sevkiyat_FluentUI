package com.microsoft.fluentuidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.microsoft.fluentui.listitem.ListItemDivider
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentuidemo.databinding.ActivityMotionBinding
import com.microsoft.fluentuidemo.demos.list.IBaseListItem
import com.microsoft.fluentuidemo.demos.list.ListAdapter
import com.microsoft.fluentuidemo.demos.list.ListItem
import kotlinx.android.synthetic.main.modal_motion.*
import org.json.JSONArray
import org.json.JSONObject


class motion_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityMotionBinding
    val arrayCustomers: java.util.ArrayList<IBaseListItem> = java.util.ArrayList(5)
    var ArticelId: String = ""
    var processType: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArticelId = intent.getStringExtra("ArticelId")
        binding = ActivityMotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        requestMotion(listAdapter = ListAdapter(this))

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }


    private fun requestMotion(listAdapter: ListAdapter) {
        var json = ""
        val httpAsync =
            "https://recep.space/StartApi.ashx?platform=android&ProcessType=Motion&MotionType=All&OrderId=$ArticelId"
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
                        }
                        is Result.Success -> {
                            json = result.get()

                        }
                    }
                }

        httpAsync.join()
        val Orders: JSONArray = JSONObject(json).getJSONArray("Result")
        for (i in 0 until Orders.length()) {
            val t: JSONObject = Orders.getJSONObject(i)
            val CreatedDate = t.getString("CreatedDate")
            val Piece = t.getString("Piece")
            val Weight = t.getString("Weight")
            val Dimensions = t.getString("Dimensions")
            val Color = t.getString("Color")
            val WayBillId = t.getString("WayBillId")
            arrayCustomers.addAll(
                arrayListOf(
                    createMotionList(
                        Piece + " Ad/Mt " + Weight + " KG - ",
                        Dimensions + Color,
                        CreatedDate + " Tarihinde " + WayBillId + " Nolur irsaliye",
                        customViewSize = ListItemView.CustomViewSize.MEDIUM,
                        addCustomAccessoryViewClick = false
                     )
                )
            )
        }
        list_motion.addItemDecoration(ListItemDivider(this, DividerItemDecoration.VERTICAL))

        listAdapter.listItems = createCustomerListSection(arrayCustomers)
        list_motion.adapter = listAdapter

    }




    private fun createCustomerListSection(items: ArrayList<IBaseListItem>): ArrayList<IBaseListItem> {
        val itemArray: ArrayList<IBaseListItem> = ArrayList(5)
        itemArray.addAll(items)
        return itemArray
    }


    private fun createMotionList(
        title: String,
        subtitle: String = "",
        footer: String = "",
        customView: View? = null,
        customViewSize: ListItemView.CustomViewSize = ListItemView.DEFAULT_CUSTOM_VIEW_SIZE,
        customAccessoryView: View? = null,
        customSecondarySubtitleView: View? = null,
        addCustomAccessoryViewClick: Boolean = true,
        layoutDensity: ListItemView.LayoutDensity = ListItemView.DEFAULT_LAYOUT_DENSITY,
    ): ListItem {
        val item = ListItem(title)
        item.subtitle = subtitle
        item.footer = footer
        item.layoutDensity = layoutDensity
        item.customAccessoryView = customAccessoryView
        item.customView = customView
        item.customViewSize = customViewSize
        item.customSecondarySubtitleView = customSecondarySubtitleView



        return item
    }
}
