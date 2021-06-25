package com.example.covid19tracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter(private var exampleItem: ArrayList<CountryModel>, private var context: Context):
        RecyclerView.Adapter<Adapter.ViewHolder>() {


    fun CountryAdapter( context: Context,  exampleItem: ArrayList<CountryModel>){
        this.exampleItem = exampleItem
        this.context = context
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var flag : ImageView =itemView.findViewById(R.id.iv_flag)
        val tv_flag: TextView = itemView.findViewById(R.id.tv_flag)
        val item :LinearLayout= itemView.findViewById(R.id.item)


        fun bind(countryModel: CountryModel, context: Context){
            tv_flag.text = countryModel.country
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemViem = LayoutInflater.from(parent.context).inflate(R.layout.example_item, parent, false)
        return ViewHolder(itemViem)

    }

     fun filterList(filterList: ArrayList<CountryModel>){
        exampleItem = filterList
        notifyDataSetChanged()
    }

    override fun getItemCount() = exampleItem.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        //var positionNew = holder.adapterPosition
        var currentItem = exampleItem[position]

        holder.tv_flag.text = currentItem.country
        Glide.with(context).load(currentItem.flag).into(holder.flag)

        holder.bind(exampleItem.get(position), context)



        holder.itemView.setOnClickListener { view->

            var intent= Intent(context, CountryDetailActivity::class.java)
            intent.putExtra("position", position)

            intent.putExtra("cases", currentItem.cases)
            intent.putExtra("recovered", currentItem.recovered)
            intent.putExtra("critical", currentItem.critical)
            intent.putExtra("activeCases", currentItem.activeCases)
            intent.putExtra("todayCases", currentItem.todayCases)
            intent.putExtra("todayDeaths", currentItem.todayDeaths)
            intent.putExtra("deaths", currentItem.deaths)
            intent.putExtra("country", currentItem.country)

            context.startActivity(intent)


        }


    }

}