package com.example.covid19tracker

import android.widget.ImageView

class CountryModel {
    
    var flag: String? = null
    var country: String? = null
    var cases: String? = null
    var todayCases: String? = null
    var deaths: String? = null
    var todayDeaths: String? = null
    var recovered: String? = null
    var activeCases: String? = null
    var critical: String? = null

    constructor(
        flag: String?,
        country: String?,
        cases: String?,
        todayCases: String?,
        deaths: String?,
        todayDeaths: String?,
        recovered: String?,
        activeCases: String?,
        critical: String?
    ) {
        this.flag = flag
        this.country = country
        this.cases = cases
        this.todayCases = todayCases
        this.deaths = deaths
        this.todayDeaths = todayDeaths
        this.recovered = recovered
        this.activeCases = activeCases
        this.critical = critical
    }

    constructor()


}