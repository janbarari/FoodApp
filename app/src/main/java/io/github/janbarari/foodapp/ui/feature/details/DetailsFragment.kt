package io.github.janbarari.foodapp.ui.feature.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import io.github.janbarari.R
import io.github.janbarari.databinding.FragmentDetailsBinding
import io.github.janbarari.foodapp.helper.show
import io.github.janbarari.foodapp.ui.base.BaseFragment
import io.github.janbarari.foodapp.ui.common.util.ImageLoaderContext
import io.github.janbarari.foodapp.ui.common.viewmodel.DRINK_THUMBNAIL_URL
import io.github.janbarari.foodapp.ui.common.viewmodel.DRINK_TITLE
import io.github.janbarari.foodapp.ui.common.viewmodel.DRINK_ID
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import kotlin.math.abs

const val COLLAPSING_EMPTY_TITLE = " "

class DetailsFragment : BaseFragment(), KodeinAware, IDetailsFragment {

    override val kodein: Kodein by kodein()
    private val factory: DetailsViewModelFactory by instance<DetailsViewModelFactory>()

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding

    private var headerImageUrl: String? = null
    private var title: String? = null
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.listener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        headerImageUrl = arguments?.getString(DRINK_THUMBNAIL_URL)
        title = arguments?.getString(DRINK_TITLE)
        id = arguments?.getString(DRINK_ID)

        binding.headerText.text = title
        ViewCompat.setTransitionName(binding.headerImage, "image_$id")

        initializeAppBar()

        headerImageUrl?.let {
            ImageLoaderContext.imageLoader.bind(binding.headerImage, it)
        }

        viewModel.drink.observe(viewLifecycleOwner, Observer {
            viewModel.progressState.postValue(false)
            binding.description.text = it.strInstructions
        })

        id?.let {
            viewModel.progressState.postValue(true)
            viewModel.fetchIngredient(it)
        }
    }

    private fun initializeAppBar() {
        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                title?.let {
                    binding.collapsingToolbar.title = title
                }
            } else {
                binding.collapsingToolbar.title = COLLAPSING_EMPTY_TITLE
            }
        })
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onError(e: Exception) {
        viewModel.progressState.postValue(false)
        e.message?.let {
            binding.root.show(it)
        }
    }

}