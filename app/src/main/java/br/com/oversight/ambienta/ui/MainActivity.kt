package br.com.oversight.ambienta.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
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

        val navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            run {
                if (destination.id == R.id.nav_home) {
                    toolbar.setPadding(180,0,0,0)
                    findViewById<AppCompatImageView>(R.id.image)?.visibility = View.VISIBLE
                }else {
                    toolbar.setPadding(0,0,0,0)
                    findViewById<AppCompatImageView>(R.id.image)?.visibility = View.GONE
                }
            }
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

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
