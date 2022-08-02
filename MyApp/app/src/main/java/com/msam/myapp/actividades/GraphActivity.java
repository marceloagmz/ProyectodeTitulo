package com.msam.myapp.actividades;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.juang.jplot.PlotPlanitoXY;
import com.msam.myapp.R;
import com.msam.myapp.Ss;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GraphActivity extends AppCompatActivity {

    private String NameOfFolder = "/GSR";
    private String NameOfFile = "imagen";
    private PlotPlanitoXY plot;
    private LinearLayout pantalla;
    private ImageView imageView;
    Context context;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        btnAdd=findViewById(R.id.btnAdd);
        imageView = (ImageView)findViewById(R.id.imageView);
        pantalla = (LinearLayout)(findViewById(R.id.pantalla));
        context = this;

        float[] x = new float[100];
        float[] y = new float[420];

        for (int i=0; i<x.length; i++)
            x[i] = i;

        for (int i=0; i<y.length; i++)
            y[i] = (int) (Math.random()*20)+1;

        plot = new PlotPlanitoXY(context,"Señal GSR","Tiempo","Resistividad (Siemens)");
        plot.SetSerie1(x,y,"graph 1",5,true);
        plot.SetHD(true);

        pantalla.addView(plot);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Bitmap b = Ss.takescreenshotOfRootView(pantalla);
                imageView.setImageBitmap(b);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                guardarImagen(context,"image", b);
            }
        });
    }


    private String guardarImagen (Context context, String nombre, Bitmap imagen){
        ContextWrapper cw = new ContextWrapper(context);
        File dirImages = cw.getDir("Imagenes", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, "image" + ".png");

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            imagen.compress(Bitmap.CompressFormat.JPEG, 10, fos);
            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }
}
