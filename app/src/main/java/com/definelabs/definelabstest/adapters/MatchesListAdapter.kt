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

class MatchesListAdapter(
    private val list: List<Venues>,
    val context: Context,
    val db: SqliteManager
) :
    RecyclerView.Adapter<MatchesListAdapter.ViewHolder>() {


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
        holder.tvName.setText(venue.name)
        if (venue.verified)
            holder.ivVerified.visibility = View.VISIBLE
        holder.tvCity.setText(venue.location.city)
        holder.tvCountry.setText(venue.location.country)
        holder.tvId.setText("id: ${venue.id}")
        holder.tvPhone.setText(venue.contact.phone)
        holder.tvUsers.setText("${venue.stats.usersCount}")
        holder.tvAddress.setText(venue.location.formattedAddress.get(0))


        if (db.checkExistance(venue.id) > 0) {
            list.get(position).isFav = true
            holder.ivFav.setImageResource(R.drawable.ic_star_selected)
        }

        holder.ivFav.setOnClickListener {
            if (!list.get(position).isFav) {

                holder.ivFav.setImageResource(R.drawable.ic_star_selected)
                holder.ivFav.setImageDrawable(context.getDrawable(R.drawable.ic_star_selected))
                list.get(position).isFav = true
                db.insertData(
                    LocalMatchData(
                        holder.tvName.text.toString(),
                        holder.tvCity.text.toString(),
                        holder.tvCountry.text.toString(),
                        holder.tvId.text.toString(),
                        holder.tvPhone.text.toString(),
                        holder.tvUsers.text.toString(),
                        holder.tvAddress.toString(),
                        venue.verified
                    )
                )
            } else {
                holder.ivFav.setImageResource(R.drawable.ic_star_unselected)
                db.removeData(venue.id)
                list.get(position).isFav = false
            }
            notifyDataSetChanged()
        }


        //updating ui if null/empty data received from server

        if (holder.tvPhone.text.toString().isEmpty())
            holder.tvPhone.setText(context.getString(R.string.na))
        else
            holder.tvPhone.setText(context.getString(R.string.na));
        if (holder.tvAddress.text.toString().isEmpty()) {
            holder.tvAddress.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}