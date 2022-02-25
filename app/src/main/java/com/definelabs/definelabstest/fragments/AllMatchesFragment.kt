package com.definelabs.definelabstest.fragments

import MatchesData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.definelabs.definelabstest.R
import com.definelabs.definelabstest.activities.dashboard.DashboardActivity
import com.definelabs.definelabstest.adapters.MatchesListAdapter
import com.definelabs.definelabstest.interfaces.RetrofitCallInterface
import com.definelabs.definelabstest.network.RetrofitIClient.getRetrofitClient
import kotlinx.android.synthetic.main.fragment_all_matches.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AllMatchesFragment : Fragment() {
    private lateinit  var act:DashboardActivity;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        act = activity as DashboardActivity



        var call = getRetrofitClient().getMatches()
        call.enqueue(object : Callback<MatchesData>{
            override fun onResponse(call: Call<MatchesData>, response: Response<MatchesData>) {
                tv_loading.visibility = View.GONE
                val venues = response.body()!!.response.venues
                tv_total.setText(""+venues.size)
                tv_fav.setText(""+act.db.getCount())
                rv_matches.apply {
                    adapter = MatchesListAdapter(venues,context,act.db)
                    layoutManager = LinearLayoutManager(context)
                }

            }

            override fun onFailure(call: Call<MatchesData>, t: Throwable) {
                tv_loading.text = "Failed to load data"
                Toast.makeText(context,"Failed to fetch data",Toast.LENGTH_SHORT).show()

            }

        })

    }
}