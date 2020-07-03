package com.example.kotlinapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.adapter_items.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var nickname: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        loadData();
        if (nickname.isEmpty()) {
            nickname = "not set yet"
            val intent = Intent(this@MainActivity, GalleryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_gallery -> {
                val intent = Intent(this@MainActivity, GalleryActivity::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadData() {
        val sharedPreferences =
            getSharedPreferences("KEY", Context.MODE_PRIVATE)
        nickname = sharedPreferences.getString("KEY_NICKNAME", "")!!
    }

    fun sendMessageOnClick(view: View) {

        if (messageEditText.text.isNotEmpty()) {
            val json = JSONObject()
            json.put("content", messageEditText.text)
            json.put("login", nickname.toString())


            HttpTask {
                if (it == null) {
                    println("connection error")
                    val intent = Intent(this@MainActivity, NoConnectionActivity::class.java)
                    startActivity(intent)
                    return@HttpTask
                }
                println(it)
            }.execute("POST", "http://tgryl.pl/shoutbox/message", json.toString())
            messageEditText.setText("")

        }

    }




    fun editButtonOnClick(view: View) {
        //val json = JSONObject()
      //  val msg = json.get("id")
      //  println("LOOOOOOOL"+msg.toString())
        val intent = Intent(this@MainActivity, EditActivity::class.java)
        startActivity(intent)
    }


}