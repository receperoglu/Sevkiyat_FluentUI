package com.microsoft.fluentuidemo

 import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.modal_customer.*
import kotlinx.android.synthetic.main.modal_customer.btn_process
import kotlinx.android.synthetic.main.modal_customer.loading



class customerModal : DialogFragment() {

    private var toolbar: Toolbar? = null
    var processType: String = ""
    var customer_id: String = ""

    private var Tcustomer_address: EditText? = null
    private var Tcustomer_name: EditText? = null
    private var Tcustomer_tax_state: EditText? = null
    private var Tcustomer_tax_id: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_FluentUI)



    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window!!.setLayout(width, height)
            dialog.window!!.setWindowAnimations(R.style.Base_Widget_MaterialComponents_Slider)

            Tcustomer_name = dialog!!.findViewById<View>(R.id.Ecustomer_name) as EditText
            Tcustomer_address = dialog!!.findViewById<View>(R.id.Ecustomer_address) as EditText
            Tcustomer_tax_id = dialog!!.findViewById<View>(R.id.Ecustomer_tax_id) as EditText
            Tcustomer_tax_state = dialog!!.findViewById<View>(R.id.Ecustomer_tax_state) as EditText
            val bundle = arguments
            if (bundle != null) {

                processType = bundle.getString("processType")
                customer_id=bundle.getString("customer_id")
                Tcustomer_name!!.setText(bundle.getString("customer_name"))
                Tcustomer_address!!.setText(bundle.getString("customer_address"))
                Tcustomer_tax_id!!.setText(bundle.getString("customer_tax_id"))
                Tcustomer_tax_state!!.setText(bundle.getString("customer_tax_state"))
                toolbar!!.title = processType
            }
        }
        loading.visibility= View.GONE
        btn_process.setOnClickListener {
            loading.visibility= View.VISIBLE
            btn_process.visibility=View.GONE
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.modal_customer, container, false)
        toolbar = view.findViewById(R.id.modalbar)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar!!.title = processType
    }

    companion object {
        const val TAG = "example_dialog"
        fun display(fragmentManager: FragmentManager?): customerModal {
            val exampleDialog = customerModal()
            exampleDialog.show(fragmentManager!!, TAG)
            return exampleDialog
        }
    }
}