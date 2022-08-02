package com.msam.myapp.clases.vo;

public class ResultadosVo {

    private int id;
    private String nombre;
    private String edad;
    private int avatar;
    private int puntos;
    private String nivel;
    private String modo;

    public ResultadosVo() {
    }

    public ResultadosVo(int id, String nombre, String edad, int avatar, int puntos, String nivel, String modo) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.avatar = avatar;
        this.puntos = puntos;
        this.nivel = nivel;
        this.modo = modo;
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

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }
}
