package com.dudnyk.projectwithmaterialdesign.SQL

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.dudnyk.projectwithmaterialdesign.data.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "MaD.UserManager.db"
        val TABLE_USERS = "users"

        val COLUMN_USER_ID = "user_id"
        val COLUMN_USER_NAME = "user_name"
        val COLUMN_USER_EMAIL = "user_email"
        val COLUMN_USER_PASSWORD = "user_password"
    }

    private val CREATE_USER_TABLE = (
            "CREATE TABLE " + TABLE_USERS + "(" +
            COLUMN_USER_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USER_NAME        + " TEXT," +
            COLUMN_USER_EMAIL       + " TEXT," +
            COLUMN_USER_PASSWORD    + " TEXT" + ")"
        )
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USERS"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_USER_TABLE)
        onCreate(db)
    }

    fun getAllUser(): List<User> {
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)

        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList = mutableListOf<User>()

        val db = this.readableDatabase
        val cursor = db.query(TABLE_USERS, columns, null, null, null, null, sortOrder)

        if (cursor.moveToFirst()) {
            do {
                val user = User(
                    id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                    email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                    password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    fun addUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)

        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    fun updateUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)

        db.update(TABLE_USERS, values, "$COLUMN_USER_ID = ?", arrayOf(user.id.toString()))
        db.close()
    }

    fun deleteUser(user: User) {
        val db = this.writableDatabase
        db.delete(TABLE_USERS, "$COLUMN_USER_ID = ?", arrayOf(user.id.toString()))
        db.close()
    }

    fun checkUser(email: String): Boolean {
        val columns = arrayOf(COLUMN_USER_ID)

        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ?"
        val selectionArgs = arrayOf(email)

        val cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null,null,null)
        val count = cursor.count

        cursor.close()
        db.close()

        return count > 0
    }

    fun checkUser(email: String, password: String): Boolean {
        val columns = arrayOf(COLUMN_USER_ID)

        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null,  null, null)
        val count = cursor.count
        cursor.close()
        db.close()

        return count > 0
    }

    fun getUser(email: String, password: String): User? {
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)
        var user: User? = null

        val db = this.readableDatabase
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(email, password)

        val cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
             user = User(
                 id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
            )
        }
        cursor.close()
        db.close()
        return user
    }

    fun getUser(id: Int): User? {
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)
        var user: User? = null

        val db = this.readableDatabase
        val selection = "$COLUMN_USER_ID = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null)

        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD))
            )
        }
        cursor.close()
        db.close()
        return user
    }
}