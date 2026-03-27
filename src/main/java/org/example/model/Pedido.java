package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido {

    private String idExterno;
    private String optica;
    private String rut;
    private String paciente;
    private String ojo;
    private Double esfera;
    private Double cilindro;
    private Integer eje;
    private Double curvaBase;
    private Double curvaTorica;
    private double diametro;
    private String material;
    private String observaciones;
    private EstadoPedido estado;
    private LocalDateTime fechaAlta;

    public Pedido(String idExterno,
                  String optica,
                  String rut,
                  String paciente,
                  String ojo,
                  Double esfera,
                  Double cilindro,
                  Integer eje,
                  Double curvaBase,
                  Double curvaTorica,
                  double diametro,
                  String material,
                  String observaciones) {
        this.idExterno = idExterno;
        this.optica = optica;
        this.rut = rut;
        this.paciente = paciente;
        this.ojo = ojo;
        this.esfera = esfera;
        this.cilindro = cilindro;
        this.eje = eje;
        this.curvaBase = curvaBase;
        this.curvaTorica = curvaTorica;
        this.diametro = diametro;
        this.material = material;
        this.observaciones = observaciones == null ? "" : observaciones;
        this.estado = EstadoPedido.PENDIENTE;
        this.fechaAlta = LocalDateTime.now();
    }

    public String getFormulaResumen() {
        StringBuilder sb = new StringBuilder();

        if (esfera != null) {
            sb.append(String.format("ESF %.2f", esfera));
        }

        if (cilindro != null) {
            if (sb.length() > 0) sb.append(" | ");
            sb.append(String.format("CIL %.2f", cilindro));
        }

        if (eje != null) {
            if (sb.length() > 0) sb.append(" | ");
            sb.append("EJE ").append(eje);
        }

        if (sb.length() > 0) sb.append(" | ");
        sb.append("CB " + curvaBase);

        if (sb.length() > 0) sb.append(" | ");
        sb.append("CB " + curvaTorica);

            if (sb.length() > 0) sb.append(" | ");
            sb.append(String.format("DIA %.2f", diametro));


        return sb.toString();
    }

    public String getFormulaLarga() {

        return String.format(
                "Ojo %s | Esfera %.2f | Cilindro %.2f | Eje %d | Curva base %.2f | Curva tórica %.2f | Diámetro %.2f",
                ojo, esfera, cilindro, eje, curvaBase, curvaTorica, diametro
        );
    }

    public String getFechaAltaFormateada() {
        return fechaAlta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public String getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }

    public String getOptica() {
        return optica;
    }

    public void setOptica(String optica) {
        this.optica = optica;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getOjo() {
        return ojo;
    }

    public void setOjo(String ojo) {
        this.ojo = ojo;
    }

    public Double getEsfera() {
        return esfera;
    }

    public void setEsfera(Double esfera) {
        this.esfera = esfera;
    }

    public Double getCilindro() {
        return cilindro;
    }

    public void setCilindro(Double cilindro) {
        this.cilindro = cilindro;
    }

    public Integer getEje() {
        return eje;
    }

    public void setEje(Integer eje) {
        this.eje = eje;
    }

    public Double getCurvaBase() {
        return curvaBase;
    }

    public void setCurvaBase(Double curvaBase) {
        this.curvaBase = curvaBase;
    }

    public Double getCurvaTorica() {
        return curvaTorica;
    }

    public void setCurvaTorica(Double curvaTorica) {
        this.curvaTorica = curvaTorica;
    }

    public double getDiametro() {
        return diametro;
    }

    public void setDiametro(double diametro) {
        this.diametro = diametro;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones == null ? "" : observaciones;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}