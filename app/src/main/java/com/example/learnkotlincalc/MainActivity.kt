package com.example.learnkotlincalc
import android.app.Activity
import net.objecthunter.exp4j.ExpressionBuilder
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.core.view.*

import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged

import kotlinx.coroutines.*
import kotlinx.coroutines.android.awaitFrame
import net.objecthunter.exp4j.operator.Operator
import kotlin.math.sqrt





class MainActivity : Activity() {
    lateinit var content: ConstraintLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val t = findViewById<LinearLayout>(R.id.linear)


        val gridContent = findViewById<GridLayout>(R.id.gridLayout)
        val inputView = findViewById<TextView>(R.id.sub_value)





        content = findViewById(R.id.content)

        val result = findViewById<TextView>(R.id.result)




        inputView.doAfterTextChanged { text -> run {
            try{



                var strSequence: String = ""

                var builder = StringBuilder()
                builder.append(text)
                val ev = ExpressionBuilder(text.toString()).build()

                val value = ev.evaluate()
                val longer = value.toLong()
                if (value == longer.toDouble()){
                    result.text = longer.toString()
                } else {
                    result.text =  value.toString()
                }
            }catch(Error: Exception) {
                result.text = ""
            }
         }
        }


        inputView.setOnFocusChangeListener { view: View, b: Boolean ->
            hidden()

        }

        inputView.setOnClickListener {
            hidden()
        }


        for (id in gridContent.children.iterator()){
                findViewById<Button?>(id.id)?.setOnClickListener {
                    clickEvent(it,inputView,result)
                    reset(inputView,result,it)
                    operationSpecial(it,result,inputView)
                }


        }




    }



    private fun changesValue(text: String,str: String, newChar:String): String {
        var strSequence = ""
        var i = 0

        while (text.length > i){
            i++
        }
       return strSequence
    }

    override fun onBackPressed() {
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }


    private fun clickEvent(event: View,text:TextView,result: TextView) {

        if(event.id == R.id.button_1
                || event.id == R.id.button_2
                || event.id == R.id.button_3
                || event.id == R.id.button_4
                || event.id == R.id.button_5
                || event.id == R.id.button_6
                || event.id == R.id.button_7
                || event.id == R.id.button_8
                || event.id == R.id.button_9
                )
        {
            val str = StringBuilder()

            val t =event.findViewById<Button>(event.id)
            str.append(t.text.toString())

            val s =  text.text.toString().plus(str.toString())



            text.text =  s



        }else if(event.id == R.id.button_0 ||event.id == R.id.dot) {
            val str = StringBuilder()

            val t =event.findViewById<Button>(event.id)


            val s =  text.text.toString().plus(str.toString())


            if (s.startsWith('0')|| s.startsWith(".") || s.endsWith('.') ){
                text.text  = removeConsecutiveCharactersInString(s,1)

            } else {
                text.text = s

            }






        } else if(event.id == R.id.sum ||event.id == R.id.subtraction ) {
            val str = StringBuilder()

            val t =event.findViewById<Button>(event.id)

            str.append(t.text.toString())
            var s =  text.text.toString().plus(str.toString())
            text.text = removeConsecutiveCharactersInString(s,1)


        }else if(event.id == R.id.division || event.id == R.id._multiply){
            val str = StringBuilder()
            val t =event.findViewById<Button>(event.id)
            str.append(t.text.toString())
            var c = ""


            removeConsecutiveCharactersInString(str.toString(),1);
            var s =  text.text.toString().plus(str.toString())

            text.text = s.replace('÷','/').replace('×','*')

        } else if (event.id == R.id.button_deleted) {
                try {
                    if (text.text.isNotEmpty()){
                        text.text = text.text.toString().substring(0,text.text.toString().length-1)
                    }else {
                        text.text = ""
                    }

                }catch (error:StringIndexOutOfBoundsException){
                    Toast.makeText(this@MainActivity,error.toString(),Toast.LENGTH_SHORT);

                }


        }else if(event.id == R.id.button_equal) {
            if (text.text.isNotEmpty()){
                try {


                    val ev = ExpressionBuilder(text.text.toString()).build()
                    val value = ev.evaluate()
                    text.text = ""

                    val longer = value.toLong()
                    if (value == longer.toDouble()){
                        result.text = longer.toString()
                    } else {
                        result.text = value.toString()
                    }

                }catch (Error:ArithmeticException) {
                    Toast.makeText(this@MainActivity, Error.toString(),Toast.LENGTH_SHORT).show()
                }catch (Error: Exception) {
                    Toast.makeText(this@MainActivity, Error.toString(),Toast.LENGTH_SHORT).show()
                }

            }

        }




    }

    private fun reset (text: TextView,result: TextView, type:View) {
        when(type.id){
            R.id.button_reset -> {
                text.text = ""
                result.text = ""
            }
        }

    }

    private fun operationSpecial(evt: View, result:TextView, input: TextView) {
         when(evt.id) {
            R.id.button_square -> {
                try {
                    if (input.text.isNotEmpty()){

                        val str = StringBuilder()
                        val t =evt.findViewById<Button>(evt.id)
                        str.append(t.text.toString())
                        val s =  input.text.toString().plus(str.toString())

                        input.text = s

                        val ev = ExpressionBuilder(input.text.toString()).build()
                        var value = ev.evaluate()

                        //value *= value;
                        val longer = value.toLong()
                        if (value == longer.toDouble()){
                            result.text = longer.toString()
                        } else {
                            result.text = value.toString()
                        }


                    }
                }catch (Error: Exception) {
                    Toast.makeText(this@MainActivity, "Invalid operation",Toast.LENGTH_SHORT).show()
                    result.text = "Invalid operation"
                }


            }
            R.id.root_square -> {
                try {
                    if (input.text.isNotEmpty()){
                        val ev = ExpressionBuilder(input.text.toString()).build()
                        val value = ev.evaluate()
                        result.text = sqrt(value).toString()
                    }
                }catch (Error: Exception) {
                    Toast.makeText(this@MainActivity, "Invalid operation",Toast.LENGTH_SHORT).show()
                    result.text = "Invalid operation"
                }
            }

            R.id.module -> {
                try {
                    if(input.text.isNotEmpty()) {
                        val str = StringBuilder()
                        val t =evt.findViewById<Button>(evt.id)
                        str.append(t.text.toString())


                        var s =  input.text.toString().plus(str.toString())

                        if (s.startsWith('%')){
                            input.text =  removeConsecutiveCharactersInString(s,0)
                        }else if(s.endsWith('%')) {
                            input.text = removeConsecutiveCharactersInString(s,0)
                        }else {
                            val m = 100
                            val ev = ExpressionBuilder(input.text.toString()).build()
                            val number = "^[1-9]".toRegex()
                            println(number)
                            val value = ev.evaluate()
                        }

                    }
                }catch(Error: Exception) {
                  result.text = "Cannot operation"
                } catch (Error: ArithmeticException) {
                    result.text = "Invalid operation"
                }
            }
        }
    }


    private fun hidden() {
      val view = this.currentFocus
       if(view != null) {
           val hidden = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
           hidden.hideSoftInputFromWindow(view.windowToken,0)
       }

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }





}

private fun removeConsecutiveCharactersInString(str: String, maxConsecutiveAllowedCount: Int): String {
    val builder = StringBuilder(str)
    var i = 0
    while (i < builder.length) {
        var foundCount = 0
        for (j in i - 1 downTo 0) {
            if (lowerCasedChar(builder[i]) == lowerCasedChar(builder[j])) {
                foundCount++
                if (foundCount >= maxConsecutiveAllowedCount) {
                    builder.deleteCharAt(i)
                    i--
                    break
                }
            } else {
                break
            }
        }
        i++
    }
    return builder.toString()
}



private fun lowerCasedChar(ch: Char): Char {
    val intChar = ch.toInt()
    return if (intChar in 65..90) {
        (intChar + 32).toChar()
    } else ch
}
