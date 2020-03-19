package com.example.chatapp.presenter

import com.example.chatapp.model.entity.Message
import com.example.chatapp.model.repository.FirebaseQuery
import com.google.firebase.database.DataSnapshot
import io.reactivex.Observable

class MessagePresenterImpl : PresenterInterface.MessageInterface {

    override fun getList(user1: String, user2: String): Observable<DataSnapshot?> {
        return FirebaseQuery().getMessage(user1, user2)
            .mergeWith(FirebaseQuery().getMessage(user2, user1))
    }

    override fun add(message: Message, user1: String, user2: String) {
        FirebaseQuery().addMessage(message, user1, user2)
    }
}