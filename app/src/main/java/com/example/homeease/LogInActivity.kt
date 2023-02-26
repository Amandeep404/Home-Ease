package com.example.homeease

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LogInActivity : AppCompatActivity() {

    private var auth = FirebaseAuth.getInstance()
    lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var mProgressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        setContentView(R.layout.activity_log_in)

        createFirebaseRequest()

        findViewById<LinearLayout>(R.id.llGoogleSignIn).setOnClickListener{
            showProgressBar()
            signInWithGoogle()
        }
        findViewById<Button>(R.id.btnLogIn).setOnClickListener{
            registerUser()
        }

    }

    private fun registerUser(){
        val name = findViewById<EditText>(R.id.etSignUpName).text.toString().trim(){it <= ' '}
        val email = findViewById<EditText>(R.id.etSignUpEmail).text.toString().trim(){it <= ' '}
        val password = findViewById<EditText>(R.id.etSignUpPassword).text.toString().trim(){it <= ' '}

        if (!isEmailValid(email)){
            findViewById<EditText>(R.id.etSignUpEmail).setError("Invalid Email Id")
        }

        if( validateForm(name, email, password)){
            showProgressBar()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val fireBaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = fireBaseUser.email!!
                    updateUi(fireBaseUser)
                } else {
                    Toast.makeText(
                        this,
                        task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    hideProgressBar()
                }
            }
        }
    }

    private fun validateForm(name:String, email: String, password : String): Boolean{
        return when{
            TextUtils.isEmpty(name) ->{
                showErrorSnackBar("Please Enter Name")
                false
            }
            TextUtils.isEmpty(email) ->{
                showErrorSnackBar("Please Enter E-mail")
                false
            }
            TextUtils.isEmpty(password) ->{
                showErrorSnackBar("Please Enter Password")
                false
            }
            else -> true
        }
    }

    fun isEmailValid(email : CharSequence):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val data =  it.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithAccount(account)
            }catch (e : ApiException){
                hideProgressBar()
                e.printStackTrace()
            }
        }else{
            hideProgressBar()
            showErrorSnackBar("Error occurred")
            return@registerForActivityResult
        }
    }

    private fun firebaseAuthWithAccount(account: GoogleSignInAccount?) {

        val credential =  GoogleAuthProvider.getCredential(account!!.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    updateUi(auth.currentUser)
                }else{
                    showErrorSnackBar("Error occurred while signing")
                    hideProgressBar()
                }
            }
    }

    private fun updateUi(currentUser: FirebaseUser?) {

        if (currentUser == null){
            hideProgressBar()
            return
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()

    }

    private fun createFirebaseRequest() {

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, options)
    }

    private fun showProgressBar(){
        mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.progress_dialog)

        /// To make bg transparent
        mProgressDialog.window!!.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                this,
                android.R.color.transparent
            )
        )

        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
    }

    private fun hideProgressBar(){
        mProgressDialog.dismiss()
    }

    private fun showErrorSnackBar(message:String){
        val snackBar = Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.reddish_brown))
        snackBar.show()
    }


    override fun onStart() {
        super.onStart()

        if (auth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }



}