package com.example.week1app

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.week1app.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private var input_email = ""
    private var m_email: EditText? = null
    private var input_password = ""
    private var m_pass: EditText? = null
    private var re_enterpass = ""
    private var m_reenterpass: EditText? = null
    private var listOfEmails: MutableSet<String> = hashSetOf()

    private fun saver(email : String, password : String){
        val sharedPref: SharedPreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val secondSharedPref: SharedPreferences = getSharedPreferences("secondSharedPref",
            Context.MODE_PRIVATE)
        val secondEditor = secondSharedPref.edit()
        editor.apply{
            putString("email", email)
            putString("password", password)
            apply()
        }
        if (listOfEmails.contains(email)){
            //do nothing
            }
        else{
            secondEditor.apply {
                listOfEmails.add(email)
                putStringSet("aaa", listOfEmails)
                //Log.d(TAG, listOfEmails.toString())
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
        val listofstuff = secondSharedPref.getStringSet("aaa",null)
        binding.InputEmailText.setText(email)
        binding.InputPasswordText.setText(password)
        if (listofstuff != null) {
            listOfEmails = listofstuff
            Log.d(TAG, listofstuff.toString())
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
        var actionBar = getSupportActionBar()

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        binding.passwordHint.isInvisible = true
        binding.nextButton.isClickable = false
        binding.nextButton.alpha = 0.5F
        binding.AccountHint.isInvisible=true
        m_email = binding.InputEmailText
        m_pass = binding.InputPasswordText
        m_reenterpass = binding.InputPasswordAgainText

        loader()
        binding.nextButton.setOnClickListener{
            saver(binding.InputEmailText.text.toString(), binding.InputPasswordText.text.toString())
            Log.d(TAG,"${listOfEmails.toString()}")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        input_email = binding.InputEmailText.text.toString()
        input_password = binding.InputPasswordText.text.toString()
        re_enterpass = binding.InputPasswordAgainText.text.toString()
        outState.putString(KEY_K, input_email)
        outState.putString(KEY_L, input_password)
        outState.putString(KEY_M, re_enterpass)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        input_email = savedInstanceState.getString(KEY_K).toString()
        input_password = savedInstanceState.getString(KEY_L).toString()
        re_enterpass = savedInstanceState.getString(KEY_M).toString()
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
        if (binding.InputEmailText.text.isNotEmpty() && binding.InputEmailText.text.toString()
                .contains("@") && binding.InputEmailText.text.toString().contains(".com")
        ) {
            binding.InputEmailText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.tick, 0)
            binding.InputEmailText.setBackgroundResource(R.drawable.greenborder)
        } else {
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
        //put another check here later to validate against a list of emails already inputted
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
                binding.nextButton.isClickable = true
                binding.nextButton.alpha = 1F

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
        if (listOfEmails.contains(binding.InputEmailText.text.toString())) {
            binding.InputEmailText.setBackgroundResource(R.drawable.border)
            binding.InputEmailText.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.cross,0)
            }
    }


    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
    }
}