package com.wallet.application.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para transferencia de datos de Cuenta.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class CuentaDTO {
    
    private String id;
    private String numeroCuenta;
    private String usuarioId;
    private BigDecimal saldo;
    private String moneda;
    private boolean activa;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    
    // Constructor vacío
    public CuentaDTO() {
    }
    
    // Constructor completo
    public CuentaDTO(String id, String numeroCuenta, String usuarioId,
                     BigDecimal saldo, String moneda, boolean activa,
                     LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.usuarioId = usuarioId;
        this.saldo = saldo;
        this.moneda = moneda;
        this.activa = activa;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }
    
    // Getters y Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
    public String getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public BigDecimal getSaldo() {
        return saldo;
    }
    
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    public String getMoneda() {
        return moneda;
    }
    
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    
    public boolean isActiva() {
        return activa;
    }
    
    public void setActiva(boolean activa) {
        this.activa = activa;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    @Override
    public String toString() {
        return "CuentaDTO{" +
                "id='" + id + '\'' +
                ", numeroCuenta='" + numeroCuenta + '\'' +
                ", usuarioId='" + usuarioId + '\'' +
                ", saldo=" + saldo +
                ", moneda='" + moneda + '\'' +
                ", activa=" + activa +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
