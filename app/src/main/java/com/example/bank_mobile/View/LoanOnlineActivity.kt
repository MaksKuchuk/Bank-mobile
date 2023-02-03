package com.example.bank_mobile.View

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.bank_mobile.Model.Domain.Organisation
import com.example.bank_mobile.Model.Domain.Service
import com.example.bank_mobile.Model.HTTPInfo
import com.example.bank_mobile.Model.Interface.IRepository.IUserRepository
import com.example.bank_mobile.Model.Serializer.Request.UserOnlineServicesRequest
import com.example.bank_mobile.Model.Serializer.Request.UserTakeLoanOnlineRequest
import com.example.bank_mobile.Model.Serializer.Response.UserAllOrganisationsResponse
import com.example.bank_mobile.Model.Serializer.Response.UserOnlineServiceResponse
import com.example.bank_mobile.R
import com.example.bank_mobile.ViewModel.OrganisationSpinnerAdapter
import com.example.bank_mobile.ViewModel.ServiceSpinnerAdapter
import com.example.bank_mobile.databinding.ActivityLoanOnlineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoanOnlineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoanOnlineBinding
    var userRepository = IUserRepository.create()
    val saveTokensData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    var orgs = ArrayList<Organisation>()
    var serv = ArrayList<Service>()
    var orgNames = ArrayList<String>()
    var servNames = ArrayList<String>()
    lateinit var orgadapter: ArrayAdapter<String>
    lateinit var servAdapter: ArrayAdapter<String>

    companion object {
        public var localOrg: Int? = null
        public var localServ: Int? = null
    }

    val handler = Handler(Looper.myLooper()!!)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanOnlineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orgadapter =
            ArrayAdapter(this, R.layout.organisation_spinner_item, R.id.orgspiitem, orgNames)
        servAdapter = ArrayAdapter<String>(
            this, R.layout.organisation_spinner_item, R.id.orgspiitem, servNames
        )

        setObserver()
        addSpinnerAdapters()

        getChangeOrganisations()

        binding.btnSubmitService.setOnClickListener {
            if (binding.timeEditText.text.toString() != "" && binding.amountEditText.text.toString() != "") {
                takeLoanOnline(
                    localServ!!,
                    binding.timeEditText.text.toString().toInt(),
                    binding.amountEditText.text.toString().toInt()
                )
            }
        }
    }

    fun addSpinnerAdapters() {
        orgadapter.setDropDownViewResource(R.layout.organisation_spinner_dropdown_item)
        binding.organisationsSpinner.adapter = orgadapter
        binding.organisationsSpinner.onItemSelectedListener = OrganisationSpinnerAdapter {
            getChangeServices(orgs[localOrg!!].id)
            binding.orgNameText.text = orgs[localOrg!!].orgName
        }

        servAdapter.setDropDownViewResource(R.layout.organisation_spinner_dropdown_item)
        binding.servicesSpinner.adapter = servAdapter
        binding.servicesSpinner.onItemSelectedListener = ServiceSpinnerAdapter {
            binding.serviceNameText.text = serv[localServ!!].serviceName
            binding.percentText.text = serv[localServ!!].percent + "%"
        }
    }

    fun getChangeOrganisations() {
        CoroutineScope(Dispatchers.IO).launch {
            var res = userRepository.getAllOrganisations()
            if (res == null) {
                checkRefreshToken()
                res = userRepository.getAllOrganisations()
            }
            handler.post {
                updateOrganisations(res!!)
            }
        }
    }

    fun getChangeServices(id: Int?) {
        if (id == null) return

        CoroutineScope(Dispatchers.IO).launch {
            var res = userRepository.getOnlineServices(UserOnlineServicesRequest(id))
            if (res == null) {
                checkRefreshToken()
                res = userRepository.getOnlineServices(UserOnlineServicesRequest(id))
            }
            handler.post {
                updateServices(res!!)
            }
        }
    }

    fun updateOrganisations(res: List<UserAllOrganisationsResponse>) {
        orgs.clear()
        orgs.addAll(res.map {
            Organisation(
                it.id, it.orgName, it.legalAddress, it.genDirector, it.foundingDate
            )
        })
        orgNames.clear()
        orgNames.addAll(orgs.map { it.orgName })
        orgadapter.notifyDataSetChanged()
    }

    fun updateServices(res: List<UserOnlineServiceResponse>) {
        serv.clear()
        serv.addAll(res.map {
            Service(
                it.id,
                it.serviceName,
                it.description,
                it.percent,
                it.minLoanPeriod,
                it.maxLoadPeriod
            )
        })
        servNames.clear()
        servNames.addAll(serv.map { it.serviceName })
        servAdapter.notifyDataSetChanged()

        if (localServ == null || serv.size <= localServ!!) {
            binding.serviceNameText.text = ""
            binding.percentText.text = ""
        } else {
            binding.serviceNameText.text = serv[localServ!!].serviceName
            binding.percentText.text = serv[localServ!!].percent + "%"
        }
    }

    fun takeLoanOnline(serviceId: Int, amount: Int, period: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var res =
                userRepository.takeLoanOnline(UserTakeLoanOnlineRequest(serviceId, amount, period))
            if (res == null) {
                checkRefreshToken()
                res = userRepository.takeLoanOnline(
                    UserTakeLoanOnlineRequest(
                        serviceId, amount, period
                    )
                )
            }
            handler.post {
                if (res != null && res!!.isSuccess) {
                    Toast.makeText(this@LoanOnlineActivity, "Succeed", Toast.LENGTH_SHORT).show()
                    binding.timeEditText.setText("")
                    binding.amountEditText.setText("")
                }
            }
        }
    }

    fun setObserver() {
        saveTokensData.value = 0
        val saveTokensObserver = Observer<Int> {
            val sharedPreferences: SharedPreferences = this.getSharedPreferences(
                "infoFile", MODE_PRIVATE
            )
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("accessToken", HTTPInfo.accessToken)
            editor.putString("refreshToken", HTTPInfo.refreshToken)
            editor.apply()
        }
        saveTokensData.observe(this@LoanOnlineActivity, saveTokensObserver)
    }

    suspend fun checkRefreshToken() {
        var res = userRepository.updateTokenUser()!!
        if (res.refresh_token != "") {
            HTTPInfo.accessToken = res.access_token
            HTTPInfo.refreshToken = res.refresh_token
            saveTokensData.postValue(saveTokensData.value!! + 1)
        } else {
            handler.post {
                goToRegAuthActivity()
            }
        }
    }

    fun goToRegAuthActivity() {
        Intent(this, RegAuthActivity::class.java).also { startActivity(it) }
        this.finish()
    }
}