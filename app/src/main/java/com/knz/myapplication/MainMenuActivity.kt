    package com.knz.myapplication

    import android.content.Intent
    import android.os.Bundle
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import com.google.android.material.bottomnavigation.BottomNavigationView

    class MainMenuActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_main_menu)
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment, HomeFragment())
                    .commit()
            }

            val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, HomeFragment())
                            .commit()
                        true
                    }
                    R.id.weather -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, WeatherFragment())
                            .commit()
                        true
                    }
                    R.id.login -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.nav_host_fragment, LoginFragment())
                            .commit()
                        true
                    }
                    else -> false
                }
            }
        }
    }