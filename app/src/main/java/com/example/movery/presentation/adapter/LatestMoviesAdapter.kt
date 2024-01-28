package com.example.movery.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movery.presentation.viewmodel.DetailInformationActivity
import com.example.movery.R
import com.example.movery.data.model.Movie
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class LatestMoviesAdapter(options: FirebaseRecyclerOptions<Movie>) :
    FirebaseRecyclerAdapter<Movie, LatestMoviesAdapter.MovieViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMoviesAdapter.MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_latest_movie,
            parent,
            false
        )
        return LatestMoviesAdapter.MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int, model: Movie) {
        holder.textViewTitle.text = model.title
        holder.textViewRating.text = model.rating.toString()
        holder.ratingBar.rating = model.rating.toFloat()

        val requestOptions = RequestOptions().placeholder(R.drawable.test)

        Glide.with(holder.itemView.context)
            .applyDefaultRequestOptions(requestOptions)
            .load(model.poster)
            .into(holder.imageViewPoster)

        holder.imageViewBookmark.setImageResource(
            if (model.favorite == "true") R.drawable.bookmark_filled else R.drawable.bookmark
        )

        holder.itemView.findViewById<CardView>(R.id.CardViewLatest).setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailInformationActivity::class.java)
            intent.putExtra("imageViewPoster", model.poster)
            intent.putExtra("favorite", model.favorite)
            intent.putExtra("textViewTitle", model.title)
            intent.putExtra("textViewRating", model.rating.toFloat())
            intent.putExtra("textViewDescription", model.description)
            intent.putExtra("textGenre", model.genre)
            intent.putExtra("ratingBar", model.rating)
            holder.itemView.context.startActivity(intent)
        }

        holder.imageViewBookmark.setOnClickListener{
            val movieRef = getRef(position)
            val currentFavorite = model.favorite
            val newFavorite = if (currentFavorite == "true"){
                Toast.makeText(holder.itemView.context, "Удалено из Закладок", Toast.LENGTH_SHORT).show()
                "false"
            }
            else{
                Toast.makeText(holder.itemView.context, "Добавлено в Закладки", Toast.LENGTH_SHORT).show()
                "true"
            }
            movieRef.child("favorite").setValue(newFavorite)
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
        val imageViewBookmark: ImageView = itemView.findViewById(R.id.imageViewBookmark)
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
    }
}