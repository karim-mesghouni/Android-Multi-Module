package com.emamagic.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.emamagic.common_jvm.GenreCategory
import com.emamagic.common_jvm.MovieCategory
import com.emamagic.core.base.BaseFragmentRedux
import com.emamagic.core.extension.findComponent
import com.emamagic.core.extension.gone
import com.emamagic.core.utils.Logger
import com.emamagic.home.contract.HomeAction
import com.emamagic.home.contract.HomeState
import com.emamagic.home.databinding.FragmentHomeBinding
import com.emamagic.home.di.HomeComponentProvider

class HomeFragment :
    BaseFragmentRedux<FragmentHomeBinding, HomeState, HomeAction, HomeViewModel>() {

    override val viewModel: HomeViewModel
        get() = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

    override fun getResId(): Int = R.layout.fragment_home


    override fun onFragmentCreated(view: View, savedInstanceState: Bundle?) {
        findComponent<HomeComponentProvider>().provideHomeComponent().inject(this)
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        val dummyTimeout = 4000L
        Handler(Looper.getMainLooper()).postDelayed({
            hideShimmer()
        }, dummyTimeout)
    }


    override fun renderViewState(viewState: HomeState) {
        binding.recyclerViewTopMovieImdb.withModels {
            viewState.topImdbMovies.forEach { topMovie ->
                movieV {
                    id(topMovie.id)
                    movie(topMovie)
                    onClickListener { _ ->
                        movieClicked(topMovie.categoryName!!)
                    }
                }
            }
        }
        binding.recyclerViewSeries.withModels {
            viewState.series.forEach { movie ->
                movieH {
                    id(movie.id)
                    movie(movie)
                    onClickListener { _ ->
                        movieClicked(movie.categoryName!!)
                    }
                }
            }
        }
        binding.recyclerViewAnimation.withModels {
            viewState.animations.forEach { animation ->
                movieV {
                    id(animation.id)
                    movie(animation)
                    onClickListener { _ ->
                        movieClicked(animation.categoryName!!)
                    }
                }
            }
        }
        binding.recyclerViewPopularMovie.withModels {
            viewState.popularMovies.forEach { papular ->
                movieH {
                    id(papular.id)
                    movie(papular)
                    onClickListener { _ ->
                        movieClicked(papular.categoryName!!)
                    }
                }
            }

        }
        binding.recyclerViewGenre.withModels {
            viewState.genres.forEach { genre ->
                genre {
                    id(genre.id)
                    genre(genre)
                    onClickListener { _ ->
                        genreClicked(genre.name)
                    }
                }
            }
        }
    }

    private fun movieClicked(@MovieCategory category: String) {
        viewModel.movieClickEvent(category)
    }

    private fun genreClicked(@GenreCategory category: String) {
        viewModel.genreClickEvent(category)
    }

    private fun hideShimmer() {
        binding.shimmerFrameLayoutGenre.hideShimmer()
        binding.shimmerFrameLayoutTop.hideShimmer()
        binding.shimmerFrameLayoutPopular.hideShimmer()
        binding.shimmerFrameLayoutAnim.hideShimmer()
        binding.shimmerFrameLayoutSeries.hideShimmer()
        binding.shimmerFrameLayoutGenre.gone()
        binding.shimmerFrameLayoutTop.gone()
        binding.shimmerFrameLayoutPopular.gone()
        binding.shimmerFrameLayoutAnim.gone()
        binding.shimmerFrameLayoutSeries.gone()
    }

    private fun setUpRecyclerView() {
        binding.recyclerViewTopMovieImdb.setHasFixedSize(true)
        binding.recyclerViewSeries.setHasFixedSize(true)
        binding.recyclerViewAnimation.setHasFixedSize(true)
        binding.recyclerViewPopularMovie.setHasFixedSize(true)
        binding.recyclerViewGenre.setHasFixedSize(true)
    }

}