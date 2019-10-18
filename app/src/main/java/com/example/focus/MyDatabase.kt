package com.example.focus

import android.content.Context
import android.provider.Settings
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import java.util.ArrayList
import java.util.HashMap

class MyDatabase(context: Context, val databaseReference: DatabaseReference) {

    private val androidId: String = Settings.Secure.getString(context.contentResolver,
        Settings.Secure.ANDROID_ID)

    val HEADER = "$USERS/$androidId/"

    fun getKey(): String? {
        return databaseReference.child("users").push().key
    }

    fun write(location: MyLocation) {
        val childUpdates = HashMap<String, Any>()
        childUpdates[HEADER + getKey()] = location.toMap()
        databaseReference.updateChildren(childUpdates)
    }

    fun readSnapshot(snapshot: DataSnapshot) : List<MyLocation> {
        val dataMap : Map<String, Any> = (snapshot.value as Map<String, Any>)[androidId] as Map<String, Any>

        val newData: MutableList<MyLocation> = ArrayList()

        for (id in dataMap.keys) {
            newData.add(MyLocation(dataMap[id] as Map<String, Any>))
        }

        return newData
    }

    companion object {
        const val USERS = "users"
    }
}