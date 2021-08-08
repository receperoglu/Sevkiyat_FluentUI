package com.microsoft.fluentui.persona

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import com.microsoft.fluentui.persona.R
import com.microsoft.fluentui.listitem.ListItemDivider
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import java.util.*

class PersonaListView : RecyclerView {

    var personas = ArrayList<IPersona>()
        set(value) {
            field = value
            personaListAdapter.personas = value
        }


    var onItemClickedListener: OnItemClickedListener? = null
        set(value) {
            field = value
            personaListAdapter.onItemClickedListener = value
        }

    private val personaListAdapter = PersonaListAdapter(context)

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        FluentUIContextThemeWrapper(appContext, R.style.Theme_FluentUI_Persona),
        attrs,
        defStyleAttr
    ) {
        adapter = personaListAdapter
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(ListItemDivider(context, DividerItemDecoration.VERTICAL))
    }

    interface OnItemClickedListener {
        fun onItemClicked(persona: IPersona)
    }
}