package com.example.chatapp.presenter

import com.example.chatapp.model.entity.Message
import com.google.firebase.database.DataSnapshot
import io.reactivex.Observable

class PresenterInterface {

    interface MessageInterface {

        fun getList(user1: String, user2: String): Observable<DataSnapshot?>

        fun add(message: Message, user1: String, user2: String)
    }

}