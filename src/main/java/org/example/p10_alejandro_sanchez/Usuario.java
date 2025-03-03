package org.example.p10_alejandro_sanchez;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String identificador;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "campo_calculado", nullable = false)
    private BigDecimal campoCalculado = BigDecimal.ZERO;  // Ahora es BigDecimal

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getCampoCalculado() {  // Cambiado a BigDecimal
        return campoCalculado;
    }

    public void setCampoCalculado(BigDecimal campoCalculado) { // Cambiado a BigDecimal
        this.campoCalculado = campoCalculado;
    }
}