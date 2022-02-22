package com.o3.o3interfacestest.presentaion.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding>(val bindingInflater: (LayoutInflater) -> VB) :Fragment() {

    private  val TAG = "AppBaseFragment"
    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB get() = _binding as VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(layoutInflater)


        return requireNotNull(_binding).root
    }


    private var progressDialog: ProgressDialog? = null

    fun showProgressDialog(msg:String="Please Wait") {
        progressDialog = progressDialog ?: ProgressDialog(context)
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage(msg)
        progressDialog?.show()
    }

    fun hideProgress() {
        progressDialog?.dismiss()
    }
}