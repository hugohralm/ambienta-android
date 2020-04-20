package br.com.oversight.ambienta.ui

import android.os.Bundle
import android.os.Handler
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.di.BaseActivity
import br.com.oversight.ambienta.di.RequiresViewModel
import br.com.oversight.ambienta.service.ApiResult
import br.com.oversight.ambienta.service.room.AppDatabase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@RequiresViewModel(MainViewModel::class)
class MainActivity : BaseActivity<MainViewModel>() {

    @Inject
    lateinit var database: AppDatabase

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        this.viewModel?.tipoCategoria?.observe(this, Observer {
            if (it.status == ApiResult.Status.STATUS_SUCCESS) {
                lifecycleScope.launch {
                    it.data?.let { it1 ->
                        database.tipoCategoriaDao().insertTipoCategoria(it1)
                    }
                }
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
