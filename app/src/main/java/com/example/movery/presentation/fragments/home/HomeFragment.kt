package com.example.movery.presentation.fragments.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movery.presentation.viewmodel.AccountActivity
import com.example.movery.R
import com.example.movery.data.model.Movie
import com.example.movery.presentation.adapter.LatestMoviesAdapter
import com.example.movery.presentation.adapter.PopularMoviesAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {
    private lateinit var popularMoviesRecyclerView: RecyclerView
    private lateinit var latestMoviesRecyclerView: RecyclerView
    private lateinit var adapter: PopularMoviesAdapter
    private lateinit var adapterTwo: LatestMoviesAdapter
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        latestMoviesRecyclerView = view.findViewById(R.id.recyclerView)
        latestMoviesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        popularMoviesRecyclerView = view.findViewById(R.id.recyclerViewSecond)
        popularMoviesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)

        database = FirebaseDatabase.getInstance()
        val moviesRef = database.getReference("Movie")

        val options = FirebaseRecyclerOptions.Builder<Movie>()
            .setQuery(moviesRef, Movie::class.java)
            .build()

        val options2 = FirebaseRecyclerOptions.Builder<Movie>()
            .setQuery(moviesRef.orderByChild("rating").limitToLast(6), Movie::class.java)
            .build()

        adapter = PopularMoviesAdapter(options2)
        popularMoviesRecyclerView.adapter = adapter

        adapterTwo = LatestMoviesAdapter(options)
        latestMoviesRecyclerView.adapter = adapterTwo

        val accountImage = view.findViewById<ImageView>(R.id.accountImage)
        accountImage.setOnClickListener {
            val intent = Intent(activity, AccountActivity::class.java)
            startActivity(intent)
        }
        return view
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
        adapterTwo.startListening()
    }
    override fun onStop() {
        super.onStop()
        //adapter.stopListening()
    }
}