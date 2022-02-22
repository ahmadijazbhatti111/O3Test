package com.o3.o3interfacestest.presentaion.ui.result

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.o3.o3interfacestest.common.showLog
import com.o3.o3interfacestest.databinding.FragmentResultBinding
import com.o3.o3interfacestest.domain.model.User
import com.o3.o3interfacestest.presentaion.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResultFragment : BaseFragment<FragmentResultBinding>(FragmentResultBinding::inflate) {
    private val args: ResultFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!args.transfereddata.isNullOrEmpty()) {
            showLog( "onViewCreated: " + args.transfereddata)
            val user = args.transfereddata!!.toList()[0]
            updateUi(user)
        }
    }

    private fun updateUi(user: User) {
        with(binding) {
           model = user
        }
    }
}