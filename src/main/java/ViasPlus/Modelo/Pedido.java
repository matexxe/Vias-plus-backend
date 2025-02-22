package ViasPlus.Modelo;

import ViasPlus.Estatus.EstatusPedido;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Clientes cliente;

    @ManyToOne
    @JoinColumn(name = "conductor_id", nullable = false)  // Relación con Conductor
    private Conductor conductor;

    private String direccion;

    @Enumerated(EnumType.STRING)
    private EstatusPedido estatus;
    private String articulo;
    private String negocio;
    private LocalDate fecha;

    // 🔹 Constructor vacío (JPA lo necesita)
    public Pedido() {
    }

    //  Constructor
    public Pedido(Clientes cliente, Conductor conductor, String direccion, EstatusPedido estatus,
                  String articulo, String negocio, LocalDate fecha) {
        this.cliente = cliente;
        this.conductor = conductor;
        this.direccion = direccion;
        this.estatus = estatus;
        this.articulo = articulo;
        this.negocio = negocio;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public EstatusPedido getEstatus() {
        return estatus;
    }

    public void setEstatus(EstatusPedido estatus) {
        this.estatus = estatus;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getNegocio() {
        return negocio;
    }

    public void setNegocio(String negocio) {
        this.negocio = negocio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}

