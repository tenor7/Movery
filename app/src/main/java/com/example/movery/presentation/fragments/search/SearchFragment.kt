package com.example.movery.presentation.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movery.R
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.RatingBar
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movery.data.model.Movie
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class SearchFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var searchView: SearchView
    private lateinit var chipGroup: ChipGroup
    private lateinit var movieList: MutableList<Movie>
    private lateinit var filteredMovies: List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewSecond)
        searchView = view.findViewById(R.id.idSV)
        chipGroup = view.findViewById(R.id.ChipGroup)

        adapter = MovieAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        movieList = mutableListOf()
        movieList.add(Movie("Начало", 8.8, "Боевик, Приключения, Фантастика", R.drawable.inception, "false", "Вор, способный воровать секреты корпораций с помощью технологии совместного сна, получает обратную задачу - внушить идею главе компании."))
        movieList.add(Movie("Темный рыцарь", 9.0, "Боевик, Криминал, Драма", R.drawable.the_dark_knight, "false", "Когда маньяк, известный как Джокер, наводит хаос на жителей Готэма, Бэтмен должен пройти одно из своих самых серьезных испытаний - психологическое и физическое."))
        movieList.add(Movie("ВАЛЛ·И", 8.3, "Мультфильм, Комедия, Семейный", R.drawable.test, "false", "ВАЛЛ·И (от англ. Waste Allocation Load Lifter Earth-Class) - последний робот на Земле. Он убирает планету, собирая мусор. Но за 700 лет ВАЛЛ·И приобрел личность и стал чувствовать себя очень одиноко. И тогда он замечает Еву (Элисса Найт), элегантного и стройного робота, отправленного на Землю для исследования. Влюбленный ВАЛЛ·И начинает свое самое большое приключение, следуя за Евой через галактику."))
        movieList.add(Movie("12 разгневанных мужчин", 9.0, "Драма", R.drawable.twelve_angry_men, "false", "Член жюри пытается предотвратить нарушение справедливости, заставляя своих коллег пересмотреть доказательства."))
        movieList.add(Movie("Криминальное чтиво", 8.9, "Криминал, Драма", R.drawable.pulp_fiction, "false", "Жизни двух киллеров мафии, боксера, гангстера и его жены, а также двух грабителей-одиночек переплетаются в четырех историях насилия и искупления."))
        movieList.add(Movie("Форрест Гамп", 8.8, "Драма, Романтика", R.drawable.forrest_gump, "false", "Президентство Кеннеди и Джонсона, события Вьетнама, Уотергейта и другие исторические события раскрываются через призму взгляда человека из Алабамы с IQ 75, единственным желанием которого является воссоединение с возлюбленной детства."))
        filteredMovies = movieList

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText)
                return false
            }
        })
        setupChipGroup()
        return view
    }

    private fun setupChipGroup() {
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val genre = chip.text.toString()

            filteredMovies = if (genre.isNotEmpty()) {
                movieList.filter { it.genre.contains(genre, ignoreCase = true) }
            } else {
                movieList
            }
            adapter.setMovies(filteredMovies)
        }
    }

    inner class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
        private var movies = listOf<Movie>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_popular_movie, parent, false)
            return MovieViewHolder(view)
        }

        override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
            val movie = movies[position]
            holder.bind(movie)
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        fun setMovies(movieList: List<Movie>) {
            movies = movieList
            notifyDataSetChanged()
        }

        fun filter(query: String?) {
            val filteredList = if (query.isNullOrEmpty()) {
                filteredMovies
            } else {
                filteredMovies.filter { it.title.contains(query, ignoreCase = true) }
            }
            setMovies(filteredList)
        }

        inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
            private val genreTextView: TextView = itemView.findViewById(R.id.textGenre)
            private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
            private val posterImageView: ImageView = itemView.findViewById(R.id.imageViewPoster)
            private val description: TextView = itemView.findViewById(R.id.textViewDescription)

            fun bind(movie: Movie) {
                titleTextView.text = movie.title
                genreTextView.text = movie.genre
                ratingBar.rating = movie.rating.toFloat()
                posterImageView.setImageResource(movie.imageResId)
                description.text = movie.description
            }
        }
    }
    data class Movie(
        val title: String,
        val rating: Double,
        val genre: String,
        val imageResId: Int,
        val isFavorite: String,
        val description: String
    )
}