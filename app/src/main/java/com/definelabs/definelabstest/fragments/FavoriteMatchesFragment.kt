package com.definelabs.definelabstest.fragments

import android.os.Bundle
import android.os.LocaleList
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.definelabs.definelabstest.R
import com.definelabs.definelabstest.activities.dashboard.DashboardActivity
import com.definelabs.definelabstest.adapters.LocalListAdapter
import com.definelabs.definelabstest.adapters.MatchesListAdapter
import com.definelabs.definelabstest.database.data.LocalMatchData
import kotlinx.android.synthetic.main.fragment_all_matches.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteMatches.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteMatchesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_matches, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteMatches.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteMatchesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val act = activity as DashboardActivity
        val venues = act.db.readData()
        val list= arrayOf(venues).toCollection(ArrayList())
        val data = ArrayList<LocalMatchData>()
        data.addAll(list.get(0))
        val localAdapter = LocalListAdapter(data,context,act.db)
        rv_matches.apply {
            adapter = localAdapter
            layoutManager = LinearLayoutManager(context)
        }

        if(localAdapter.list.size==0)
            tv_loading.text = "No favorites found"
        else
            tv_loading.visibility = View.GONE
    }
}