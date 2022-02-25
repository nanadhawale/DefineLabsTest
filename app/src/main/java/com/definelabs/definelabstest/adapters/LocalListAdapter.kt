package com.definelabs.definelabstest.adapters

import Venues
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.definelabs.definelabstest.R
import com.definelabs.definelabstest.database.SqliteManager
import com.definelabs.definelabstest.database.data.LocalMatchData
import kotlinx.android.synthetic.main.layout_match_item.view.*

class LocalListAdapter(
    var list: ArrayList<LocalMatchData>,
    val context: Context?,
    val db: SqliteManager
) :
    RecyclerView.Adapter<LocalListAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.findViewById<TextView>(R.id.tv_mc_name)
        var ivVerified = itemView.findViewById<ImageView>(R.id.iv_verified)
        var tvCity = itemView.findViewById<TextView>(R.id.tv_city)
        var tvCountry = itemView.findViewById<TextView>(R.id.tv_country)
        var tvId = itemView.findViewById<TextView>(R.id.tv_id)
        var tvPhone = itemView.findViewById<TextView>(R.id.tv_phone)
        var tvUsers = itemView.findViewById<TextView>(R.id.tv_users)
        var tvAddress = itemView.findViewById<TextView>(R.id.tv_address)
        var ivFav = itemView.findViewById<ImageView>(R.id.iv_fav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.layout_match_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val venue = list.get(position)
        holder.tvName.setText(venue.matchName)
        if (venue.isVerified)
            holder.ivVerified.visibility = View.VISIBLE
        holder.tvCity.setText(venue.city)
        holder.tvCountry.setText(venue.country)
        holder.tvId.setText("id: ${venue.id}")
        holder.tvPhone.setText(venue.phone)
        holder.tvUsers.setText("${venue.usersCount}")
        holder.tvAddress.setText(venue.address)
        holder.ivFav.setImageDrawable(context!!.getDrawable(R.drawable.ic_star_selected))

        holder.ivFav.setOnClickListener {
            list.removeAt(position)
            //list = list.drop(position)
            db.removeData(venue.id)
            notifyDataSetChanged()
        }


        //updating ui if null/empty data received from server

        if (holder.tvPhone.text.toString().isEmpty())
            holder.tvPhone.setText(context!!.getString(R.string.na))
        else
            holder.tvPhone.setText(context!!.getString(R.string.na));
        if (holder.tvAddress.text.toString().isEmpty()) {
            holder.tvAddress.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}