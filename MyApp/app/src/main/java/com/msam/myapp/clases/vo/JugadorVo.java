package com.msam.myapp.clases.vo;

public class JugadorVo {

    private int id;
    private String nombre;
    private String edad;
    private int avatar;

    public JugadorVo(int id, String nombre, String edad, int avatar) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.avatar = avatar;
    }

    public JugadorVo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String genero) {
        this.edad = edad;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
