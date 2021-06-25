package com.example.covid19tracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.view.View
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.eazegraph.lib.models.PieModel
import org.json.JSONObject
import java.io.StringReader
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()

        btn_track_countries.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, AffectedCountries::class.java)
            startActivity(intent)
        })
    }

    private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)

    fun testDate() {
        val time = 1624618303029
        println(getDateString(time)) // 14 June 2019, 13:18:08
    }

    private fun getDateString(time: Long) : String = simpleDateFormat.format(time * 1000L)

    private fun getDateString(time: Int) : String = simpleDateFormat.format(time * 1000L)

    private fun fetchData(){
        var url: String = "https://corona.lmao.ninja/v2/all"

        loader.start()

        var request: StringRequest = StringRequest(Request.Method.GET, url, Response.Listener { response ->
            Toast.makeText(this, "Data Loaded Successfully", Toast.LENGTH_SHORT).show()

            var jsonObject: JSONObject = JSONObject(response.toString())

            Log.i("Cases", NumberFormat.getInstance().format(jsonObject.getString("cases").toInt()))


            var cases = jsonObject.getString("cases")
            var recovered =  jsonObject.getString("recovered")
            var critical = jsonObject.getString("critical")
            var active = jsonObject.getString("active")
            var todayCases= jsonObject.getString("todayCases")
            var todayDeaths= jsonObject.getString("todayDeaths")
            var totalDeaths = jsonObject.getString("deaths")
            var affectedCountries = jsonObject.getString("affectedCountries")
            var timeStamp =jsonObject.getString("updated")


            var test = testDate()

            Log.i("New TimeStamp", test.toString())

            tv_cases.setText((NumberFormat.getInstance().format(cases.toInt())).toString())
            tv_recovered.setText((NumberFormat.getInstance().format(recovered.toInt())).toString())
            tv_critical.setText((NumberFormat.getInstance().format(critical.toInt())).toString())
            tv_active.setText((NumberFormat.getInstance().format(active.toInt())).toString())
            tv_todayCases.setText((NumberFormat.getInstance().format(todayCases.toInt())).toString())
            tv_todayDeaths.setText((NumberFormat.getInstance().format(todayDeaths.toInt())).toString())
            tv_totalDeaths.setText((NumberFormat.getInstance().format(totalDeaths.toInt())).toString())
            tv_affectedCountries.setText(jsonObject.getString("affectedCountries"))



            pie_chart.addPieSlice(PieModel("Recovered", Integer.parseInt(recovered.toString()).toFloat(),Color.parseColor("#66BB6A")))
            pie_chart.addPieSlice(PieModel("Deaths", Integer.parseInt(totalDeaths.toString()).toFloat(),Color.parseColor("#EF5350")))
            pie_chart.addPieSlice(PieModel("Active", Integer.parseInt(active.toString()).toFloat(),Color.parseColor("#29B6F6")))
            pie_chart.startAnimation()




            loader.stop()
            loader.visibility = View.GONE
            scrollStats.visibility = View.VISIBLE


        }, Response.ErrorListener {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            loader.stop()
            loader.visibility = View.GONE
            scrollStats.visibility = View.VISIBLE

        })

        var requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

}