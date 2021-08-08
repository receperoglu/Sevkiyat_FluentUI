package com.microsoft.fluentuidemo
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import android.view.View
import android.widget.ImageView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.AvatarView
import com.microsoft.fluentui.listitem.ListItemDivider
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.listitem.ListItemView.Companion.DEFAULT_CUSTOM_VIEW_SIZE
import com.microsoft.fluentui.listitem.ListItemView.Companion.DEFAULT_LAYOUT_DENSITY
import com.microsoft.fluentuidemo.demos.BottomSheetActivity
import com.microsoft.fluentuidemo.demos.list.IBaseListItem
import com.microsoft.fluentuidemo.demos.list.ListAdapter
import com.microsoft.fluentuidemo.demos.list.ListItem
import kotlinx.android.synthetic.main.activity_articels_list_item_view.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
class ArticelsList : DemoActivity() {
    val arrayCustomers: ArrayList<IBaseListItem> = ArrayList(5)
    override val contentLayoutId: Int
        get() = R.layout.activity_articels_list_item_view
    override val contentNeedsScrollableContainer: Boolean
        get() = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Sipariş Listesi"
        val httpAsync = "https://recep.space/StartApi.ashx?platform=android&ProcessType=Articels"
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                    }
                    is Result.Success -> {
                        val data = result.get()
                        requestcustomer(ListAdapter(this),data)
                    }
                }
            }
        httpAsync.join()
    }
    private fun requestcustomer(listAdapter: ListAdapter,  json: String) {
        val Articels: JSONArray = JSONObject(json).getJSONArray("Result")
        for (i in 0 until Articels.length()) {
            val t: JSONObject = Articels.getJSONObject(i)
            val ArticelName = t.getString("ArticelName")
            val CustomerName = t.getString("CustomerName")
             arrayCustomers.addAll(
                arrayListOf(
                    createCustomerList(
                        ArticelName,
                        CustomerName,
                        customView = viewAvatarCreate(
                            Logo = CustomerName,
                            Cid = t.getString("id"),
                            ArticelName=ArticelName
                        ),
                        customViewSize = ListItemView.CustomViewSize.MEDIUM,
                        customAccessoryView = createEditBtn(),
                        addCustomAccessoryViewClick = true
                    )
                )
            )
        }

        listAdapter.listItems =  createCustomerListSection(arrayCustomers)
        list_articel.adapter = listAdapter
        list_articel.addItemDecoration(ListItemDivider(this, DividerItemDecoration.VERTICAL))
        listAdapter.notifyDataSetChanged()
     }


    private fun createEditBtn(): ImageView {
        val iv = ImageView(this)
        val imgResId = R.drawable.ic_info_24_regular
        iv.setImageResource(imgResId)
        iv.setOnClickListener {
            val intent = Intent(this, BottomSheetActivity::class.java)
            intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
            startActivity(intent)
        }
        return iv
    }

    private fun createCustomerListSection(items: ArrayList<IBaseListItem>): ArrayList<IBaseListItem> {
        val itemArray: ArrayList<IBaseListItem> = ArrayList(5)
        itemArray.addAll(items)
        return itemArray
    }
    private fun viewAvatarCreate( Cid: String = String(),ArticelName:String=String(), Logo: String = String() ): AvatarView {
        val avatarView = AvatarView(this)
        avatarView.avatarImageResourceId = -1
        avatarView.avatarSize = AvatarSize.LARGE
        avatarView.setOnClickListener {
            val intent = Intent(this, ArticelDetail::class.java)
            intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
            intent.putExtra("ArticelId", Cid)
            intent.putExtra("ArticelName", ArticelName)

            startActivity(intent)
        }

            avatarView.name = Logo
        return avatarView
    }

    private fun createCustomerList(
        title: String,
        subtitle: String = "",
        footer: String = "",
        customView: View? = null,
        customViewSize: ListItemView.CustomViewSize = DEFAULT_CUSTOM_VIEW_SIZE,
        customAccessoryView: View? = null,
        customSecondarySubtitleView: View? = null,
        addCustomAccessoryViewClick: Boolean = true,
        layoutDensity: ListItemView.LayoutDensity = DEFAULT_LAYOUT_DENSITY,
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


