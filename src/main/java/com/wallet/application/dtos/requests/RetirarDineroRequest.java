package com.wallet.application.dtos.requests;

import java.math.BigDecimal;

/**
 * Request DTO para retirar dinero de una cuenta.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class RetirarDineroRequest {
    
    private String cuentaId;
    private BigDecimal monto;
    private String descripcion;
    
    public RetirarDineroRequest() {
    }
    
    public RetirarDineroRequest(String cuentaId, BigDecimal monto, String descripcion) {
        this.cuentaId = cuentaId;
        this.monto = monto;
        this.descripcion = descripcion;
    }
    
    public String getCuentaId() {
        return cuentaId;
    }
    
    public void setCuentaId(String cuentaId) {
        this.cuentaId = cuentaId;
    }
    
    public BigDecimal getMonto() {
        return monto;
    }
    
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
