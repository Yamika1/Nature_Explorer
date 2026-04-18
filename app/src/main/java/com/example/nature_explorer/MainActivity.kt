package com.example.nature_explorer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    var nature = Nature()
    private lateinit var binding: ActivityMainWithNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainWithNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nature.setOnClickListener(this)

        setSupportActionBar(binding.navToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val toggleOnOff = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.navToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggleOnOff)
        toggleOnOff.syncState()

        binding.navView.bringToFront()
        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.nature -> {
                nature.reserveName =
                    "The name of the nature reserve is Cumberland Nature Reserve"

                openIntent(
                    applicationContext,
                    nature.reserveName,
                    NatureDetailsActivity::class.java
                )
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_main -> {
                Toast.makeText(this, "Menu is clicked", Toast.LENGTH_SHORT).show()
            }

            R.id.nature -> {
                openIntent(applicationContext, "", NatureDetailsActivity::class.java)
                Toast.makeText(this, "This is a Cherry Blossom", Toast.LENGTH_SHORT).show()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /*@SuppressLint("GestureBackNavigation")
    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }*/
}
class Nature() {
    lateinit var reserveName: String
    lateinit var reservePlace: String


    constructor(name: String, place: String) : this() {
        reserveName = name
        reservePlace = place
    }
}


fun openIntent(context: Context, nature: String, activityToOpen: Class<*>){
    val intent = Intent(context, activityToOpen)

    intent.putExtra("nature", nature)

    if(context !is android.app.Activity){
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    context.startActivity(intent)
}

fun shareIntent(context: Context, order: String){
    val sendIntent = Intent()

    sendIntent.action = Intent.ACTION_SEND

    sendIntent.putExtra(Intent.EXTRA_TEXT, order)

    sendIntent.type = "text/plain"

    val shareIntent = Intent.createChooser(sendIntent, null)

    if(context !is android.app.Activity){
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(shareIntent)
}


fun shareIntent(context: Context, nature: Nature){
    val sendIntent = Intent()

    val shareDetails = Bundle()
    shareDetails.putString("reserveName", nature.reserveName)
    shareDetails.putString("reservePlace", nature.reservePlace)


    sendIntent.putExtra(Intent.EXTRA_TEXT, shareDetails)
    sendIntent.type = "text/plain"

    val shareIntent = Intent.createChooser(sendIntent, null)

    if(context !is android.app.Activity){
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(shareIntent)
}
