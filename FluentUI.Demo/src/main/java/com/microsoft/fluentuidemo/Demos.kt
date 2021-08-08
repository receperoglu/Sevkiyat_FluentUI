/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo

import com.microsoft.fluentuidemo.demos.*
import java.util.*
import kotlin.reflect.KClass

const val ACTION_BAR_LAYOUT = "Action Bar Layout"
const val APP_BAR_LAYOUT = "App Bar Layout"
const val BOTTOM_SHEET = "Bottom Sheet"
const val CONTEXTUAL_COMMAND_BAR = "Contextual CommandBar"
const val DATE_TIME_PICKER = "Date Time Picker"
const val DRAWER = "Drawer"
const val POPUP_MENU = "Popup Menu"
const val TAB_LAYOUT = "Tab Layout"
val DEMOS = arrayListOf(
    Demo(ACTION_BAR_LAYOUT, ActionBarLayoutActivity::class),
    Demo(APP_BAR_LAYOUT, AppBarLayoutActivity::class),
    Demo(BOTTOM_SHEET, BottomSheetActivity::class),
    Demo(CONTEXTUAL_COMMAND_BAR, ContextualCommandBarActivity::class),
     Demo(DRAWER, DrawerActivity::class),
    Demo(POPUP_MENU, PopupMenuActivity::class),
    Demo(TAB_LAYOUT, TabLayoutActivity::class),
    )


data class Demo(val title: String, val demoClass: KClass<out DemoActivity>) {
    val id: UUID = UUID.randomUUID()
}

