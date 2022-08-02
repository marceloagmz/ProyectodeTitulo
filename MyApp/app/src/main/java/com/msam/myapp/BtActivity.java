package com.msam.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ingenieria.jhr.bluetoothjhr.BluetoothJhr;

public class BtActivity extends AppCompatActivity {

    ListView dispositivos;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt);

        dispositivos = findViewById(R.id.dispositivos);

        final BluetoothJhr blue = new BluetoothJhr(this ,dispositivos, BtActivity2.class);
        blue.onBluetooth();

        dispositivos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                blue.bluetoothSeleccion(i);
            }
        });
    }

}
