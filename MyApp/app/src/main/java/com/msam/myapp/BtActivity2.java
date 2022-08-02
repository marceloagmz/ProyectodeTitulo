package com.msam.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import ingenieria.jhr.bluetoothjhr.BluetoothJhr;

public class BtActivity2 extends AppCompatActivity {

    Boolean initHilo = false;
    Boolean hilo = true;
    BluetoothJhr blue;
    String mensaje = "";
    TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        blue = new BluetoothJhr(this, BtActivity.class);
        blue.exitErrorOk(true);
        blue.mensajeConexion("Conectado");
        blue.mensajeErrorTx("algo mal");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!initHilo){
                    delay(500L);
                }
                while (hilo){
                    blue.mTx("t");
                    delay(1000L);
                    mensaje = blue.mRx();
                    if( mensaje != ""){
                        if(hilo){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    display.setText(mensaje);
                                }
                            });
                        }else{
                            break;
                        }
                        blue.mensajeReset();
                    }
                    delay(1000L);
                }
            }
        }).start();
    }

    public void delay(Long time){
        try{
            Thread.sleep(time);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        initHilo = blue.conectaBluetooth();
    }

    @Override
    protected void onPause(){
        super.onPause();
        hilo = true;
        initHilo = true;
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        initHilo = true;
        hilo = false;
    }
}
