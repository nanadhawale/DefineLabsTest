package com.definelabs.definelabstest.database

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.util.LocaleData
import android.widget.Toast
import com.definelabs.definelabstest.database.data.LocalMatchData

val DATABASE_NAME = "Matches.db"
val FAVORITES_TABLE_NAME = "Favorites"
val FAVORITES_MATCH_NAME = "Match_Name"
val FAVORITES_CITY = "City"
val FAVORITES_COUNTRY = "Country"
val FAVORITES_ID = "ID"
val FAVORITES_PHONE = "Phone"
val FAVORITES_USERS = "Users_Count"
val FAVORITES_ADDRESS = "Address"
val FAVORITES_ISVERIFIED = "isVerified"

class SqliteManager(var context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $FAVORITES_TABLE_NAME (" +
                    "$FAVORITES_ID TEXT PRIMARY KEY," +
                    "$FAVORITES_MATCH_NAME VARCHAR(256)," +
                    "$FAVORITES_CITY VARCHAR(256)," +
                    "$FAVORITES_COUNTRY VARCHAR(256)," +
                    "$FAVORITES_PHONE VARCHAR(256)," +
                    "$FAVORITES_USERS VARCHAR(256)," +
                    "$FAVORITES_ADDRESS VARCHAR(256)," +
                    "$FAVORITES_ISVERIFIED BOOLEAN" +
                    ")"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //do nothing
    }

    fun insertData(data: LocalMatchData) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(FAVORITES_ID, data.id)
        contentValues.put(FAVORITES_MATCH_NAME, data.matchName)
        contentValues.put(FAVORITES_CITY, data.city)
        contentValues.put(FAVORITES_COUNTRY, data.country)
        contentValues.put(FAVORITES_PHONE, data.phone)

        contentValues.put(FAVORITES_USERS, data.usersCount)
        contentValues.put(FAVORITES_ADDRESS, data.country)
        contentValues.put(FAVORITES_ISVERIFIED, data.isVerified)


        val result = database.insert(FAVORITES_TABLE_NAME, null, contentValues)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(): MutableList<LocalMatchData> {
        val list: MutableList<LocalMatchData> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $FAVORITES_TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val matchName: String =
                    result.getString(result.getColumnIndex(FAVORITES_MATCH_NAME))
                val city: String = result.getString(result.getColumnIndex(FAVORITES_CITY))
                val country: String = result.getString(result.getColumnIndex(FAVORITES_COUNTRY))
                val id: String = result.getString(result.getColumnIndex(FAVORITES_ID))
                val phone: String = result.getString(result.getColumnIndex(FAVORITES_PHONE))
                val usersCount: String = result.getString(result.getColumnIndex(FAVORITES_USERS))
                val address: String = result.getString(result.getColumnIndex(FAVORITES_ADDRESS))
                val isVerified: Boolean =
                    result.getString(result.getColumnIndex(FAVORITES_ISVERIFIED)).toBoolean()

                list.add(
                    LocalMatchData(
                        matchName,
                        city,
                        country, id, phone, usersCount, address, isVerified
                    )
                )
            } while (result.moveToNext())
        }
        return list
    }

    fun checkExistance(idtmp: String): Int {
        val id = idtmp.removePrefix("id: ")
        val db = this.readableDatabase
        val query = "Select count(*) from $FAVORITES_TABLE_NAME where $FAVORITES_ID = '$id'"
        val result = db.rawQuery(query, null)
        result.moveToFirst()
        var cnt = result.getInt(0)
        return cnt
    }

    fun getCount(): Int {
        val db = this.readableDatabase
        val query = "Select * from $FAVORITES_TABLE_NAME"
        val result = db.rawQuery(query, null)
        return result.count
    }

    fun removeData(id: String) {

        Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_SHORT).show()
        this.readableDatabase.delete(FAVORITES_TABLE_NAME, "$FAVORITES_ID= '$id'", null)

    }

}