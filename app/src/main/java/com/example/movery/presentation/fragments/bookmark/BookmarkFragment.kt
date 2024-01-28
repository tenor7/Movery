package com.example.movery.presentation.fragments.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movery.R
import com.example.movery.presentation.adapter.PopularMoviesAdapter
import com.example.movery.data.model.Movie
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase


class BookmarkFragment : Fragment() {
    private lateinit var bookmarkedMoviesRecyclerView: RecyclerView
    private lateinit var adapter: PopularMoviesAdapter
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)
        bookmarkedMoviesRecyclerView = view.findViewById(R.id.recyclerViewBookmark)
        bookmarkedMoviesRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        database = FirebaseDatabase.getInstance()
        val moviesRef = database.getReference("Movie")
        val query = moviesRef.orderByChild("favorite").equalTo("true")
        val options = FirebaseRecyclerOptions.Builder<Movie>()
            .setQuery(query, Movie::class.java)
            .build()

        adapter = PopularMoviesAdapter(options)
        bookmarkedMoviesRecyclerView.adapter = adapter

        return view
    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }
}
