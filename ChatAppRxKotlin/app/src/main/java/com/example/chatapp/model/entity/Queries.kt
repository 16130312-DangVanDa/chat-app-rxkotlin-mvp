package com.example.chatapp.model.entity

import android.util.Log
import com.google.firebase.database.*
import io.reactivex.Observable

class Queries {

    lateinit var path: String

    private fun build(): Query {
        return FirebaseDatabase.getInstance().reference.child(path)
    }

    fun observe(): Observable<DataSnapshot?> {
        return Observable.create<DataSnapshot> {
            val query = this.build()
            query.addChildEventListener(object : ChildEventListener {
                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    it.onNext(p0)
                    Log.d("tester", "get data")
                }

                override fun onChildRemoved(p0: DataSnapshot) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }

    fun add(message: Message, currentUser: String, otherUser: String) {
        val query = this.build()
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                val path = if (p0.hasChild("$otherUser-$currentUser")) {
                    "$otherUser-$currentUser"
                } else {
                    "$currentUser-$otherUser"
                }
                FirebaseDatabase.getInstance().reference.child(this@Queries.path + "/" + path)
                    .push()
                    .setValue(message)
            }
        })
    }
}


