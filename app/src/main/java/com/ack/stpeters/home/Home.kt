package com.ack.stpeters.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ack.stpeters.adapter.MembersAdapter
import com.ack.stpeters.databinding.FragmentHomeBinding
import com.ack.stpeters.model.FetchMembers
import com.ack.stpeters.model.Member


class Home : Fragment(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var memberRecyclerView: RecyclerView
    private lateinit var mContext: Context
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        mContext = container!!.context

        memberRecyclerView = binding.recyclerview
        memberRecyclerView.layoutManager = LinearLayoutManager(mContext)

        // initialize the search view
        binding.searchView.isSubmitButtonEnabled = true
        binding.searchView.setOnQueryTextListener(this)


            viewModel.fetchMembers.observe(viewLifecycleOwner) {

                memberRecyclerView.adapter =
                    MembersAdapter(it as ArrayList<FetchMembers>, mContext)
            }



        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if  (query!= null) {
            viewModel.search(query.trim())
            Log.d("search",query.trim())
        }
        return true
    }

    override fun onQueryTextChange(text: String?): Boolean {
        if (text != null) {
            viewModel.search(text.trim())
            Log.d("search",text.trim())
        }
        return true
    }


}