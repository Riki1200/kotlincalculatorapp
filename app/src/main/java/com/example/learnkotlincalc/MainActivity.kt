package com.example.learnkotlincalc
import net.objecthunter.exp4j.ExpressionBuilder
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import java.util.zip.Inflater
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {
    lateinit var content: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val gridContent = findViewById<GridLayout>(R.id.gridLayout)
        val inputView = findViewById<TextView>(R.id.sub_value)
        content = findViewById<ConstraintLayout>(R.id.content) as ConstraintLayout

        val result = findViewById<TextView>(R.id.result)

           val x = Admin("Romeo")


        inputView.setOnClickListener {
            hidden()

        }


        for (ids in gridContent.children.iterator()){
            findViewById<Button?>(ids.id)?.setOnClickListener {
               clickEvent(it,inputView,result)
                reset(inputView,result,it)
                operationSpecial(it,result,inputView)
            }
        }




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId) {
            R.id.Home -> {
                val intent = Intent(this@MainActivity, Main3Activity::class.java)
                startActivity(intent)
                println("Si")
                return true
            }
        }
        
        
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
                || event.id == R.id.button_0
                || event.id == R.id.division
                || event.id == R.id.sum
                || event.id == R.id._multiply
                || event.id == R.id.subtraction
                || event.id == R.id.dot) {
                    val str = StringBuilder()

            val t =event.findViewById<Button>(event.id)
            str.append(t.text.toString())

            val s =  text.text.toString().plus(str.toString())


            text.text = s
            VerifyString(text.text.toString())


        }else if (event.id == R.id.button_deleted) {
                if (text.text.isNotEmpty()){
                    text.setText(text.text.toString().substring(0,text.text.toString().length-1))
                }else {
                    text.text = ""
                }
        }else if(event.id == R.id.button_equal) {
            if (text.text.isNotEmpty()){
                try {
                    val ev = ExpressionBuilder(text.text.toString()).build()
                    val value = ev.evaluate()
                    result.text = value.toString()
                }catch (Error:ArithmeticException) {
                    Toast.makeText(this@MainActivity, Error.toString(),Toast.LENGTH_SHORT).show()
                    result.text = Error.toString().replace("java.lang.ArithmeticException: ", "")
                }catch (Error: Exception) {
                    Toast.makeText(this@MainActivity, Error.toString(),Toast.LENGTH_SHORT).show()
                    result.text = "Invalid operation"
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
                        val op = input.text.toString();
                        val exp = ExpressionBuilder(op).build()
                        val r = exp.evaluate().toDouble()
                        val res = r / 100
                        result.text = res.toString()
                    }
                }catch (Error: ArithmeticException) {
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



