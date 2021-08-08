package com.microsoft.fluentuidemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.microsoft.fluentui.bottomsheet.BottomSheetDialog
import com.microsoft.fluentui.listitem.ListItemDivider
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.listitem.ListItemView.Companion.DEFAULT_CUSTOM_VIEW_SIZE
import com.microsoft.fluentui.listitem.ListItemView.Companion.DEFAULT_LAYOUT_DENSITY
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.AvatarView
import com.microsoft.fluentuidemo.demos.BottomSheetActivity
import com.microsoft.fluentuidemo.demos.TabLayoutActivity
import com.microsoft.fluentuidemo.demos.list.IBaseListItem
import com.microsoft.fluentuidemo.demos.list.ListAdapter
import com.microsoft.fluentuidemo.demos.list.ListItem
import kotlinx.android.synthetic.main.activity_customers_list_item_view.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class CustomersList : DemoActivity() {
    private val listAdapter = ListAdapter(this)
    override val contentLayoutId: Int
        get() = R.layout.activity_customers_list_item_view
    override val contentNeedsScrollableContainer: Boolean
        get() = false
    private var bottomSheetDialog: BottomSheetDialog? = null

    val arrayCustomers: ArrayList<IBaseListItem> = ArrayList(5)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Müşteri Listesi"
        val httpAsync = "https://recep.space/abi/post/CorpList.ashx"
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                    }
                    is Result.Success -> {
                        val data = result.get()
                        requestcustomer(data)
                    }
                }
            }
        httpAsync.join()

    }

    override fun onDestroy() {
        super.onDestroy()
        bottomSheetDialog?.dismiss()
    }

    private fun requestcustomer(json: String) {
        val taleplerim: JSONArray = JSONObject("{\"Result\":$json}").getJSONArray("Result")
        for (i in 0 until taleplerim.length()) {
            val t: JSONObject = taleplerim.getJSONObject(i)
            val Name = t.getString("Name")
            val Adress = t.getString("Adress")
            val VergiDairesi = t.getString("VergiDairesi")
            val VergiNo = t.getString("VergiNo")
            var customer_id = t.getString("id")
            arrayCustomers.addAll(
                arrayListOf(
                    createCustomerList(
                        Name,
                        Adress,
                        VergiDairesi + " - " + VergiNo,
                        customView = viewAvatarCreate(
                            Logo = Name,
                            customer_id = customer_id,
                            customer_address = Adress,
                            customer_name = Name,
                            customer_tax_state = VergiDairesi,
                            customer_tax_id = VergiNo
                        ),
                        customViewSize = ListItemView.CustomViewSize.MEDIUM,
                        customAccessoryView = createEditBtn(),
                        addCustomAccessoryViewClick = true
                    )
                )
            )
        }

        listAdapter.listItems = createCustomerListSection(arrayCustomers)
        list_customers.adapter = listAdapter
        list_customers.addItemDecoration(ListItemDivider(this, DividerItemDecoration.VERTICAL))
        listAdapter.notifyDataSetChanged()
    }

    private fun createEditBtn(): ImageView {
        val iv = ImageView(this)
        val imgResId = R.drawable.ic_edit_24_filled
        iv.setImageResource(imgResId)
        iv.setOnClickListener {
            val intent = Intent(this, TabLayoutActivity::class.java)
            intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
            startActivity(intent)
        }
        return iv
    }

    private fun createCustomerListSection(items: ArrayList<IBaseListItem>): ArrayList<IBaseListItem> {
        val itemArray: ArrayList<IBaseListItem> = ArrayList(5)
        itemArray.addAll(items)
        itemArray.forEach {
            println(it)
        }
        return itemArray
    }

    private fun viewAvatarCreate(
        customer_tax_id: String = String(),
        customer_tax_state: String = String(),
        customer_address: String = String(),
        customer_id: String = String(),
        customer_name: String = String(),
        Logo: String = String()
    ): AvatarView {
        val avatarView = AvatarView(this)
        avatarView.avatarImageResourceId = -1
        avatarView.name = Logo
        avatarView.avatarSize = AvatarSize.LARGE
        avatarView.setOnClickListener {
            val fm = supportFragmentManager
            val dialogFragment = customerModal()
            val args = Bundle()
            args.putString("processType", "Güncelle")
            args.putString("customer_id", customer_id)
            args.putString("customer_name", customer_name)
            args.putString("customer_address", customer_address)
            args.putString("customer_tax_state", customer_tax_state)
            args.putString("customer_tax_id", customer_tax_id)
            dialogFragment.setArguments(args)
            dialogFragment.show(fm, "Sample Fragment")
        }
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


