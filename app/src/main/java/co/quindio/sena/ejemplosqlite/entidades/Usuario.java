package co.quindio.sena.ejemplosqlite.entidades;

import java.io.Serializable;

/**
 * Created by CHENAO on 7/05/2017.
 */

public class Usuario implements  Serializable{

    private Integer id;
    private String nombre;
    private String asistencia;

    public Usuario(Integer id, String nombre, String asistencia) {
        this.id = id;
        this.nombre = nombre;
        this.asistencia = asistencia;
    }

    public Usuario(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }
}
