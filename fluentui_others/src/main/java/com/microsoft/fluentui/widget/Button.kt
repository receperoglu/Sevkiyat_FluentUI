
package com.microsoft.fluentui.widget

import android.content.Context
import androidx.appcompat.widget.AppCompatButton
import android.util.AttributeSet
import com.microsoft.fluentui.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper

class Button : AppCompatButton {
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.buttonStyle)
        : super(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Components), attrs, defStyleAttr)
}