package com.o3.o3interfacestest.presentaion.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.o3.o3interfacestest.R
import com.o3.o3interfacestest.common.Response
import com.o3.o3interfacestest.common.showLog
import com.o3.o3interfacestest.common.showToast
import com.o3.o3interfacestest.databinding.FragmentHistoryBinding
import com.o3.o3interfacestest.domain.model.User
import com.o3.o3interfacestest.presentaion.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate), (User) -> Unit {
    private val viewModel by viewModels<HistoryFragmentViewModel>()
    private var adapter = HistoryFragmentAdapter().apply {
        delUserCallback = this@HistoryFragment
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.adapter = adapter
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiUpdates.collectLatest {
                    when (it) {
                        is Response.Error -> {
                            showLog("Error")
                        }
                        is Response.Idle -> {
                            showLog("Ideal")
                        }
                        is Response.Loading -> {
                            showDialog()
                            showLog("Loading")
                        }
                        is Response.Success -> {
                            dismissDialog()
                            if ((it.data.isNullOrEmpty()) and (it.data!!.isEmpty())) {
                                requireContext().showToast("No Data Found")
                                adapter.submitList(null)

                            } else {
                                adapter.submitList(it.data)
                            }
                        }
                    }
                }
            }
        }
        viewModel.viewModelScope.launch {
            viewModel.getDataFromLocalDb()
        }
    }

    private fun showDialog() {
        viewModel.viewModelScope.launch {
            withContext(Dispatchers.Main) {
                showProgressDialog()
            }
        }
    }

    private fun dismissDialog() {
        viewModel.viewModelScope.launch {
            withContext(Dispatchers.Main) {
                hideProgress()
            }
        }
    }

    override fun invoke(userData: User) {
        viewModel.deleteUser(userData)
    }
}