package com.example.chatapp.model.repository

import com.example.chatapp.model.entity.Message
import com.example.chatapp.model.entity.Queries
import com.google.firebase.database.DataSnapshot
import io.reactivex.Observable

class FirebaseQuery : RepositoryInterface.FirebaseQueryInterface {

    override fun getMessage(user1: String, user2: String): Observable<DataSnapshot?> {
        return Queries().apply { path = "message/$user1-$user2" }
            .observe()
    }

    override fun addMessage(message: Message, user1: String, user2: String) {
        Queries().apply { path = "message" }.add(message, user1, user2)
    }
}