package com.msam.myapp.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.msam.myapp.MainActivity;
import com.msam.myapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor=preferences.edit();

                int bandera=Integer.parseInt(preferences.getString("bandera","0"));

                if (bandera==1){
                    Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    editor.putString("bandera","1");
                    editor.commit();

                    Intent intent=new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        },1500);

    }
}
