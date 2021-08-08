
package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.View
import com.microsoft.fluentui.popupmenu.PopupMenu
import com.microsoft.fluentui.popupmenu.PopupMenuItem
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_demo_detail.*
import kotlinx.android.synthetic.main.activity_popup_menu.*

class PopupMenuActivity : DemoActivity(), View.OnClickListener {
    companion object {
        private const val SINGLE_CHECKED_ITEM_ID = "singleCheckedItemId"
        private const val ALL_CHECKED_ITEMS = "allCheckedItems"
    }

    override val contentLayoutId: Int
        get() = R.layout.activity_popup_menu

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private var singleCheckedItemId: Int = -1
    private var allCheckedItems: ArrayList<PopupMenuItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        no_check.setOnClickListener(this)


        savedInstanceState?.let {
            singleCheckedItemId = it.getInt(SINGLE_CHECKED_ITEM_ID)
            allCheckedItems = it.getParcelableArrayList(ALL_CHECKED_ITEMS)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SINGLE_CHECKED_ITEM_ID, singleCheckedItemId)
        outState.putParcelableArrayList(ALL_CHECKED_ITEMS, allCheckedItems)
    }

    override fun onClick(anchorView: View) {
        when (anchorView) {
            no_check -> showPopupNoCheck(anchorView)

        }
    }

    private fun showPopupNoCheck(anchorView: View) {
        val popupMenuItems = arrayListOf(
            PopupMenuItem(R.id.popup_menu_item_share, getString(R.string.popup_menu_item_share)),
            PopupMenuItem(R.id.popup_menu_item_follow, getString(R.string.popup_menu_item_follow)),
            PopupMenuItem(R.id.popup_menu_item_invite_people, getString(R.string.popup_menu_item_invite_people)),
            PopupMenuItem(R.id.popup_menu_item_refresh_page, getString(R.string.popup_menu_item_refresh_page)),
            PopupMenuItem(R.id.popup_menu_item_open_in_browser, getString(R.string.popup_menu_item_open_in_browser)),
            PopupMenuItem(R.id.popup_menu_item_multiline, getString(R.string.popup_menu_item_multiline))
        )

        val onPopupMenuItemClickListener = object : PopupMenuItem.OnClickListener {
            override fun onPopupMenuItemClicked(popupMenuItem: PopupMenuItem) {
                showSnackbar(popupMenuItem)
            }
        }

        showPopupMenu(anchorView, popupMenuItems, PopupMenu.ItemCheckableBehavior.NONE, onPopupMenuItemClickListener)
    }

    private fun showPopupMenu(
        anchorView: View,
        items: ArrayList<PopupMenuItem>,
        itemCheckableBehavior: PopupMenu.ItemCheckableBehavior,
        onItemClickListener: PopupMenuItem.OnClickListener
    ) {
        val popupMenu = PopupMenu(this, anchorView, items, itemCheckableBehavior)
        popupMenu.onItemClickListener = onItemClickListener
        popupMenu.show()
    }

    private fun showSnackbar(popupMenuItem: PopupMenuItem) {
        Snackbar.make(root_view, "${getString(R.string.popup_menu_item_clicked)} ${popupMenuItem.title}").show()
    }
}