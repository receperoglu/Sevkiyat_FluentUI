
package com.microsoft.fluentuidemo.demos.list

import android.view.View
import com.microsoft.fluentui.listitem.ListSubHeaderView

interface IListSubHeader : IBaseListItem {
    var titleColor: ListSubHeaderView.TitleColor
    var customAccessoryView: View?
}

data class ListSubHeader(override var title: String = "") : IListSubHeader {
    override var titleColor: ListSubHeaderView.TitleColor = ListSubHeaderView.DEFAULT_TITLE_COLOR
    override var customAccessoryView: View? = null
}