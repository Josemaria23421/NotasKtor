package com.example.micolornote.Activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        // MENU HAMBURGUESA / LATERAL
        binding.toolbarUser.setupWithNavController(
            navController,
            AppBarConfiguration(
                setOf(R.id.user_notas, R.id.user_perfil, R.id.fragment_crear_nota),
                binding.drawerLayoutUser
            )
        )
        binding.navigationViewUser.setupWithNavController(navController)

        // BOTTOM NAV
        binding.bottonNavUser.setupWithNavController(navController)
    }

    // MENU ⋮ (TRES PUNTOS)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_del_usuario, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }
}