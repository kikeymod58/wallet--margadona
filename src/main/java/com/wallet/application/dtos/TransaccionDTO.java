package com.wallet.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para transferencia de datos de Transacción.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class TransaccionDTO {
    
    private String id;
    private String cuentaId;
    private String tipo;
    private BigDecimal monto;
    private String moneda;
    private String descripcion;
    private LocalDateTime fecha;
    private String cuentaDestinoId; // Para transferencias
    
    // Constructor vacío
    public TransaccionDTO() {
    }
    
    // Constructor completo
    public TransaccionDTO(String id, String cuentaId, String tipo,
                          BigDecimal monto, String moneda, String descripcion,
                          LocalDateTime fecha, String cuentaDestinoId) {
        this.id = id;
        this.cuentaId = cuentaId;
        this.tipo = tipo;
        this.monto = monto;
        this.moneda = moneda;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.cuentaDestinoId = cuentaDestinoId;
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getCuentaId() {
        return cuentaId;
    }
    
    public void setCuentaId(String cuentaId) {
        this.cuentaId = cuentaId;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public BigDecimal getMonto() {
        return monto;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
    public String getMoneda() {
        return moneda;
    }
    
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    public String getCuentaDestinoId() {
        return cuentaDestinoId;
    }
    
    public void setCuentaDestinoId(String cuentaDestinoId) {
        this.cuentaDestinoId = cuentaDestinoId;
    }
    
    @Override
    public String toString() {
        return "TransaccionDTO{" +
                "id='" + id + '\'' +
                ", cuentaId='" + cuentaId + '\'' +
                ", tipo='" + tipo + '\'' +
                ", monto=" + monto +
                ", moneda='" + moneda + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", cuentaDestinoId='" + cuentaDestinoId + '\'' +
                '}';
    }
}
