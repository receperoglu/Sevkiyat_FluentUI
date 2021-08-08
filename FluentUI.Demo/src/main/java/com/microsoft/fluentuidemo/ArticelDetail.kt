package com.microsoft.fluentuidemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.microsoft.fluentui.listitem.ListItemDivider
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.listitem.ListItemView.Companion.DEFAULT_CUSTOM_VIEW_SIZE
import com.microsoft.fluentui.listitem.ListItemView.Companion.DEFAULT_LAYOUT_DENSITY
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.AvatarView
import com.microsoft.fluentuidemo.demos.BottomSheetActivity
import com.microsoft.fluentuidemo.demos.list.IBaseListItem
import com.microsoft.fluentuidemo.demos.list.ListAdapter
import com.microsoft.fluentuidemo.demos.list.ListItem
import com.microsoft.fluentuidemo.util.Image
import com.microsoft.fluentuidemo.util.ImageDownloader
import com.microsoft.fluentuidemo.util.ImageTypeBig
import com.mindorks.placeholderview.PlaceHolderView
import kotlinx.android.synthetic.main.activity_articeldetail.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class ArticelDetail : DemoActivity() {
    private val adapterArticelDetail = ListAdapter(this)
    private var mGalleryView: PlaceHolderView? = null
    val arrayCustomers: ArrayList<IBaseListItem> = ArrayList(5)

    var ArticelId = String()
    override val contentLayoutId: Int
        get() = R.layout.activity_articeldetail

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ArticelId = intent.getStringExtra("ArticelId")
        title = intent.getStringExtra("ArticelName")
        mGalleryView = findViewById(R.id.galleryView)
        val httpAsync =
            "https://recep.space/StartApi.ashx?platform=android&ProcessType=Orders&ArticelId=$ArticelId"
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
        btn_motion.setOnClickListener {
            val intent = Intent(this, motion_Activity::class.java).apply {
                putExtra("processType", "Güncelle")
                putExtra("ArticelId",ArticelId)
            }
            startActivity(intent)
        }
    }

    private fun requestcustomer(json: String) {
        requestImages()
        val Orders: JSONArray = JSONObject(json).getJSONArray("Result")
        for (i in 0 until Orders.length()) {
            val t: JSONObject = Orders.getJSONObject(i)
            val OrderId = t.getString("id")
            val Piece = t.getString("Piece")
            val Dimensions = t.getString("Dimensions")
            val ProductTypeName = t.getString("ProductTypeName")
            val Color = t.getString("Color")
            val Metrics = t.getString("Metrics")
            arrayCustomers.addAll(
                arrayListOf(
                    createCustomerList(
                        Piece + " " + Metrics + " " + Dimensions,
                        Color,
                        ProductTypeName,
                        customView = viewAvatarCreate(
                            Logo = ProductTypeName,
                            Cid = OrderId
                        ),
                        customViewSize = ListItemView.CustomViewSize.MEDIUM,
                        customAccessoryView = createEditBtn(),
                        addCustomAccessoryViewClick = true
                    )
                )
            )
        }

        adapterArticelDetail.listItems = createCustomerListSection(arrayCustomers)
        list_articeldetail.adapter = adapterArticelDetail
        list_articeldetail.addItemDecoration(ListItemDivider(this, DividerItemDecoration.VERTICAL))
        adapterArticelDetail.notifyDataSetChanged()
    }

    private fun requestImages() {
        var data = ""
        val httpAsync =
            "https://recep.space/StartApi.ashx?platform=android&ProcessType=Pictures&ArticelId=$ArticelId"
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
                        }
                        is Result.Success -> {
                            data = result.get()

                        }
                    }
                }

        httpAsync.join()

        var PictureCount: Int = 0
        val imageList: List<Image> =
            ImageDownloader.loadImages(this, JSONObject(data).getJSONArray("Result"))
        for (i in 0 until if (imageList.size > 10) 10 else imageList.size) {
            var url = imageList[i].imageUrl
            if (url.contains(".jpg") || url.contains(".png") || url.contains(".jpeg") || url.contains(
                    ".gif"
                ) || url.contains(".bmp")
            ) {
                mGalleryView!!.addView(
                    ImageTypeBig(
                        applicationContext,
                        mGalleryView,
                        imageList[i].imageUrl
                    )
                )
                PictureCount++

            }

        }


        if (PictureCount == 0) run {
            mGalleryView!!.addView(
                ImageTypeBig(
                    applicationContext,
                    mGalleryView,
                    "https://ps.w.org/broken-link-checker/assets/icon-256x256.png"
                )
            )
        }


        adapterArticelDetail.listItems = createCustomerListSection(arrayCustomers)
        list_articeldetail.adapter = adapterArticelDetail
        list_articeldetail.addItemDecoration(ListItemDivider(this, DividerItemDecoration.VERTICAL))
        adapterArticelDetail.notifyDataSetChanged()
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

    private fun viewAvatarCreate(Cid: String = String(), Logo: String = String()): AvatarView {
        val avatarView = AvatarView(this)
        avatarView.avatarImageResourceId = -1
        avatarView.avatarSize = AvatarSize.LARGE
        avatarView.setOnClickListener {
            val intent = Intent(this, DemoListActivity::class.java).apply {
                putExtra("data", Cid)
            }
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





