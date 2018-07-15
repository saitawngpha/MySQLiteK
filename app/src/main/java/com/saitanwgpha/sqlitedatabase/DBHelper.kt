package com.saitanwgpha.sqlitedatabase

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.widget.Toast
import java.io.FileOutputStream
import java.io.IOException
import java.util.ArrayList

import android.widget.Toast.LENGTH_SHORT

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 12) {
    private var mDatabase: SQLiteDatabase? = null
    private var mContext: Context? = null

    //Todo: Get All Data users
    val allUesers: List<Account>?
        get() {
            val temp = ArrayList<Account>()
            val db = this.writableDatabase

            val c: Cursor?
            try {
                c = db.rawQuery("SELECT * FROM Account", null)
                if (c == null) return null
                c.moveToFirst()

                do {
                    val account = Account(c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("email")))
                    temp.add(account)
                } while (c.moveToNext())
                c.close()
            } catch (e: Exception) {
            }

            db.close()
            return temp
        }


    init {

        if (Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.applicationInfo.dataDir + "/databases/"
        } else {
            DB_PATH = "/data/data/" + context.packageName + "/databases/"
        }

        mContext = mContext
    }

    override fun close() {
        if (mDatabase != null)
            mDatabase!!.close()
        super.close()
    }

    private fun checkDataBase(): Boolean {
        //Todo: Check Database
        var tempDB: SQLiteDatabase? = null
        try {
            val path = DB_PATH + DB_NAME
            tempDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE)
        } catch (ex: Exception) {
        }

        if (tempDB != null)
            tempDB.close()
        return if (tempDB != null) true else false
    }

    fun copyDataBase() {
        //Todo:Copy Database

        try {
            val myInput = mContext!!.assets.open(DB_NAME)
            val outputFileName = DB_PATH + DB_NAME
            val myOutput = FileOutputStream(outputFileName)

            val buffer = ByteArray(1024)
            var length: Int
            length= myInput.read(buffer)
            while (length > 0) {
                myOutput.write(buffer, 0, length)
            }
            myOutput.flush()
            myOutput.close()
            myInput.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun openDataBase() {
        //Todo: Open Database
        val path = DB_PATH + DB_NAME
        mDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    fun createDataBase() {
        //Todo: Create Database
        val isDBExist = checkDataBase()
        if (isDBExist) { } else {
            this.readableDatabase
            try {
                copyDataBase()
                Toast.makeText(this.mContext, "Copy has been finished.", LENGTH_SHORT).show()
            } catch (ex: Exception) {
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {

        private var DB_PATH = ""
        private val DB_NAME = "Mydb.db"
    }
}
