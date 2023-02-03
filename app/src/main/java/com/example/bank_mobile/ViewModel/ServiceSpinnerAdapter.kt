package com.example.bank_mobile.ViewModel

import android.view.View
import android.widget.AdapterView
import com.example.bank_mobile.View.LoanOnlineActivity

class ServiceSpinnerAdapter(f: () -> Unit): AdapterView.OnItemSelectedListener {
    var func = f

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        LoanOnlineActivity.localServ = position
        func()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        LoanOnlineActivity.localServ = null
    }
}