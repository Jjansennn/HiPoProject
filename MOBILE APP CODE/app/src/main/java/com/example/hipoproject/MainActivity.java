package com.example.hipoproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // Top-level destinations
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.homeFragment,
                    R.id.plantFragment,
                    R.id.controlFragment,
                    R.id.profileFragment
            ).build();

            // 🟢 Manual handle BottomNavigationView supaya back stack di-reset tiap ganti tab
            bottomNavigationView.setOnItemSelectedListener(item -> {
                // Hapus semua fragment di back stack sampai ke startDestination
                navController.popBackStack(navController.getGraph().getStartDestinationId(), false);

                // Navigate ke tab yang dipilih
                navController.navigate(item.getItemId());
                return true;
            });

            // Klik ulang tab yg sama → scroll/reset state fragment itu
            bottomNavigationView.setOnItemReselectedListener(item ->
                    navController.popBackStack(item.getItemId(), false)
            );
        }
    }
}
