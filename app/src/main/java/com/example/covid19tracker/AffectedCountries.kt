package com.example.covid19tracker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_affected_countries.*
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class AffectedCountries : AppCompatActivity() {



    companion object CountryModelList {
        var countryModel = ArrayList<CountryModel>()
    }

    var list : CountryModel? = null

    private var adapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affected_countries)


        supportActionBar!!.title = "Afftected Countries"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)


        adapter = Adapter(countryModel, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)


        fetchData()


        et_search.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString())
            }

        })

    }

    private fun filter(toString: String) {

        var filterList = ArrayList<CountryModel>()

        for(item in countryModel){
            if (item.country!!.toLowerCase().contains(toString.toLowerCase())){
                filterList.add(item)
            }
        }
        adapter!!.filterList(filterList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home){
            countryModel.clear()
            finish()

        }

        return super.onOptionsItemSelected(item)
    }



    private fun fetchData(){
        var url: String = "https://corona.lmao.ninja/v2/countries/"

        arc_loader.start()
        countryModel.clear()

        var request: StringRequest = StringRequest(Request.Method.GET, url, Response.Listener { response ->

            var jsonArray: JSONArray = JSONArray(response)

            for (i in 0 until jsonArray.length()) {
                var jsonObject: JSONObject = jsonArray.getJSONObject(i)

                var country: String = jsonObject.getString("country")
                var cases: String = jsonObject.getString("cases")
                var todayCases: String = jsonObject.getString("todayCases")
                var deaths: String = jsonObject.getString("deaths")
                var todayDeaths: String = jsonObject.getString("todayDeaths")
                var recovered: String = jsonObject.getString("recovered")
                var activeCases: String = jsonObject.getString("active")
                var critical: String = jsonObject.getString("critical")

                var jsonObjectNew: JSONObject= jsonObject.getJSONObject("countryInfo")
                var flagUrl: String = jsonObjectNew.getString("flag")

                list = CountryModel(flagUrl , country,cases, todayCases, deaths, todayDeaths, recovered, activeCases, critical)

                countryModel.add(list!!)

            }
            adapter!!.notifyDataSetChanged()
            arc_loader.stop()
            arc_loader.visibility = View.GONE



        }, Response.ErrorListener {
            arc_loader.stop()
            arc_loader.visibility = View.GONE

        })

        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            countryModel.clear()
            finish()

        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        countryModel.clear()
        finish()
    }

}