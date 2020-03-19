package com.example.chatapp.model.repository

import com.example.chatapp.model.entity.Message
import com.google.firebase.database.DataSnapshot
import io.reactivex.Observable

class RepositoryInterface {

    interface FirebaseQueryInterface {

        fun getMessage(user1: String, user2: String): Observable<DataSnapshot?>

        fun addMessage(message: Message, user1: String, user2: String)

    }
}