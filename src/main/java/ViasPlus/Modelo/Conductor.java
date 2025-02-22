package ViasPlus.Modelo;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "conductores")
public class Conductor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String licencia;
    private int entregasTotales;
    private double calificacion;

    @OneToMany(mappedBy = "conductor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos; // Relación con Pedido

    // 🔹 Constructor vacío (JPA lo necesita)
    public Conductor() {
    }

    // 🔹 Constructor con parámetros
    public Conductor(String nombre, String licencia, int entregasTotales, double calificacion) {
        this.nombre = nombre;
        this.licencia = licencia;
        this.entregasTotales = entregasTotales;
        this.calificacion = calificacion;
    }

    // 🔹 Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public int getEntregasTotales() {
        return entregasTotales;
    }

    public void setEntregasTotales(int entregasTotales) {
        this.entregasTotales = entregasTotales;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
