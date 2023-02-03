package com.example.bank_mobile.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.bank_mobile.Model.Domain.Organisation
import com.example.bank_mobile.Model.Domain.Service
import com.example.bank_mobile.R
import com.example.bank_mobile.databinding.ActivityLoanOnlineBinding

class LoanOnlineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoanOnlineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanOnlineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addSpinnerAdapters()
    }

    fun addSpinnerAdapters() {

        var orgs = ArrayList<Organisation>()
        orgs.add(Organisation(1, "org name by name mane", "add", "dir", "as"))
        orgs.add(Organisation(2, "name 2 2 2 lalalala", "lol", "dir2", "as2"))
        orgs.add(Organisation(3, "trytryrrtryrt", "add3", "dir3", "as3"))
        orgs.add(Organisation(4, "dfafa", "add3", "dir3", "as3"))
        orgs.add(Organisation(5, "adsf", "add3", "dir3", "as3"))
        orgs.add(Organisation(6, "gdgd", "add3", "dir3", "as3"))
        var orgNames = orgs.map { it.orgName }
        var adapter = ArrayAdapter(this, R.layout.organisation_spinner_item, R.id.orgspiitem, orgNames)
        adapter.setDropDownViewResource(R.layout.organisation_spinner_dropdown_item)
        binding.organisationsSpinner.adapter = adapter


        var serv = ArrayList<Service>()
        serv.add(Service(1, "serv name 1", "desc", "20%", "mounth", "3 mounths"))
        serv.add(Service(2, "serv name 1dsdfd", "desc", "25%", "1 days", "mounth"))
        serv.add(Service(3, "serv name 312313 2", "desc", "23%", "3 days", "5 days"))
        var servNames = serv.map { it.serviceName }
        var servAdapter = ArrayAdapter(this, R.layout.organisation_spinner_item, R.id.orgspiitem, servNames)
        servAdapter.setDropDownViewResource(R.layout.organisation_spinner_dropdown_item)
        binding.servicesSpinner.adapter = servAdapter
    }
}