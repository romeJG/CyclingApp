package com.cometchat.pro.androiduikit

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cometchat.pro.androiduikit.Views.Login1
import com.cometchat.pro.androiduikit.Views.SessionManager
import com.cometchat.pro.androiduikit.constants.AppConfig
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.core.CometChat.CallbackListener
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.cometchat.pro.uikit.ui_components.cometchat_ui.CometChatUI
import com.cometchat.pro.uikit.ui_resources.utils.ErrorMessagesUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.cometchat.pro.uikit.ui_resources.utils.Utils
import kotlinx.android.synthetic.main.activity_create_user.*

class CreateUserActivity : AppCompatActivity() {
    private var inputUid: TextInputLayout? = null
    private var inputName: TextInputLayout? = null
    private var uid: TextInputEditText? = null
    private var name: TextInputEditText? = null
    private var createUserBtn: MaterialButton? = null
    private var go_to_login: MaterialButton? = null
    private var progressBar: ProgressBar? = null
    private var title: TextView? = null
    private var des1: TextView? = null
    private var des2: TextView? = null
    private var sessionManager: SessionManager? = null
    var username: String? = null
    var name1: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        sessionManager = SessionManager(this@CreateUserActivity) //TINITIGNAN KUNG MAY SESSION
        if (!sessionManager!!.isLoggedIn) {
            moveToLogin()
            // REKTA SA LOGIN XML KUNG WALANG SESSION
        }

        username = sessionManager!!.userDetail[SessionManager.UNIQUE_ID]
        name1 = sessionManager!!.userDetail[SessionManager.NAME]

        inputUid = findViewById(R.id.inputUID)
        inputName = findViewById(R.id.inputName)
        title = findViewById(R.id.tvTitle)
        des1 = findViewById(R.id.tvDes1)
        des2 = findViewById(R.id.tvDes2)
        progressBar = findViewById(R.id.createUser_pb)





        val uid = findViewById<EditText>(R.id.etUID) as EditText
        uid.setText(username, TextView.BufferType.EDITABLE);

        val name = findViewById<EditText>(R.id.etName) as EditText
        name.setText(name1, TextView.BufferType.EDITABLE);

        createUserBtn = findViewById(R.id.create_user_btn)
        go_to_login = findViewById(R.id.go_to_login)

        go_to_login!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
            }
            startActivity(intent)
        })
        createUserBtn!!.setTextColor(resources.getColor(R.color.textColorWhite))
        createUserBtn!!.setOnClickListener(View.OnClickListener {
            if (uid!!.text.toString().isEmpty()) uid!!.error = resources.getString(R.string.fill_this_field) else if (name!!.text.toString().isEmpty()) name!!.error = resources.getString(R.string.fill_this_field) else {
                progressBar!!.visibility = View.VISIBLE
                createUserBtn!!.isClickable = false
                val user = User()
                user.uid = uid!!.text.toString()
                user.name = name!!.text.toString()
                CometChat.createUser(user, AppConfig.AppDetails.AUTH_KEY, object : CallbackListener<User>() {
                    override fun onSuccess(user: User) {
                        login(user)
                    }

                    override fun onError(e: CometChatException) {
                        createUserBtn!!.isClickable = true
                        ErrorMessagesUtils.cometChatErrorMessage(this@CreateUserActivity, e.code)
                    }
                })
            }
        })

    }


    private fun login(user: User) {
        CometChat.login(user.uid, AppConfig.AppDetails.AUTH_KEY, object : CallbackListener<User?>() {
            override fun onSuccess(user: User?) {
                startActivity(Intent(this@CreateUserActivity, CometChatUI::class.java))
            }

            override fun onError(e: CometChatException) {
                if (uid != null) Snackbar.make(uid!!.rootView, "Unable to login", Snackbar.LENGTH_INDEFINITE).setAction("Try Again") { startActivity(Intent(this@CreateUserActivity, LoginActivity::class.java)) }.show()
                else ErrorMessagesUtils.cometChatErrorMessage(this@CreateUserActivity, e.code)
            }
        })
    }

    private fun moveToLogin() {
        sessionManager!!.logoutSession()
        val intent = Intent(this@CreateUserActivity, Login1::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
        finish()
    }
}