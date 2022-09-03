package com.example.week1app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.week1app.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private var InputEmail = ""
    private var mEmail: EditText? = null
    private var InputPassword = ""
    private var mPass: EditText? = null
    private var reenterPass = ""
    private var mReenterPass: EditText? = null
    private var listOfEmails: MutableSet<String> = hashSetOf()
    private var state = false

    private fun saver(email : String, password : String) {

        val sharedPref: SharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val secondSharedPref: SharedPreferences = getSharedPreferences(
            "secondSharedPref",
            Context.MODE_PRIVATE
        )
        val secondEditor = secondSharedPref.edit()
        editor.apply {
            putString("email", email)
            putString("password", password)
            apply()
        }
        if (listOfEmails.contains(email)) {
            //do nothing
        } else {
            secondEditor.apply {
                listOfEmails.add(email)
                putStringSet("aaa", listOfEmails)
                apply()
            }
        }
    }
    private fun loader(){
        val sharedPref: SharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val email = sharedPref.getString("email", null)
        val password = sharedPref.getString("password", null)
        val secondSharedPref: SharedPreferences = getSharedPreferences("secondSharedPref",
        Context.MODE_PRIVATE)
        val listOfStuff = secondSharedPref.getStringSet("aaa",null)
        binding.InputEmailText.setText(email)
        binding.InputPasswordText.setText(password)

        if (listOfStuff != null) {
            listOfEmails = listOfStuff.toMutableSet()
        }
    }
    companion object {
        const val KEY_K = ""
        const val KEY_L = ""
        const val KEY_M = ""
    }

    private val binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val view = binding.root
        setContentView(view)
        supportActionBar?.title = "Create an account"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.passwordHint.isInvisible = true
        binding.nextButton.isClickable = false
        binding.nextButton.alpha = 0.5F
        binding.AccountHint.isInvisible = true
        mEmail = binding.InputEmailText
        mPass = binding.InputPasswordText
        mReenterPass = binding.InputPasswordAgainText

        loader()
        binding.nextButton.setOnClickListener {
            saver(binding.InputEmailText.text.toString(), binding.InputPasswordText.text.toString())
            //Log.d(TAG,"${listOfEmails.toString()}")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        InputEmail = binding.InputEmailText.text.toString()
        InputPassword = binding.InputPasswordText.text.toString()
        reenterPass = binding.InputPasswordAgainText.text.toString()
        outState.putString(KEY_K, InputEmail)
        outState.putString(KEY_L, InputPassword)
        outState.putString(KEY_M, reenterPass)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        InputEmail = savedInstanceState.getString(KEY_K).toString()
        InputPassword = savedInstanceState.getString(KEY_L).toString()
        reenterPass = savedInstanceState.getString(KEY_M).toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        if (binding.InputEmailText.text.toString().isNotEmpty() && binding.InputEmailText.text.toString()
                .contains("@") &&
            (binding.InputEmailText.text.toString().contains(".com") ||
            binding.InputEmailText.text.toString().contains(".edu") ||
            binding.InputEmailText.text.toString().contains(".net") ||
            binding.InputEmailText.text.toString().contains(".gov") ||
            binding.InputEmailText.text.toString().contains(".org"))
        ) {

            if (listOfEmails.contains(binding.InputEmailText.text.toString())) {
                binding.InputEmailText.setBackgroundResource(R.drawable.border)
                binding.InputEmailText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.cross,0)
                state = false
            }
            else {
                state = true
                binding.InputEmailText.setBackgroundResource(R.drawable.greenborder)
                binding.InputEmailText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.tick,0)
            }
        } else {
            state = false
            binding.InputEmailText.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.cross,
                0
            )
            binding.InputEmailText.setBackgroundResource(R.drawable.border)
            binding.nextButton.isClickable = false
            binding.nextButton.alpha = 0.5F
        }
        if (binding.InputPasswordText.text.isNotEmpty()&&binding.InputPasswordText.text.toString()
                .contains("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$".toRegex())){
            binding.InputPasswordText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.tick,0)
            binding.InputPasswordText.setBackgroundResource(R.drawable.greenborder)
        }
        else{
            binding.InputPasswordText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.cross,0)
            binding.InputPasswordText.setBackgroundResource(R.drawable.border)
        }
        if (binding.InputPasswordText.text.toString() == binding.InputPasswordAgainText.text.toString()) {
            if(binding.InputPasswordAgainText.text.toString().contains
                    ("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$".toRegex())){
                binding.passwordHint.isInvisible=true
                binding.InputPasswordAgainText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.tick,0)
                binding.InputPasswordAgainText.setBackgroundResource(R.drawable.greenborder)
                if(state) {
                    binding.nextButton.isClickable = true
                    binding.nextButton.alpha = 1F
                }
            }
            else{
                binding.passwordHint.isInvisible=false
                binding.InputPasswordAgainText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.cross,0)
                binding.InputPasswordAgainText.setBackgroundResource(R.drawable.border)
                binding.nextButton.isClickable = false
                binding.nextButton.alpha = 0.5F
            }
        }
        else{
            binding.passwordHint.isInvisible=false
            binding.InputPasswordAgainText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.cross,0)
            binding.InputPasswordAgainText.setBackgroundResource(R.drawable.border)
            binding.nextButton.isClickable = false
            binding.nextButton.alpha = 0.5F
        }
        binding.AccountHint.isVisible = listOfEmails.contains(binding.InputEmailText.text.toString())
//        if (listOfEmails.contains(binding.InputEmailText.text.toString())) {
//            binding.InputEmailText.setBackgroundResource(R.drawable.border)
//            binding.InputEmailText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.cross,0)
//            }
//        else {
//            binding.InputEmailText.setBackgroundResource(R.drawable.greenborder)
//            binding.InputEmailText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.tick,0)
//
//        }
    }
    
}