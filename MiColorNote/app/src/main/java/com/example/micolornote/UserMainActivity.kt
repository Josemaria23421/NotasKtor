package com.example.micolornote.Activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.micolornote.R
import com.example.micolornote.databinding.ActivityUserMainBinding

class UserMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarUser)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_user) as NavHostFragment
        navController = navHostFragment.navController

        binding.navigationViewUser.setupWithNavController(navController)

        // MENU HAMBURGUESA / LATERAL
        binding.navigationViewUser.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.action_logout) {
                mostrarDialogoLogOut()
            } else if (NavigationUI.onNavDestinationSelected(item, navController)) {
                binding.drawerLayoutUser.closeDrawers()
            }
            true
        }

        // BOTTOM NAV
        binding.bottonNavUser.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.action_logout) {
                mostrarDialogoLogOut()
                true
            } else {
                NavigationUI.onNavDestinationSelected(item, navController)
            }
        }    }

    // MENU ⋮ (TRES PUNTOS)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_del_usuario, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            mostrarDialogoLogOut()
            return true
        }
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }
    private fun mostrarDialogoLogOut() {
        val respuestaUsuario = { dialogo: android.content.DialogInterface, botonId: Int ->
            if (botonId == android.content.DialogInterface.BUTTON_POSITIVE) {
                // El usuario pulsa "Sí"
                irALogin()
            }
        }

        AlertDialog.Builder(this)
            .setTitle("Cerrar sesion")
            .setMessage("Seguro quiere cerrar sesion?")
            .setPositiveButton("SI", respuestaUsuario)
            .setNegativeButton("CANCELAR", respuestaUsuario)
            .show()
    }
    private fun irALogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}