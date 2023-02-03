package com.example.bank_mobile.ViewModel

import android.view.View
import android.widget.AdapterView
import com.example.bank_mobile.View.LoanOnlineActivity

class OrganisationSpinnerAdapter(f: () -> Unit) : AdapterView.OnItemSelectedListener {
    var func = f
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        LoanOnlineActivity.localOrg = position
        func()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        LoanOnlineActivity.localOrg = null
        LoanOnlineActivity.localServ = null
    }
}