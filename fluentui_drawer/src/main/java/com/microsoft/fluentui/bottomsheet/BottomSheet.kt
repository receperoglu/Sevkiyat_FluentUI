
package com.microsoft.fluentui.bottomsheet

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class BottomSheet : AppCompatDialogFragment(), BottomSheetItem.OnClickListener {
    companion object {
        private const val ITEMS = "items"
        private const val HEADER_ITEM = "headerItem"


        @JvmStatic
        @JvmOverloads
        fun newInstance(items: ArrayList<BottomSheetItem>, headerItem: BottomSheetItem? = null): BottomSheet {
            val bundle = Bundle()
            bundle.putParcelableArrayList(ITEMS, items)
            bundle.putParcelable(HEADER_ITEM, headerItem)

            val bottomSheet = BottomSheet()
            bottomSheet.arguments = bundle
            return bottomSheet
        }
    }

    interface OnDismissListener {
        fun onBottomSheetDismiss()
    }

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var items: ArrayList<BottomSheetItem>
    private var headerItem: BottomSheetItem? = null
    private var clickedItem: BottomSheetItem? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = savedInstanceState ?: arguments
        items = bundle?.getParcelableArrayList(ITEMS) ?: arrayListOf()
        headerItem = bundle?.getParcelable(HEADER_ITEM)

        bottomSheetDialog = BottomSheetDialog(context!!, items, headerItem, theme)
        bottomSheetDialog.onItemClickListener = this

        return bottomSheetDialog
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(ITEMS, items)
        outState.putParcelable(HEADER_ITEM, headerItem)
    }

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        clickedItem = item
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        clickedItem?.let {
            (parentFragment as? BottomSheetItem.OnClickListener)?.onBottomSheetItemClick(it)
            (activity as? BottomSheetItem.OnClickListener)?.onBottomSheetItemClick(it)
            clickedItem = null
        }

        (parentFragment as? OnDismissListener)?.onBottomSheetDismiss()
        (activity as? OnDismissListener)?.onBottomSheetDismiss()
    }
}