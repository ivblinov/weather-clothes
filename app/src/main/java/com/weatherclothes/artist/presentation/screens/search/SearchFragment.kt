package com.weatherclothes.artist.presentation.screens.search

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.text.style.TextAppearanceSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.weatherclothes.artist.databinding.FragmentSearchBinding
import com.weatherclothes.artist.presentation.screens.search.recyclerView.SearchAdapter
import com.weatherclothes.artist.presentation.states.MainState
import com.weatherclothes.artist.R
import com.weatherclothes.artist.utils.appComponent
import com.weatherclothes.artist.utils.lazyViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.getValue

private const val TAG = "MyLog"
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    val viewModel: SearchViewModel by lazyViewModel {
        requireContext().appComponent().searchViewModel().create()
    }

    private var  textMainEditTextSpan: TextAppearanceSpan? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        textMainEditTextSpan = TextAppearanceSpan(requireContext(), R.style.TextMain_EditText)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribe()

        binding.searchRV.adapter = SearchAdapter(
            styleBold = textMainEditTextSpan,
            onClickItem = viewModel::clickItem
        )

        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.length?.let {
                    hideHint(it)
                }
                s?.let {
                    viewModel.query = it.toString()
                    viewModel.getSearchLocation(it.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.microphone.setOnClickListener {
            startVoiceInput()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun inject() {
        requireContext().appComponent().inject(this)
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.searchState.collect { state ->
                        when (state) {
                            MainState.Loading -> {}
                            MainState.Success -> {
                                getSearchAdapter().setList(
                                    viewModel.query,
                                    viewModel.places,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Name the place...")
        }

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "Voice input is not supported on this device",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!result.isNullOrEmpty()) {
                binding.search.setText(result[0])
            }
        }
    }

    private fun hideHint(length: Int) {
        if (length > 0)
            binding.hint.visibility = View.INVISIBLE
        else
            binding.hint.visibility = View.VISIBLE
    }

    private fun getSearchAdapter(): SearchAdapter = binding.searchRV.adapter as SearchAdapter

    companion object {
        private const val REQUEST_CODE_SPEECH_INPUT = 100
    }
}