

package com.microsoft.fluentuidemo.demos.list

import android.text.TextUtils
import android.view.View
import com.microsoft.fluentui.listitem.ListItemView

interface IListItem : IBaseListItem {
      var cid:String
    var subtitle: String
    var footer: String

    var titleMaxLines: Int
    var subtitleMaxLines: Int
    var footerMaxLines: Int

    var titleTruncateAt: TextUtils.TruncateAt
    var subtitleTruncateAt: TextUtils.TruncateAt
    var footerTruncateAt: TextUtils.TruncateAt

    var customView: View?
    var customViewSize: ListItemView.CustomViewSize
    var customAccessoryView: View?
    var customSecondarySubtitleView: View?

    var layoutDensity: ListItemView.LayoutDensity
}

data class ListItem(override var title: String = "") : IListItem {
    override var cid: String = ""
    override var subtitle: String = ""
    override var footer: String = ""

    override var titleMaxLines: Int = ListItemView.DEFAULT_MAX_LINES
    override var subtitleMaxLines: Int = ListItemView.DEFAULT_MAX_LINES
    override var footerMaxLines: Int = ListItemView.DEFAULT_MAX_LINES

    override var titleTruncateAt: TextUtils.TruncateAt = ListItemView.DEFAULT_TRUNCATION
    override var subtitleTruncateAt: TextUtils.TruncateAt = ListItemView.DEFAULT_TRUNCATION
    override var footerTruncateAt: TextUtils.TruncateAt = ListItemView.DEFAULT_TRUNCATION

    override var customView: View? = null
    override var customViewSize: ListItemView.CustomViewSize = ListItemView.DEFAULT_CUSTOM_VIEW_SIZE
    override var customAccessoryView: View? = null
    override var customSecondarySubtitleView: View? = null

    override var layoutDensity: ListItemView.LayoutDensity = ListItemView.DEFAULT_LAYOUT_DENSITY
}