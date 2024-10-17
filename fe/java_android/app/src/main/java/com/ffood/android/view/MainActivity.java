package com.ffood.android.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ffood.android.R;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        boolean isLoggedIn = checkIfLoggedIn();
        Intent intent = new Intent();
        if (isLoggedIn) {
            intent.setClass(this, HomeActivity.class);
        } else {
            intent.setClass(this, WelcomeActivity.class);
        }
        startActivity(intent);

    }

    private boolean checkIfLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        Logger.getLogger("UserPreferences").info("isLoggedIn: " + sharedPreferences.getBoolean("isLoggedIn", false));
        return sharedPreferences.getBoolean("isLoggedIn", false); // Default is 'false'
    }
}