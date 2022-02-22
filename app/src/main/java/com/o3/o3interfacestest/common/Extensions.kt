package com.o3.o3interfacestest.common

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList


inline fun <reified T> T.showLog(msg : String){
    Log.e(T::class.simpleName, msg)
}

fun Context.showToast(msg:String){
    Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
}
