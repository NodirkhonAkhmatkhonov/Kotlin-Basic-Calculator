package com.example.kotlinproject2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var btnZero: Button
    private lateinit var btnOne: Button
    private lateinit var btnTwo: Button
    private lateinit var btnThree: Button
    private lateinit var btnFour: Button
    private lateinit var btnFive: Button
    private lateinit var btnSix: Button
    private lateinit var btnSeven: Button
    private lateinit var btnEight: Button
    private lateinit var btnNine: Button
    private lateinit var btnPercentage: Button
    private lateinit var btnClearAll: Button
    private lateinit var btnPoint: Button

    private lateinit var btnBackSpace: ImageButton
    private lateinit var btnRestore: ImageView
    private lateinit var btnDivision: ImageView
    private lateinit var btnMultiply: ImageView
    private lateinit var btnSubtract: ImageView
    private lateinit var btnAdd: ImageView
    private lateinit var btnEquals: ImageView

    private var lastState = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        declareButtons()
    }

    private fun declareButtons() {
        btnZero = findViewById(R.id.btnZero)
        btnOne = findViewById(R.id.btnOne)
        btnTwo = findViewById(R.id.btnTwo)
        btnThree = findViewById(R.id.btnThree)
        btnFour = findViewById(R.id.btnFour)
        btnFive = findViewById(R.id.btnFive)
        btnSix = findViewById(R.id.btnSix)
        btnSeven = findViewById(R.id.btnSeven)
        btnEight = findViewById(R.id.btnEight)
        btnNine = findViewById(R.id.btnNine)
        btnPercentage = findViewById(R.id.btnPercentage)
        btnClearAll = findViewById(R.id.allClear)
        btnPoint = findViewById(R.id.btnPoint)

        btnBackSpace = findViewById(R.id.btnBackspace)
        btnRestore = findViewById(R.id.btnRestore)
        btnDivision = findViewById(R.id.btnDivide)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnSubtract = findViewById(R.id.btnSubtract)
        btnAdd = findViewById(R.id.btnAdd)
        btnEquals = findViewById(R.id.btnEquals)

        btnZero.setOnClickListener(this)
        btnOne.setOnClickListener(this)
        btnTwo.setOnClickListener(this)
        btnThree.setOnClickListener(this)
        btnFour.setOnClickListener(this)
        btnFive.setOnClickListener(this)
        btnSix.setOnClickListener(this)
        btnSeven.setOnClickListener(this)
        btnEight.setOnClickListener(this)
        btnNine.setOnClickListener(this)
        btnPercentage.setOnClickListener(this)
        btnClearAll.setOnClickListener(this)
        btnPoint.setOnClickListener(this)

        btnBackSpace.setOnClickListener(this)
        btnRestore.setOnClickListener(this)
        btnDivision.setOnClickListener(this)
        btnMultiply.setOnClickListener(this)
        btnSubtract.setOnClickListener(this)
        btnAdd.setOnClickListener(this)
        btnEquals.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val view = p0 as View

        var onDisplay = textView.text.toString()

        if (onDisplay.matches(".*[+-.x/]".toRegex())) {
            onDisplay = onDisplay.dropLast(1)
        }

        when ((p0 as View).id) {
            R.id.allClear -> {
                textView.text = "0"
            }
            R.id.btnBackspace -> {
                if (textView.text.length == 1) {
                    textView.text = "0"
                } else {
                    textView.text = textView.text.dropLast(1)
                }
            }
            R.id.btnEquals -> {
                lastState = textView.text.toString()

                calculate()
            }
            R.id.btnPercentage -> {
                percentage(onDisplay)
            }
            R.id.btnRestore -> {
                textView.text = lastState
            }

            // Operators
            R.id.btnDivide -> {
                onOperatorClicked("/", onDisplay)
            }
            R.id.btnMultiply -> {
                onOperatorClicked("x", onDisplay)
            }
            R.id.btnSubtract -> {
                onOperatorClicked("-", onDisplay)
            }
            R.id.btnAdd -> {
                onOperatorClicked("+", onDisplay)
            }
            R.id.btnPoint -> {
                onOperatorClicked(".", onDisplay)
            }

            // Numbers
            R.id.btnZero -> {
                onNumbersClicked("0")
            }
            R.id.btnOne -> {
                onNumbersClicked("1")
            }
            R.id.btnTwo -> {
                onNumbersClicked("2")
            }
            R.id.btnThree -> {
                onNumbersClicked("3")
            }
            R.id.btnFour -> {
                onNumbersClicked("4")
            }
            R.id.btnFive -> {
                onNumbersClicked("5")
            }
            R.id.btnSix -> {
                onNumbersClicked("6")
            }
            R.id.btnSeven -> {
                onNumbersClicked("7")
            }
            R.id.btnEight -> {
                onNumbersClicked("8")
            }
            R.id.btnNine -> {
                onNumbersClicked("9")
            }
        }
    }

    private fun percentage(onDisplay: String) {
            textView.text = (onDisplay.toDouble() / 100).toString()
    }

    private fun calculate() {
        var onDisplay = textView.text.toString()

        var isFirstNegative = false
        if (onDisplay.startsWith("-"))
        {
            onDisplay = onDisplay.drop(1)
            isFirstNegative = true
        }

        val subList = onDisplay.split("[+-]".toRegex()).toMutableList()

        val numbers = arrayListOf<Double>()

        var result = 0.0

        for (item in subList) {
            if (item.contains("[x/]".toRegex())) {
                numbers.add(test(item))
            } else {
                numbers.add(item.toDouble())
            }
        }

        var pointer = 1
        for (item in onDisplay.toCharArray()) {
            if (item.toString() == "+") {
                result += numbers[pointer]
                pointer ++
            } else if (item.toString() == "-") {
                result -= numbers[pointer]
                pointer ++
            }
        }

        if (isFirstNegative)
            result -= numbers[0]
        else
            result += numbers[0]

        var stringResult = result.toString()

        if (stringResult.endsWith(".0")) {
            textView.text = result.toInt().toString()
        }
        else {
            if (stringResult.length > 15)
                stringResult = stringResult.subSequence(0, 15).toString()

            textView.text = stringResult
        }
    }

    private fun test(s: String): Double {
        if (s.contains("x")) {
            val nums = s.split("x")
            return nums.get(0).toDouble() * nums.get(1).toDouble()
        } else {
            val nums = s.split("/")
            return nums.get(0).toDouble() / nums.get(1).toDouble()
        }
    }

    private fun onOperatorClicked(operator: String, onDisplay: String) {

        if (onDisplay != "0" && operator != ".") {
            textView.text = "$onDisplay$operator"
        } else if (onDisplay != "0"){
            for (i in onDisplay.lastIndex downTo 0) {
                if ("x/+-".contains(onDisplay.elementAt(i))) {
                    if (!onDisplay.substring(i).contains('.')) {
                        textView.text = "$onDisplay$operator"
                        break
                    }
                } else if (!onDisplay.contains('.')) {
                    textView.text = "$onDisplay$operator"
                    break
                }
            }
        }
    }

    private fun onNumbersClicked(arg: String) {

        var onDisplay = textView.text

        if (onDisplay == "0")
            onDisplay = ""

        textView.text = "$onDisplay$arg"
    }
}
