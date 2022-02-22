package com.o3.o3interfacestest.presentaion.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.o3.o3interfacestest.R
import com.o3.o3interfacestest.common.Response
import com.o3.o3interfacestest.common.showLog
import com.o3.o3interfacestest.common.showToast
import com.o3.o3interfacestest.databinding.FragmentHomeBinding
import com.o3.o3interfacestest.domain.model.UserResponse
import com.o3.o3interfacestest.presentaion.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel by activityViewModels<HomeFragmentViewModel>()

    private var currentIndex: Int = -1
    private var currentCountry = "US"

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnsearch.setOnClickListener {
            viewModel.viewModelScope.launch {
                if (!TextUtils.isEmpty(binding.tveText.text.toString())) {
                        viewModel.searchAge(UserResponse().apply {
                            userName = binding.tveText.text.toString()
                            viewModel.currUserName = userName
                        })
                } else {
                    requireContext().showToast("Enter Name")
                }
            }
        }

        observeData()
        binding.tveText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.btnsearch.callOnClick()
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun validateCountry(): String? {
        return try {
            currentCountry = resources.getStringArray(R.array.countrycodes)[currentIndex]
            currentCountry
        } catch (e: Exception) {
            null
        }
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiUpdates.collectLatest { resModel ->
                    when (resModel) {
                        is Response.Error -> {
                            showLog("observeData: Error")
                        }
                        is Response.Idle -> {
                            showLog("observeData: Idle")
                        }
                        is Response.Loading -> {
                            showProgressDialog()
                            showLog("observeData: Loading")
                        }
                        is Response.Success -> {
                            hideProgress()
                            val dir = HomeFragmentDirections.actionMainFragmentToResultFragment()
                            showLog(resModel.data!!.size.toString())
                            if (resModel.data.isNotEmpty()) {
                                val list = resModel.data.map {
                                    it!!
                                }.toTypedArray()
                                showLog(list.size.toString())
                                dir.transfereddata = list
                                findNavController().navigate(dir)
                                viewModel.markIdleState()
                            } else {
                                requireContext().showToast("No Data Found ")
                            }
                            showLog("observeData: Success")
                        }
                    }
                }
            }
        }
    }
}