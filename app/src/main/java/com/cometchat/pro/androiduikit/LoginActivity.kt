package com.cometchat.pro.androiduikit

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cometchat.pro.androiduikit.Views.Login1
import com.cometchat.pro.androiduikit.constants.AppConfig
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.core.CometChat.CallbackListener
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants
import com.cometchat.pro.uikit.ui_resources.utils.ErrorMessagesUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.cometchat.pro.uikit.ui_resources.utils.Utils
import com.cometchat.pro.androiduikit.Views.SessionManager
import com.cometchat.pro.uikit.ui_components.cometchat_ui.CometChatUI

class LoginActivity : AppCompatActivity() {
    private var inputLayout: TextInputLayout? = null
    private var progressBar: ProgressBar? = null
    private var uid: TextInputEditText? = null
    private var title: TextView? = null
    private var des1: TextView? = null
    private var des2: TextView? = null
    private var sessionManager: SessionManager? = null
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sessionManager = SessionManager(this@LoginActivity) //TINITIGNAN KUNG MAY SESSION
        if (!sessionManager!!.isLoggedIn) {
            moveToLogin()
            // REKTA SA LOGIN XML KUNG WALANG SESSION
        }
        username = sessionManager!!.userDetail[SessionManager.UNIQUE_ID]
        val uid = findViewById<EditText>(R.id.etUID) as EditText
        uid.setText(username, TextView.BufferType.EDITABLE);

        des1 = findViewById(R.id.tvDes1)
        des2 = findViewById(R.id.tvDes2)
        progressBar = findViewById(R.id.loginProgress)
        inputLayout = findViewById(R.id.inputUID)
        uid?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                inputLayout?.endIconDrawable = getDrawable(R.drawable.ic_arrow_right_24dp)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        uid!!.setOnEditorActionListener(OnEditorActionListener { textView: TextView?, i: Int, keyEvent: KeyEvent? ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (uid!!.text.toString().isEmpty()) {
                    inputLayout?.endIconDrawable = null
                    uid?.error = resources.getString(R.string.fill_this_field)
                } else {
                    progressBar!!.visibility = View.VISIBLE
                    inputLayout!!.isEndIconVisible = false
                    login(uid!!.text.toString())
                }
            }
            true
        })
        inputLayout!!.setEndIconOnClickListener(View.OnClickListener { view: View? ->
            if (uid!!.text.toString().isEmpty()) {
                inputLayout?.endIconDrawable = null
                uid?.error = resources.getString(R.string.fill_this_field)
            } else {
                findViewById<View>(R.id.loginProgress).visibility = View.VISIBLE
                inputLayout!!.isEndIconVisible = false
                login(uid!!.text.toString())
            }
        })

    }



    private fun login(uid: String) {
        CometChat.login(uid, AppConfig.AppDetails.AUTH_KEY, object : CallbackListener<User?>() {
            override fun onSuccess(user: User?) {
                startActivity(Intent(this@LoginActivity, CometChatUI::class.java))
                finish()
            }

            override fun onError(e: CometChatException) {
                inputLayout!!.isEndIconVisible = true
                Log.e("login", "onError login: "+e.code)
                findViewById<View>(R.id.loginProgress).visibility = View.GONE
                ErrorMessagesUtils.cometChatErrorMessage(this@LoginActivity, e.code)
            }
        })
    }

    fun createUser(view: View?) {
        startActivity(Intent(this@LoginActivity, CreateUserActivity::class.java))
    }
    private fun moveToLogin() {
        sessionManager!!.logoutSession()
        val intent = Intent(this@LoginActivity, Login1::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
        finish()
    }
}