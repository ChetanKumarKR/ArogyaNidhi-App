package com.example.arogyanidhi

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HospitalAdapter(private var hospitalList: List<Hospital>) :
    RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {

    // ✅ VIEW HOLDER (MATCHES XML IDs)
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val hospitalName: TextView = itemView.findViewById(R.id.hospitalName)
        val hospitalLocation: TextView = itemView.findViewById(R.id.hospitalLocation)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val ratingText: TextView = itemView.findViewById(R.id.ratingText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hospital, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = hospitalList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val hospital = hospitalList[position]

        // ✅ SET DATA
        holder.hospitalName.text = hospital.name
        holder.hospitalLocation.text = hospital.location
        holder.ratingBar.rating = hospital.rating
        holder.ratingText.text = hospital.rating.toString()

        // 📍 OPEN GOOGLE MAPS
        holder.itemView.setOnClickListener {

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=${hospital.name},${hospital.location}")
            )

            holder.itemView.context.startActivity(intent)
        }
    }

    // 🔄 UPDATE LIST (FOR SEARCH)
    fun updateData(newList: List<Hospital>) {
        hospitalList = newList
        notifyDataSetChanged()
    }
}