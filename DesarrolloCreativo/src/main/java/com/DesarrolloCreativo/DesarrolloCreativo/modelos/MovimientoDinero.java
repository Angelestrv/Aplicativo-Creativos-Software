package com.DesarrolloCreativo.DesarrolloCreativo.modelos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table (name = "MovimientoDinero")
public class MovimientoDinero {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int ID;
    private long monto;
    private String concepto;
    @ManyToOne
    @JoinColumn (name = "usuario_id")
    private Usuario usuario;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;


    //Constructor

    public MovimientoDinero() {
    }

    public MovimientoDinero(long monto, String concepto, Usuario usuario, Date fecha) {
        this.monto = monto;
        this.concepto = concepto;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    //Setters and Getters


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public long getMonto() {
        return monto;
    }

    public void setMonto(long monto) {
        this.monto = monto;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
