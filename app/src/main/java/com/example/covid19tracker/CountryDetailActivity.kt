package com.example.covid19tracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_country_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import org.eazegraph.lib.models.PieModel
import java.text.NumberFormat

class CountryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)

        var countryPosition= intent.getIntExtra("position", 0)


        var cases= intent.getStringExtra("cases")
        var recovered= intent.getStringExtra("recovered")
        var critical= intent.getStringExtra("critical")
        var activeCases= intent.getStringExtra("activeCases")
        var todayCases= intent.getStringExtra("todayCases")
        var todayDeaths= intent.getStringExtra("todayDeaths")
        var deaths= intent.getStringExtra("deaths")
        var country= intent.getStringExtra("country")


        supportActionBar!!.title = "Details Of " + country
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        Log.i("Details of $country" , cases.toString() + recovered.toString() + critical.toString()
        + activeCases.toString() + todayCases.toString() + todayDeaths.toString() + deaths.toString() )

        Log.i("cases", cases )
        Log.i("recovered", recovered )
        Log.i("critical", critical )
        Log.i("activeCases", activeCases )
        Log.i("todayCases", todayCases )
        Log.i("todayDeaths", todayDeaths )
        Log.i("deaths", deaths )


        Log.i("Country Position", countryPosition.toString())

        Log.i("Country", AffectedCountries.countryModel[countryPosition].country)


        tv_country_title.setText(country)


        tv_cases_country_detail.setText((NumberFormat.getInstance().format(cases.toInt())).toString())
        tv_recovered_country_detail.setText((NumberFormat.getInstance().format(recovered.toInt())).toString())
        tv_critical_country_detail.setText((NumberFormat.getInstance().format(critical.toInt())).toString())
        tv_active_country_detail.setText((NumberFormat.getInstance().format(activeCases.toInt())).toString())
        tv_todayCases_country_detail.setText((NumberFormat.getInstance().format(todayCases.toInt())).toString())
        tv_todayDeaths_country_detail.setText((NumberFormat.getInstance().format(todayDeaths.toInt())).toString())
        tv_totalDeaths_country_detail.setText((NumberFormat.getInstance().format(deaths.toInt())).toString())



        pie_chart_country_details.addPieSlice(PieModel("Recovered", Integer.parseInt(recovered.toString()).toFloat(), Color.parseColor("#66BB6A")))
        pie_chart_country_details.addPieSlice(
            PieModel("Deaths", Integer.parseInt(deaths.toString()).toFloat(),
                Color.parseColor("#EF5350"))
        )
        pie_chart_country_details.addPieSlice(
            PieModel("Active", Integer.parseInt(activeCases.toString()).toFloat(),
                Color.parseColor("#29B6F6"))
        )
        pie_chart_country_details.startAnimation()

        country_arc_loader.stop()
        country_arc_loader.visibility = View.GONE
        scrollStats_country_detail.visibility = View.VISIBLE


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home){
            finish()
        }

        return super.onOptionsItemSelected(item)
    }





}