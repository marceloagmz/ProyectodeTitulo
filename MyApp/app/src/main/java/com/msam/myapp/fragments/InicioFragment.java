package com.msam.myapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.msam.myapp.R;
import com.msam.myapp.clases.PreferenciasJuego;
import com.msam.myapp.clases.Utilidades;
import com.msam.myapp.interfaces.IComunicaFragments;

public class InicioFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View vista;
    Activity actividad;
    RelativeLayout layoutFondo;
    GridLayout gridMenu;
    CardView cardJugar,cardAjustes,cardRanking,cardAyuda,cardUser,cardInfo;

    IComunicaFragments interfaceComunicaFragments;

    TextView textNickName;
    ImageView imagenAvatar;

    public InicioFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_inicio, container, false);
        layoutFondo=vista.findViewById(R.id.idLayoutFondo);
        gridMenu=vista.findViewById(R.id.idGrid);
        cardJugar=vista.findViewById(R.id.cardJugar);
        cardAjustes=vista.findViewById(R.id.cardAjustes);
        cardRanking=vista.findViewById(R.id.cardRanking);
        cardAyuda=vista.findViewById(R.id.cardAyuda);
        cardUser=vista.findViewById(R.id.cardUser);
        cardInfo=vista.findViewById(R.id.cardInfo);
        textNickName=vista.findViewById(R.id.textNickName);
        imagenAvatar=vista.findViewById(R.id.avatarImage);


        textNickName.setText(PreferenciasJuego.nickName);

        if (PreferenciasJuego.avatarId==1){
            imagenAvatar.setImageResource(R.drawable.cara_simio_banner);
        }else{
            imagenAvatar.setImageResource(Utilidades.listaAvatars.get(PreferenciasJuego.avatarId-1).getAvatarId());
        }

        eventosMenu();

        return vista;
    }

    public AlertDialog createSimpleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(actividad);

        builder.setTitle("Ayuda")
                .setMessage("La aplicación cuenta con 2 niveles de juego que pueden ser configurables desde los Ajustes, para iniciar " +
                        "debes crear un Jugador o seleccionar uno de los existentes. Navega desde el menú principal")
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }

    //Permite asignar las preferencias y cambiar el modo y color del banner personalizado
    private void asignarValoresPreferencias() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layoutFondo.setBackground(getResources().getDrawable(PreferenciasJuego.formaBanner));
        }else{
            layoutFondo.setBackgroundDrawable(getResources().getDrawable(PreferenciasJuego.formaBanner));
        }

        Drawable shape = (Drawable) layoutFondo.getBackground();
        shape.setColorFilter(getResources().getColor(PreferenciasJuego.colorTema), android.graphics.PorterDuff.Mode.SRC);

    }

    private void eventosMenu() {

        cardJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.iniciarJuego();
            }
        });

        cardAjustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.llamarAjustes();
            }
        });

        cardRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.consultarRanking();
            }
        });

        cardAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.consultarInstrucciones();
            }
        });

        cardUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.gestionarUsuario();
            }
        });

        cardInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.consultarBluetooth();
            }
        });

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
            this.actividad=(Activity) context;
            interfaceComunicaFragments= (IComunicaFragments) this.actividad;
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

    //El fragment es visible, por eso llamamos a los valores de las preferencias
    @Override
    public void onResume() {
        super.onResume();
        asignarValoresPreferencias();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
