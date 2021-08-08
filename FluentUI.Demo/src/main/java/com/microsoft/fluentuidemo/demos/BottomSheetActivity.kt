/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import com.microsoft.fluentui.bottomsheet.BottomSheet
import com.microsoft.fluentui.bottomsheet.BottomSheetDialog
import com.microsoft.fluentui.bottomsheet.BottomSheetItem
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.activity_demo_detail.*

class BottomSheetActivity : DemoActivity(), BottomSheetItem.OnClickListener {
    override val contentLayoutId: Int
        get() = R.layout.activity_bottom_sheet

    private var bottomSheetDialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        show_with_double_line_items_and_two_line_header_button.setOnClickListener {
            val bottomSheet = BottomSheet.newInstance(
                arrayListOf(
                    BottomSheetItem(
                        R.id.bottom_sheet_item_double_line_header_share,
                        R.drawable.ic_share_24_filled,
                        getString(R.string.bottom_sheet_item_double_line_header_share)
                    ),
                    BottomSheetItem(
                        R.id.bottom_sheet_item_double_line_header_move,
                        R.drawable.ic_folder_move_24_regular,
                        getString(R.string.bottom_sheet_item_double_line_header_move)
                    ),
                    BottomSheetItem(
                        R.id.bottom_sheet_item_double_line_header_delete,
                        R.drawable.ic_delete_24_regular,
                        getString(R.string.bottom_sheet_item_double_line_header_delete)
                    ),
                    BottomSheetItem(
                        R.id.bottom_sheet_item_double_line_header_info,
                        R.drawable.ic_info_24_regular,
                        getString(R.string.bottom_sheet_item_double_line_header_info),
                        useDivider = true
                    )
                ),
                BottomSheetItem(
                    imageId = R.drawable.ic_folder_24_regular,
                    title = getString(R.string.bottom_sheet_item_double_line_header),
                    subtitle = getString(R.string.bottom_sheet_item_double_line_header_subtitle)
                )
            )
            bottomSheet.show(supportFragmentManager, null)
        }

        // Dialog

    }

    override fun onDestroy() {
        super.onDestroy()
        bottomSheetDialog?.dismiss()
    }

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        when(item.id) {

            R.id.bottom_sheet_item_double_line_header_move -> showSnackbar(getString(R.string.bottom_sheet_item_double_line_header_move_toast))
            R.id.bottom_sheet_item_double_line_header_share -> showSnackbar(getString(R.string.bottom_sheet_item_double_line_header_share_toast))
            R.id.bottom_sheet_item_double_line_header_delete -> showSnackbar(getString(R.string.bottom_sheet_item_double_line_header_delete_toast))
            R.id.bottom_sheet_item_double_line_header_info -> showSnackbar(getString(R.string.bottom_sheet_item_double_line_header_info_toast))


        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(root_view, message).show()
    }
}