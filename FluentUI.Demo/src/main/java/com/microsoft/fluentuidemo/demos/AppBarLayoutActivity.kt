
package com.microsoft.fluentuidemo.demos

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.microsoft.fluentui.appbarlayout.AppBarLayout
import com.microsoft.fluentui.listitem.ListItemDivider
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.listitem.ListSubHeaderView
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.AvatarView
import com.microsoft.fluentui.search.Searchbar
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.getTintedDrawable
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.DemoListActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.demos.list.*
 import kotlinx.android.synthetic.main.activity_app_bar_layout.*
import kotlinx.android.synthetic.main.activity_demo_detail.*
import kotlinx.android.synthetic.main.activity_demo_list.app_bar
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class AppBarLayoutActivity : DemoActivity(), View.OnClickListener {
    companion object {
        private var themeId = R.style.AppTheme
        private const val SCROLL_BEHAVIOR = "scrollBehavior"
        private const val NAVIGATION_ICON_TYPE = "navigationIconType"
        private const val SEARCHBAR_IS_ACTION_MENU_VIEW = "searchbarIsActionMenuView"
        private const val SEARCHBAR_HAS_FOCUS = "searchbarHasFocus"
        private const val SEARCHBAR_QUERY = "searchbarQuery"
    }

    enum class NavigationIconType {
        NONE, AVATAR, BACK_ICON
    }

    override val contentLayoutId: Int
        get() = R.layout.activity_app_bar_layout

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private var optionsMenu: Menu? = null
        set(value) {
            field = value
            updateSearchbar()
            updateSearchbarFocus()
            updateSearchbarQuery()
        }
    private var scrollBehavior: AppBarLayout.ScrollBehavior = AppBarLayout.ScrollBehavior.COLLAPSE_TOOLBAR
        set(value) {
            field = value
            updateScrollBehavior()
        }
    private var navigationIconType: NavigationIconType = NavigationIconType.BACK_ICON
        set(value) {
            field = value
            updateNavigationIcon()
        }
    private var searchbarIsActionMenuView: Boolean = false
        set(value) {
            field = value
            updateSearchbar()
        }
    private var searchbarHasFocus: Boolean = false
        set(value) {
            field = value
            updateSearchbarFocus()
        }
    private var searchbarQuery: String = ""
        set(value) {
            field = value
            updateSearchbarQuery()
        }

     private lateinit var scrollBehaviorSubHeader: ListSubHeader
    private lateinit var navigationIconButton: ButtonItem
    private lateinit var searchbarButton: ButtonItem
    private lateinit var searchbar: Searchbar
     val arrayCustomers: ArrayList<IBaseListItem> = ArrayList(5)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(themeId)

        var scrollBehaviorOrdinal = scrollBehavior.ordinal
        var navigationIconTypeOrdinal = navigationIconType.ordinal
        savedInstanceState?.let {
            scrollBehaviorOrdinal = it.getInt(SCROLL_BEHAVIOR)
            navigationIconTypeOrdinal = it.getInt(NAVIGATION_ICON_TYPE)
            searchbarIsActionMenuView = it.getBoolean(SEARCHBAR_IS_ACTION_MENU_VIEW)
            searchbarHasFocus = it.getBoolean(SEARCHBAR_HAS_FOCUS)
            searchbarQuery = it.getString(SEARCHBAR_QUERY) ?: ""
        }
        scrollBehavior = AppBarLayout.ScrollBehavior.values()[scrollBehaviorOrdinal]
        navigationIconType = NavigationIconType.values()[navigationIconTypeOrdinal]

        super.onCreate(savedInstanceState)

        searchbar = createSearchbar()

        navigationIconButton = ButtonItem(
            buttonText = resources.getString(R.string.app_bar_layout_hide_icon_button),
            id = R.id.app_bar_layout_toggle_navigation_icon_button,
            onClickListener = this
        )
        searchbarButton = ButtonItem(
            buttonText = resources.getString(R.string.app_bar_layout_searchbar_accessory_view_button),
            id = R.id.app_bar_layout_toggle_searchbar_type_button,
            onClickListener = this
        )


        app_bar.scrollTargetViewId = R.id.list_searcharticels

        updateScrollBehavior()
        updateNavigationIcon()
        updateSearchbar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        searchbarQuery = searchbar.query.toString()

        outState.putInt(SCROLL_BEHAVIOR, scrollBehavior.ordinal)
        outState.putInt(NAVIGATION_ICON_TYPE, navigationIconType.ordinal)
        outState.putBoolean(SEARCHBAR_IS_ACTION_MENU_VIEW, searchbarIsActionMenuView)
        outState.putBoolean(SEARCHBAR_HAS_FOCUS, searchbarHasFocus)
        outState.putString(SEARCHBAR_QUERY, searchbarQuery)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.app_bar_layout_toggle_scroll_behavior_button -> {
                scrollBehavior = when (scrollBehavior) {
                    AppBarLayout.ScrollBehavior.NONE -> AppBarLayout.ScrollBehavior.COLLAPSE_TOOLBAR
                    AppBarLayout.ScrollBehavior.COLLAPSE_TOOLBAR -> AppBarLayout.ScrollBehavior.PIN
                    AppBarLayout.ScrollBehavior.PIN -> AppBarLayout.ScrollBehavior.NONE
                }
            }
            R.id.app_bar_layout_toggle_navigation_icon_button ->
                navigationIconType = when (navigationIconType) {
                    NavigationIconType.NONE -> NavigationIconType.AVATAR
                    NavigationIconType.AVATAR -> NavigationIconType.BACK_ICON
                    NavigationIconType.BACK_ICON -> NavigationIconType.NONE
                }
            R.id.app_bar_layout_toggle_searchbar_type_button ->
                searchbarIsActionMenuView = !searchbarIsActionMenuView
            R.id.app_bar_layout_toggle_theme_button -> {
                themeId = when (themeId) {
                    R.style.AppTheme -> R.style.AppTheme_Neutral
                    R.style.AppTheme_Neutral -> R.style.AppTheme_Orange
                    else -> R.style.AppTheme
                }

                recreate()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_app_bar_layout, menu)

        optionsMenu = menu

        for (index in 0 until menu.size()) {
            val drawable = menu.getItem(index).icon
            drawable?.setColorFilter(
                ThemeUtil.getThemeAttrColor(this, R.attr.fluentuiToolbarIconColor),
                PorterDuff.Mode.SRC_IN
            )
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.app_bar_layout_action_search)
            (item.actionView as? Searchbar)?.requestSearchViewFocus()

        return super.onOptionsItemSelected(item)
    }

    private fun updateScrollBehavior() {
        if (app_bar == null)
            return

        app_bar.scrollBehavior = scrollBehavior

        scrollBehaviorSubHeader.title =
            resources.getString(
                R.string.app_bar_layout_toggle_scroll_behavior_sub_header,
                scrollBehavior.toString()
            )

     }

    private fun updateNavigationIcon() {
        if (app_bar == null)
            return

        searchbar.clearFocus()
        optionsMenu?.findItem(R.id.app_bar_layout_action_search)?.collapseActionView()

        when (navigationIconType) {
            NavigationIconType.NONE -> {
                app_bar.toolbar.navigationIcon = null

                navigationIconButton.buttonText = resources.getString(R.string.app_bar_layout_show_avatar_button)
            }

            NavigationIconType.BACK_ICON -> {
                val backArrow = ContextCompat.getDrawable(this, R.drawable.ms_ic_arrow_left_24_filled)
                backArrow?.setTint(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(this, R.style.Theme_FluentUI_Components), R.attr.fluentuiToolbarIconColor))
                app_bar.toolbar.navigationIcon = backArrow
                app_bar.toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
                navigationIconButton.buttonText = resources.getString(R.string.app_bar_layout_hide_icon_button)
            }
        }

     }

    private fun updateSearchbar() {
        if (app_bar == null)
            return

        searchbar.isActionMenuView = searchbarIsActionMenuView
        if (searchbarIsActionMenuView) {
            val optionsMenu = optionsMenu ?: return
            app_bar.accessoryView = null

            val searchIcon = getTintedDrawable(R.drawable.ms_ic_search_24_filled, ThemeUtil.getThemeAttrColor(this, R.attr.fluentuiToolbarIconColor))
            optionsMenu.add(R.id.app_bar_menu, R.id.app_bar_layout_action_search, 0, getString(R.string.app_bar_layout_menu_search))
                .setIcon(searchIcon)
                .setActionView(searchbar)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_ALWAYS)

            searchbarButton.buttonText = resources.getString(R.string.app_bar_layout_searchbar_accessory_view_button)
        } else {
            optionsMenu?.removeItem(R.id.app_bar_layout_action_search)
            app_bar.accessoryView = searchbar
            searchbarButton.buttonText = resources.getString(R.string.app_bar_layout_searchbar_action_view_button)
        }

     }

    private fun updateSearchbarFocus() {
        if (app_bar == null)
            return

        if (searchbarHasFocus) {
            optionsMenu?.performIdentifierAction(R.id.app_bar_layout_action_search, 0)
            searchbar.requestSearchViewFocus()
        }
    }

    private fun updateSearchbarQuery() {
        if (app_bar == null)
            return

        searchbar.setQuery(searchbarQuery, false)
    }

    private fun createSearchbar(): Searchbar {
        val searchbar = Searchbar(ContextThemeWrapper(this, themeId))
        searchbar.onQueryTextFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            searchbarHasFocus = hasFocus
        }

        return searchbar
    }




}