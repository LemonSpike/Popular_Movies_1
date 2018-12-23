package com.kasetti.pranav.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kasetti.pranav.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {
    private List<String> mMovieURLStrings;
    private List<Movie> mMovies;

    private final MoviesAdapterOnClickHandler mClickHandler;

    interface MoviesAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MoviesAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    /**
     * Cache of the children views for a movie grid item.
     */
    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mMovieImageView;

        MoviesAdapterViewHolder(View view) {
            super(view);
            mMovieImageView = view.findViewById(R.id.iv_movie);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onClick(mMovies.get(getAdapterPosition()));
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new MoviesAdapterViewHolder that holds the View for each list item
     */
    @NonNull
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new MoviesAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param moviesAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MoviesAdapterViewHolder moviesAdapterViewHolder, int position) {
        String movieURLString = mMovieURLStrings.get(position);
        ImageView movieImageView = moviesAdapterViewHolder.mMovieImageView;
        Picasso.get().load(movieURLString).placeholder(R.drawable.placeholder).into(movieImageView);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of movies available in our app
     */
    @Override
    public int getItemCount() {
        if (null == mMovieURLStrings) return 0;
        return mMovieURLStrings.size();
    }

    /**
     * This method is used to set the weather forecast on a MoviesAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MoviesAdapter to display it.
     *
     * @param movies The movies data to be displayed.
     * @param moviesURLStrings The new movie image url data to be displayed.
     */
    public void setMoviesData(List<Movie> movies, List<String> moviesURLStrings) {
        mMovieURLStrings = moviesURLStrings;
        mMovies = movies;
        notifyDataSetChanged();
    }
}
