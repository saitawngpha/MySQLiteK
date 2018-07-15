package com.saitanwgpha.sqlitedatabase

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.saitanwgpha.mysqlite.R

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

     var lstUser: List<Account>? = ArrayList()
     lateinit var dbHelper: DBHelper
     var btn: Button? = null
     var container: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById<View>(R.id.buGetData) as Button
        container = findViewById<View>(R.id.container) as LinearLayout

        //listen to Database
        dbHelper = DBHelper(applicationContext)
        dbHelper.createDataBase()

        btn!!.setOnClickListener {
            lstUser = dbHelper.allUesers
            //List View
            for (account in lstUser!!) {
                val inflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val addView = inflater.inflate(R.layout.row, null)
                val txtUser = addView.findViewById<View>(R.id.txtUser) as TextView
                val txtEmail = addView.findViewById<View>(R.id.txtEmail) as TextView
                txtUser.text = account.userName
                txtEmail.text = account.email
                //add View
                container!!.addView(addView)
            }
        }
    }
}
