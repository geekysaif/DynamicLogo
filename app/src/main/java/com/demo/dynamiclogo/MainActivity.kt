package com.demo.dynamiclogo

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var clickeddd: TextView? = null
    var newicon:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clickeddd = findViewById(R.id.oldicon);
        newicon = findViewById(R.id.newicon);


        clickeddd!!.setOnClickListener {
            changeicon()
        }

        newicon!!.setOnClickListener {
            newicon()
        }

    }


    private fun changeicon() {

        // enable old icon
        val manager = packageManager
        manager.setComponentEnabledSetting(
            ComponentName(
                this@MainActivity,
                "com.demo.dynamiclogo.MainActivity"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP)

        // disable new icon
        manager.setComponentEnabledSetting(
            ComponentName(
                this@MainActivity,
                "com.demo.dynamiclogo.MainActivityAlias"),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP)
        Toast
            .makeText(this@MainActivity, "Enable Old Icon",
                Toast.LENGTH_LONG)
            .show()
    }


    private fun newicon() {

        // disable old icon
        val manager = packageManager
        manager.setComponentEnabledSetting(
            ComponentName(
                this@MainActivity,
                "com.demo.dynamiclogo.MainActivity"),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP)

        // enable new icon
        manager.setComponentEnabledSetting(
            ComponentName(
                this@MainActivity,
                "com.demo.dynamiclogo.MainActivityAlias"),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP)
        Toast
            .makeText(this@MainActivity, "Enable New Icon",
                Toast.LENGTH_LONG)
            .show()
    }


}