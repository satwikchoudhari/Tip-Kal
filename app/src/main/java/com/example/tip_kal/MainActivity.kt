package com.example.tip_kal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView


private const val TAG ="MainActivity"
private const val INITIAL_TIP_PERCENT =15

class MainActivity : AppCompatActivity() {
    private lateinit var et_baseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tv_percent: TextView
    private lateinit var tv_tip: TextView
    private lateinit var tv_total: TextView
    private lateinit var tv_info: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_baseAmount = findViewById(R.id.et_baseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tv_percent = findViewById(R.id.tv_percent)
        tv_tip = findViewById(R.id.tv_tip)
        tv_total = findViewById(R.id.tv_total)
        tv_info = findViewById(R.id.tv_info)



        seekBarTip.progress = INITIAL_TIP_PERCENT
        tv_percent.text = "$INITIAL_TIP_PERCENT%"
        updateTipInfo(INITIAL_TIP_PERCENT)
        

        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged $p1")
                tv_percent.text = "$p1%"
                computeTipAndTotal()
                updateTipInfo(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        et_baseAmount.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged $p0")
                computeTipAndTotal()

            }

        })
    }

    private fun updateTipInfo(p1: Int) {
        val tip_reaction = when (p1){
            in 0..9 -> "\uD83E\uDD26"
            in 10..14-> "\uD83D\uDE4D\u200D♂️"
            in 15..19 -> "\uD83D\uDE4E"
            in 20..24 -> "\uD83D\uDE46\u200D♂️"
            else -> "\uD83D\uDE47\u200D♂️"
        }
        tv_info.text = tip_reaction
    }

    private fun computeTipAndTotal() {
        if (et_baseAmount.text.isEmpty()){
            tv_tip.text = ""
            tv_total.text = ""
            return
        }
        // 1. get the value of base amount and tip percent
        val baseAmount = et_baseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress
        // 2. compute the tip and total
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount =  tipAmount + baseAmount
        // 3. update the UI
        tv_tip.text = "%.2f".format(tipAmount)
        tv_total.text = "%.2f".format(totalAmount)
    }
}