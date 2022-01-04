package com.example.messageapp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



public class AuthenticationActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.appbar_main1);
////        Toolbar toolbar = findViewById(R.id.toolbar);
////
////        setSupportActionBar(toolbar);
//        BottomNavigationView navView = findViewById(R.id.nav_view1);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.searchFragment)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_authentication);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController( navView,navController);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_authentication);
        appBarConfiguration1 = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration1);

    }
}