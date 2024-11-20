package com.example.feesmanagementsystem.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.feesmanagementsystem.Models.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "fees_management.db"
        private const val DATABASE_VERSION = 3 // Increment version if updating the schema

        // Table and column names
        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_REG = "reg"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_TOTAL_FEE = "totalFee"
        const val COLUMN_PENDING_FEE = "pendingFee"
        const val COLUMN_PASSWORD = "password" // Add the password column
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Create users table with password column
        val createUsersTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_REG TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL,
                $COLUMN_PHONE TEXT NOT NULL,
                $COLUMN_TOTAL_FEE TEXT NOT NULL,
                $COLUMN_PENDING_FEE TEXT NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL -- Password column added here
            )
        """
        db?.execSQL(createUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Drop the old table and create a new one if database version is updated
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    // Insert a user into the database, including the password
    fun insertUser(user: User): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, user.nameTxt)
            put(COLUMN_REG, user.regTxt)
            put(COLUMN_EMAIL, user.emailTxt)
            put(COLUMN_PHONE, user.phoneTxt)
            put(COLUMN_TOTAL_FEE, user.totalFee)
            put(COLUMN_PENDING_FEE, user.pendingFee)
            put(COLUMN_PASSWORD, user.password) // Store the password as well
        }
        return db.insert(TABLE_USERS, null, values)
    }

    // Get all users
    fun getAllUsers(): List<User> {
        val userList = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            null, // Select all columns
            null, null, null, null, null
        )

        if (cursor.moveToFirst()) {
            do {
                val user = User(
                    nameTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    regTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REG)),
                    emailTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    phoneTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                    totalFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_FEE)),
                    pendingFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PENDING_FEE)),
                    password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)) // Add the password column
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }


    // Fetch a single user by registration number
    fun getUserByReg(regNumber: String): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS, // Table name
            null, // Select all columns
            "$COLUMN_REG = ?", // WHERE clause
            arrayOf(regNumber), // WHERE arguments
            null, null, null
        )
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                nameTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                regTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REG)),
                emailTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                phoneTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                totalFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_FEE)),
                pendingFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PENDING_FEE)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)) // Add the password field here as well
            )
        }
        cursor.close()
        return user
    }

    // Get a user by email and password (for login authentication)
    fun getUserByEmailAndPassword(email: String, password: String): User? {
        val db = readableDatabase
        var user: User? = null
        var cursor: Cursor? = null
        try {
            cursor = db.query(
                TABLE_USERS, // Table name
                null, // Select all columns
                "$COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?", // WHERE clause
                arrayOf(email, password), // WHERE arguments
                null, null, null
            )

            if (cursor.moveToFirst()) {
                user = User(
                    nameTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    regTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REG)),
                    emailTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    phoneTxt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                    totalFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_FEE)),
                    pendingFee = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PENDING_FEE)),
                    password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return user
    }


//    @SuppressLint("Range")
    fun getUserByEmail(email: String): User? {
        val db = this.readableDatabase
        var cursor: Cursor? = null
        return try {
            // Query to get the user by email
            cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", arrayOf(email))

            // If a record is found, populate the User object
            if (cursor.moveToFirst()) {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val regNo = cursor.getString(cursor.getColumnIndexOrThrow("regNo"))
                val phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
                val totalFee = cursor.getString(cursor.getColumnIndexOrThrow("totalFee"))
                val pendingFee = cursor.getString(cursor.getColumnIndexOrThrow("pendingFee"))
                User(name, regNo, email, phone, "", totalFee, pendingFee)
            } else {
                null // No user found
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null // Return null if an error occurs
        } finally {
            cursor?.close() // Ensure the cursor is closed
        }
    }
}
