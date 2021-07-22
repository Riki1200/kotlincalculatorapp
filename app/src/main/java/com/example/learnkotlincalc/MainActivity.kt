package com.example.learnkotlincalc
import net.objecthunter.exp4j.ExpressionBuilder
import android.content.Context
import android.icu.text.SymbolTable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.*
import androidx.core.widget.doAfterTextChanged

import kotlinx.coroutines.*
import kotlin.js.*

import kotlin.math.sqrt
import kotlin.coroutines.*
import kotlin.js.ExperimentalJsExport

class MainActivity : AppCompatActivity() {
    private lateinit var content: ConstraintLayout

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
                if (text != null) {
                    if (text.length > 2){
                        val ev = ExpressionBuilder(text.toString()).build()

                        var value = ev.evaluate()
                        var longer = value.toLong()
                        if (value == longer.toDouble()){
                            result.text = longer.toString()
                        } else {
                            result.text =  value.toString()
                        }

                    }else {
                        result.text = ""
                    }
                }

            }catch(Error: Exception) {
                result.text = "";
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

    override fun onBackPressed() {
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item) as Boolean
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



            text.text =  s;
            VerifyString(text.text.toString())


        }else if(event.id == R.id.button_0) {
            val str = StringBuilder()

            val t =event.findViewById<Button>(event.id)
            str.append(t.text.toString())

            val s =  text.text.toString().plus(str.toString())
            notZeroFirst(s);


            text.text  = removeConsecutiveCharactersInString(s,1);
            VerifyString(text.text.toString())
        } else if(
           event.id == R.id.sum
            ||event.id == R.id.subtraction
            ||event.id == R.id.dot) {
            val str = StringBuilder()

            val t =event.findViewById<Button>(event.id)

            str.append(t.text.toString())
            var s =  text.text.toString().plus(str.toString())
            text.text = removeConsecutiveCharactersInString(s,1);
        }else if(event.id == R.id.division || event.id == R.id._multiply){
            val str = StringBuilder()
            val t =event.findViewById<Button>(event.id)
            str.append(t.tag.toString())
            var c = ""

            var s =  text.text.toString().plus(str.toString())
            removeConsecutiveCharactersInString(str.toString(),1);
            text.text = s;
        } else if (event.id == R.id.button_deleted) {
                if (text.text.isNotEmpty()){
                    text.setText(text.text.toString().substring(0,text.text.toString().length-1))
                }else {
                    text.text = ""
                }
                if(text.text.isEmpty()){
                    result.text = ""
                }

        }else if(event.id == R.id.button_equal) {
            if (text.text.isNotEmpty()){
                try {
                    val ev = ExpressionBuilder(text.text.toString()).build()
                    val value = ev.evaluate()
                    text.text = ""

                    var longer = value.toLong()
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

                        val ev = ExpressionBuilder(input.text.toString()).build()
                        val value = ev.evaluate()
                        val square = value * value
                        result.text = square.toString()
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
                        val op = input.text.toString();
                        val exp = ExpressionBuilder(op).build()
                        val r = exp.evaluate().toDouble()
                        val res = r / 100
                        result.text = r.toString()
                    }
                }catch(Error: Exception) {
                  result.text = "Cannot operation";
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




    private fun VerifyString(Str:String) {
        val exp = Regex(pattern = """(\+|\-|\/|\*)""")
        println(exp.find(Str)?.value)
    }
}



/**
 * Esta funcion remueve los caracteres repetidos en una cadena 
 *
 *
 *
 * */
fun removeConsecutiveCharactersInString(str: String, maxConsecutiveAllowedCount: Int): String {
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


private fun notZeroFirst(str: String): String {
    var builder = StringBuilder(str)
    var n = 0;

    while (n < builder.length) {

        if(builder.contains(str) ){

        }

        n++;
    }



    return str
}
private fun lowerCasedChar(ch: Char): Char {
    val intChar = ch.toInt()
    return if (intChar in 65..90) {
        (intChar + 32).toChar()
    } else ch
}
