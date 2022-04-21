package com.example.cn333as4.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cn333as4.R

class LengthViewModel: ViewModel() {
    private val _cm: MutableLiveData<Int> = MutableLiveData(R.string.cen)

    val cm: LiveData<Int>
        get() = _cm

    fun setCm(value: Int) {
        _cm.value = value
    }

    private val _length: MutableLiveData<String> = MutableLiveData("")

    val length: LiveData<String>
        get() = _length

    fun getLengthAsFloat(): Float = (_length.value ?: "").let {
        return try {
            it.toFloat()
        } catch (e: NumberFormatException) {
            Float.NaN
        }
    }

    fun setLength(value: String) {
        _length.value = value
    }

    fun convert() = getLengthAsFloat().let {
        if (!it.isNaN())
            if (_cm.value == R.string.cen)
                it / 2.54F
            else
                it * 2.54F
        else
            Float.NaN
    }
}

