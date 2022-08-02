package com.msam.myapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.msam.myapp.R;
import com.msam.myapp.clases.PreferenciasJuego;
import com.msam.myapp.clases.Utilidades;
import com.msam.myapp.clases.vo.AvatarVo;
import com.msam.myapp.clases.vo.JugadorVo;

public class AdaptadorJugador extends RecyclerView.Adapter<AdaptadorJugador.ViewHolderJugador> implements View.OnClickListener {

    private View.OnClickListener listener;
    List<JugadorVo> listaJugador;
    View vista;

    public AdaptadorJugador(List<JugadorVo> listaJugador) {
        this.listaJugador = listaJugador;
    }

    @NonNull
    @Override
    public ViewHolderJugador onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        vista=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_jugador,viewGroup,false);
        vista.setOnClickListener(this);
        return new ViewHolderJugador(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderJugador viewHolderJugador, int i) {

        //se resta uno ya que buscamos la lista de elementos que inicia en la pos 0
        viewHolderJugador.imgAvatar.setImageResource(Utilidades.listaAvatars.get(listaJugador.get(i).getAvatar()-1).getAvatarId());
        viewHolderJugador.txtNombre.setText(listaJugador.get(i).getNombre());
        viewHolderJugador.txtEdad.setText(listaJugador.get(i).getEdad());
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return listaJugador.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderJugador extends RecyclerView.ViewHolder {

        ImageView imgAvatar;
        TextView txtNombre;
        TextView txtEdad;

        public ViewHolderJugador(@NonNull View itemView) {
            super(itemView);
            imgAvatar=itemView.findViewById(R.id.idAvatar);
            txtNombre=itemView.findViewById(R.id.idNombre);
            txtEdad=itemView.findViewById(R.id.idEdad);
        }

    }
}
