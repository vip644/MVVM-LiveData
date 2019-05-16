package com.vipin.livedata

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vipin.livedata.livedatademo.LiveData
import kotlinx.android.synthetic.main.data_fragments.*
import java.lang.Exception

/**
 * Created by vipin.c on 15/05/2019
 */
class DataFragment : Fragment(), View.OnClickListener {

    companion object {
        private val liveData = LiveData<Int?>()

        init {
            liveData.setValue(0)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.data_fragments, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addObserver()
        text_view.setOnClickListener(this)
        add_observer_button.setOnClickListener(this)
        remove_observer_button.setOnClickListener(this)
    }

    private val observer:(Int?) -> Unit = {
        if (it != liveData.getValue()) throw Exception()
        text_view.text = it.toString()
    }

    private fun addObserver() {
        liveData.addObserver(observer, this)
        text_view.setTextColor(Color.GREEN)

    }

    private fun removeObserver() {
        liveData.removeObserver(observer)
        text_view.setTextColor(Color.RED)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.text_view -> liveData.setValue(liveData.getValue()?.plus(1))
            R.id.add_observer_button -> addObserver()
            R.id.remove_observer_button -> removeObserver()
        }
    }

}