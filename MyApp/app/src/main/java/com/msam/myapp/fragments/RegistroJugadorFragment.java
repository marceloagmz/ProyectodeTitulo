package com.msam.myapp.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.msam.myapp.R;
import com.msam.myapp.adapters.AdaptadorAvatar;
import com.msam.myapp.clases.ConexionSQLiteHelper;
import com.msam.myapp.clases.Utilidades;
import com.msam.myapp.interfaces.IComunicaFragments;
import com.msam.myapp.clases.PreferenciasJuego;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistroJugadorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistroJugadorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroJugadorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Activity actividad;
    View vista;
    IComunicaFragments iComunicaFragments;
    RelativeLayout layoutFondo;

    RecyclerView recyclerAvatars;

    ImageButton btnAtras;
    FloatingActionButton fabRegistro;
    EditText campoNick, campoEdad;

    public RegistroJugadorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroJugadorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroJugadorFragment newInstance(String param1, String param2) {
        RegistroJugadorFragment fragment = new RegistroJugadorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_registro_jugador, container, false);

        recyclerAvatars =vista.findViewById(R.id.recyclerAvatarsId);
        layoutFondo=vista.findViewById(R.id.idLayoutFondo);

        layoutFondo.setBackgroundColor(getResources().getColor(PreferenciasJuego.colorTema));

        btnAtras=vista.findViewById(R.id.btnIcoAtras);
        fabRegistro=vista.findViewById(R.id.idFabRegistro);

        campoNick=vista.findViewById(R.id.campoNickName);
        campoEdad=vista.findViewById(R.id.campoEdad);

        recyclerAvatars.setLayoutManager(new GridLayoutManager(this.actividad,3));
        recyclerAvatars.setHasFixedSize(true);

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComunicaFragments.mostrarMenu();
                campoNick.setText("");
            }
        });

        fabRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrarJugador();
            }
        });

        //se asigna la lista de avatars por defecto
        final AdaptadorAvatar miAdaptador=new AdaptadorAvatar(Utilidades.listaAvatars);

        recyclerAvatars.setAdapter(miAdaptador);

        return vista;
    }

    private void registrarJugador() {

        if (campoNick.getText().toString()!=null && !campoNick.getText().toString().trim().equals("") && campoEdad.getText().toString()!=null && !campoEdad.getText().toString().trim().equals("")){
            String nickName=campoNick.getText().toString();
            String edad=campoEdad.getText().toString();

            int avatarId=Utilidades.avatarSeleccion.getId();

            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(actividad,Utilidades.NOMBRE_BD,null,1);

            SQLiteDatabase db=conn.getWritableDatabase();

            ContentValues values=new ContentValues();
            values.put(Utilidades.CAMPO_NOMBRE,nickName);
            values.put(Utilidades.CAMPO_EDAD,edad);
            values.put(Utilidades.CAMPO_AVATAR,avatarId);

            Long idResultante=db.insert(Utilidades.TABLA_JUGADOR,Utilidades.CAMPO_ID,values);

            if(idResultante!=-1){
                PreferenciasJuego.jugadorId=Integer.parseInt(idResultante+"");
                Toast.makeText(actividad,"El Jugador a sido Registrado con Exito!",Toast.LENGTH_SHORT).show();
            //    Toast.makeText(actividad,"Id Registro resultante: "+idResultante,Toast.LENGTH_LONG).show();
                PreferenciasJuego.nickName="{ "+campoNick.getText().toString()+" }";
                PreferenciasJuego.edad="{ "+campoEdad.getText().toString()+" }";
                PreferenciasJuego.avatarId=Utilidades.avatarSeleccion.getId();
                SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(actividad);
                PreferenciasJuego.asignarPreferenciasJugador(preferences,actividad);
                campoNick.setText("");
                campoEdad.setText("");
                iComunicaFragments.mostrarMenu();
            }else
                Toast.makeText(actividad,"No se pudo Registrar el Jugador! ",Toast.LENGTH_SHORT).show();

            db.close();

        }else{
            Toast.makeText(actividad,"Verifique los datos de registro",Toast.LENGTH_SHORT).show();
        }

    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(actividad);

        builder.setTitle("Ayuda")
                .setMessage("Ingrese el Nickname, Edad y seleccione el Avatar del jugador de la lista de avatars disponibles, posteriormente presione el botón para confirmar el registro.")
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad= (Activity) context;
            iComunicaFragments= (IComunicaFragments) this.actividad;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
