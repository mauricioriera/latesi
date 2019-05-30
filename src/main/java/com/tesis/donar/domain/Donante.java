package com.tesis.donar.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Donante.
 */
@Entity
@Table(name = "donante")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Donante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull
    @Column(name = "sexo", nullable = false)
    private String sexo;

    @NotNull
    @Column(name = "grupo_sanguineo", nullable = false)
    private String grupoSanguineo;

    @NotNull
    @Column(name = "factor_sanguineo", nullable = false)
    private String factorSanguineo;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne
    @JsonIgnoreProperties("donantes")
    private Hospital hospital;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Donante nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Donante apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public Donante direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Donante telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public Donante email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Donante fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public Donante sexo(String sexo) {
        this.sexo = sexo;
        return this;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public Donante grupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
        return this;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getFactorSanguineo() {
        return factorSanguineo;
    }

    public Donante factorSanguineo(String factorSanguineo) {
        this.factorSanguineo = factorSanguineo;
        return this;
    }

    public void setFactorSanguineo(String factorSanguineo) {
        this.factorSanguineo = factorSanguineo;
    }

    public Boolean isActivo() {
        return activo;
    }

    public Donante activo(Boolean activo) {
        this.activo = activo;
        return this;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Donante hospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Donante)) {
            return false;
        }
        return id != null && id.equals(((Donante) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Donante{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", email='" + getEmail() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", grupoSanguineo='" + getGrupoSanguineo() + "'" +
            ", factorSanguineo='" + getFactorSanguineo() + "'" +
            ", activo='" + isActivo() + "'" +
            "}";
    }
}
