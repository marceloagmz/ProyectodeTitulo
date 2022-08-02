package com.msam.myapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.juang.jplot.PlotPlanitoXY;
import com.msam.myapp.actividades.AjustesActivity;
import com.msam.myapp.actividades.ContenedorInstruccionesActivity;
import com.msam.myapp.clases.ConexionSQLiteHelper;
import com.msam.myapp.clases.PreferenciasJuego;
import com.msam.myapp.clases.Utilidades;
import com.msam.myapp.dialogos.DialogoTipoJuegoFragment;
import com.msam.myapp.fragments.GestionJugadorFragment;
import com.msam.myapp.fragments.InicioFragment;
import com.msam.myapp.fragments.RankingFragment;
import com.msam.myapp.fragments.RegistroJugadorFragment;
import com.msam.myapp.interfaces.IComunicaFragments;


public class MainActivity extends AppCompatActivity implements IComunicaFragments, InicioFragment.OnFragmentInteractionListener,RegistroJugadorFragment.OnFragmentInteractionListener,GestionJugadorFragment.OnFragmentInteractionListener, RankingFragment.OnFragmentInteractionListener {

    Fragment fragmentInicio,registroJugadorFragment,gestionJugadorFragment,rankingFragment;

    private PlotPlanitoXY plot;
    private LinearLayout display;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this,R.xml.preferencias,false);

        SharedPreferences preferences= android.preference.PreferenceManager.getDefaultSharedPreferences(this);
        PreferenciasJuego.obtenerPreferencias(preferences,getApplicationContext());

        Utilidades.obtenerListaAvatars();//llena la lista de avatars para ser utilizada
        Utilidades.consultarListaJugadores(this);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,Utilidades.NOMBRE_BD,null,1);

        fragmentInicio =new InicioFragment();
        registroJugadorFragment=new RegistroJugadorFragment();
        gestionJugadorFragment=new GestionJugadorFragment();
        rankingFragment=new RankingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments, fragmentInicio).commit();
    }

    public void onClick(View view) {

    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("GESTIONAR JUGADOR")
                .setMessage("Indique si desea registrar un nuevo jugador o si desea seleccionar uno ya existente.\n\n" +
                        "También podrá modificar un Jugador desde la opción SELECCIONAR")
                .setNegativeButton("REGISTRAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utilidades.avatarIdSeleccion=0;//solo cuando se haga lo de seleccionar avatar en jugador
                                Utilidades.avatarSeleccion=Utilidades.listaAvatars.get(0);//se asigna el primer elemento para que siempre se tenga esta referencia hasta el evento clic
                                //  Toast.makeText(getApplicationContext(),"Avatar Seleccion: "+Utilidades.avatarSeleccion.getId(),Toast.LENGTH_SHORT).show();
                                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,registroJugadorFragment).commit();
                            }
                        })
                .setPositiveButton("SELECCIONAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Utilidades.listaJugadores!=null && Utilidades.listaJugadores.size()>0)
                                {
                                    getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,gestionJugadorFragment).commit();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Debe registrar un Jugador",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        return builder.create();
    }

    /*se controla la pulsacion del boton atras*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Desea salir de MyApp?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void mostrarMenu() {
        Utilidades.obtenerListaAvatars();//llena la lista de avatars para ser utilizada
        Utilidades.consultarListaJugadores(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragmentInicio).commit();
    }

    @Override
    public void iniciarJuego() {

        if (Utilidades.listaJugadores!=null && Utilidades.listaJugadores.size()>0)
        {
            DialogoTipoJuegoFragment dialogoTipoJuego=new DialogoTipoJuegoFragment();
            dialogoTipoJuego.show(getSupportFragmentManager(),"DialogoTipoJuego");
        }else{
            Toast.makeText(getApplicationContext(),"Debe registrar un Jugador",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void llamarAjustes() {
        Intent intent=new Intent(this, AjustesActivity.class);
        startActivity(intent);
    }

    @Override
    public void consultarRanking() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,rankingFragment).commit();
    }

    @Override
    public void consultarInstrucciones() {
        Intent intent=new Intent(this, ContenedorInstruccionesActivity.class);
        startActivity(intent);
    }

    @Override
    public void gestionarUsuario() {
        createSimpleDialog().show();
    }

    @Override
    public void consultarBluetooth() {
        Intent intent=new Intent(this, BtActivity.class);
        startActivity(intent);
    }

    public void delay(Long time){
        try{
            Thread.sleep(time);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}