package io.github.janbarari.foodapp.ui.feature.search

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import io.github.janbarari.foodapp.data.database.entity.Drink
import io.github.janbarari.databinding.FragmentSearchBinding
import io.github.janbarari.foodapp.helper.forceClearFocus
import io.github.janbarari.foodapp.ui.base.BaseFragment
import io.github.janbarari.foodapp.ui.common.mapper.toDrinkViewModel
import io.github.janbarari.foodapp.ui.common.model.DrinkViewEvent
import io.github.janbarari.foodapp.ui.common.viewholder.DrinkViewHolder
import io.github.janbarari.foodapp.ui.common.viewmodel.DrinkViewModel
import io.github.janbarari.genericrecyclerview.adapter.GenericAdapter
import io.github.janbarari.genericrecyclerview.listener.GenericUriListener
import kotlinx.android.synthetic.main.drink_adapter_cell.view.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import kotlin.math.abs
import android.widget.ArrayAdapter
import io.github.janbarari.R
import io.github.janbarari.foodapp.helper.hide
import io.github.janbarari.foodapp.helper.show

const val DEFAULT_SEARCH_KEYWORD = "Gin"

class SearchFragment : BaseFragment(), KodeinAware, ISearchFragment, GenericUriListener, androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {

    override val kodein: Kodein by kodein()
    private val factory: SearchViewModelFactory by instance<SearchViewModelFactory>()

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.listener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeSearchBox()

        initializeAppBarBehaviour()

        viewModel.drinks.observe(viewLifecycleOwner, Observer {
            initializeRecyclerView(it)
            viewModel.progressState.postValue(false)
        })

        if (viewModel.drinks.value == null) {
            viewModel.searchFor(DEFAULT_SEARCH_KEYWORD)
        }

    }

    private fun initializeSearchBox() {

        binding.toolbar.setOnMenuItemClickListener(this)

        binding.searchEditText.postDelayed({
            activity?.forceClearFocus(binding.searchEditText)
        }, 300)
        context?.let {context ->
            viewModel.ingredients.observe(viewLifecycleOwner, Observer {
                val adapter = ArrayAdapter<String>(context, android.R.layout.select_dialog_item, it)
                binding.searchEditText.threshold = 1
                binding.searchEditText.setAdapter(adapter)
            })
        }

        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                activity?.forceClearFocus(binding.searchEditText)
                viewModel.progressState.postValue(true)
                if(v.text.isEmpty()) {
                    viewModel.searchFor(DEFAULT_SEARCH_KEYWORD)
                } else {
                    viewModel.searchFor(v.text.toString())
                }
            }
            false
        }

    }

    private fun initializeAppBarBehaviour() {
        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                binding.header.hide()
            } else {
                binding.header.show()
            }
        })
    }


    private fun initializeRecyclerView(drinks: List<Drink>) {
        context?.let {
            binding.recyclerView.apply {
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
                val adapter = GenericAdapter(it, drinks.toDrinkViewModel(), this@SearchFragment)
                adapter.addView(
                    DrinkViewHolder::class.java,
                    DrinkViewModel::class.java,
                    R.layout.drink_adapter_cell
                )
                this.layoutManager = LinearLayoutManager(it, LinearLayoutManager.VERTICAL, false)
                this.adapter = adapter
            }
            if (viewModel.recyclerViewState != null) {
                (binding.recyclerView.layoutManager as LinearLayoutManager).onRestoreInstanceState(viewModel.recyclerViewState)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.recyclerViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
    }

    override fun onClick(event: Any) {
        val clickedEvent = event as DrinkViewEvent

        if (clickedEvent.viewModel.idDrink != null &&
            clickedEvent.viewModel.strDrinkThumb != null &&
            clickedEvent.viewModel.strDrink != null
        ) {

            val directions = SearchFragmentDirections.viewDrinkDetails(
                clickedEvent.viewModel.idDrink!!,
                clickedEvent.viewModel.strDrinkThumb!!,
                clickedEvent.viewModel.strDrink!!
            )

            val extras = FragmentNavigatorExtras(clickedEvent.root.image to "image_${clickedEvent.viewModel.idDrink}")

            findNavController().navigate(directions, extras)
        }

    }

    override fun onError(e: Exception) {
        viewModel.progressState.postValue(false)
        e.message?.let { binding.root.show(it) }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.settingsMenu -> {
                findNavController().navigate(R.id.settingsFragment)
                return true
            }
        }
        return false
    }

}