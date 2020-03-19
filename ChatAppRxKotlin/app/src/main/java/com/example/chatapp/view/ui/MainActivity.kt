package com.example.chatapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import com.example.chatapp.R
import com.example.chatapp.model.entity.Message
import com.example.chatapp.presenter.MessagePresenterImpl

import com.example.chatapp.view.adapter.MessageAdapter
import com.google.firebase.database.DataSnapshot
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    //view
    private lateinit var listView: ListView
    private lateinit var editTextTypeMessage: EditText
    private lateinit var btnSend: ImageView

    //properties
    private lateinit var adapterMessage: MessageAdapter
    private var list = ArrayList<Message>()
    private var currentUser: String = ""
    private var otherUser: String = ""
    private lateinit var presenter: MessagePresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.elevation = 0f
        rec()
        mapping()
        action()
    }

    // mapping to GUI
    private fun mapping() {
        listView = findViewById(R.id.chatListPassMessage)
        editTextTypeMessage = findViewById(R.id.chatTypeMessage)
        btnSend = findViewById(R.id.chatBtnSend)

        presenter = MessagePresenterImpl()
        adapterMessage = MessageAdapter(list, this, currentUser)
        listView.adapter = adapterMessage
        adapterMessage.notifyDataSetChanged()
        listView.setSelection(listView.lastVisiblePosition)
    }

    // main action
    private fun action() {
        handleBtnSend()
        streamData()
    }

    private fun streamData() {
        //create observable
        val observable: Observable<DataSnapshot?> =
            presenter.getList(currentUser, otherUser)
        //Create an Observer
        val myObserver: Observer<DataSnapshot?> = getObserver()
        //Subscribe myObserver to myObservable//
        observable.subscribe(myObserver)
    }

    private fun getObserver(): Observer<DataSnapshot?> {
        return object : Observer<DataSnapshot?> {
            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(s: DataSnapshot) {
                val message = s.getValue(Message::class.java)!!
                message.codeFB = s.key!!
                list.add(message)
                adapterMessage.notifyDataSetChanged()
                listView.setSelection(listView.lastVisiblePosition)
                Log.d("tester", "test: $message")
            }

            //Called if an exception is thrown//
            override fun onError(e: Throwable) {
                Log.e("TAG", "onError: " + e.message)
            }

            //When onComplete is called, print the following to Logcat//
            override fun onComplete() {
                Log.d("TAG", "onComplete")
            }
        }
    }

    private fun rec() {
        val intent = intent
        if (intent != null) {
            currentUser = intent.getStringExtra("user")!!
            otherUser = if (currentUser == "user1") {
                "user2"
            } else {
                "user1"
            }
            this.title = otherUser
        }
    }

    //Press button send message after enter in InputText
    private fun handleBtnSend() {
        btnSend.setOnClickListener {
            sendMessage(editTextTypeMessage)
        }
    }

    private fun sendMessage(editText: EditText) {
        if (editText.text != null || editText.text.toString().trim() != "") {
            //dang ki Observer nhan su thay doi tu Observable
            presenter.add(
                Message(
                    currentUser,
                    editText.text.toString().trim(),
                    Calendar.getInstance().timeInMillis,
                    false
                ), currentUser, otherUser
            )
            editText.setText("")
            editText.onEditorAction(EditorInfo.IME_ACTION_DONE)
        } else {
            Toast.makeText(this, "không được phép", Toast.LENGTH_SHORT).show()
        }
    }
}
