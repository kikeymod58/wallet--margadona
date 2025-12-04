package com.wallet.application.dtos.requests;

import java.math.BigDecimal;

/**
 * Request DTO para transferir dinero entre cuentas.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class TransferirDineroRequest {
    
    private String cuentaOrigenId;
    private String cuentaDestinoId;
    private BigDecimal monto;
    private String descripcion;
    
    public TransferirDineroRequest() {
    }
    
    public TransferirDineroRequest(String cuentaOrigenId, String cuentaDestinoId,
                                   BigDecimal monto, String descripcion) {
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.monto = monto;
        this.descripcion = descripcion;
    }
    
    public String getCuentaOrigenId() {
        return cuentaOrigenId;
    }
    
    public void setCuentaOrigenId(String cuentaOrigenId) {
        this.cuentaOrigenId = cuentaOrigenId;
    }
    
    public String getCuentaDestinoId() {
        return cuentaDestinoId;
    }
    
    public void setCuentaDestinoId(String cuentaDestinoId) {
        this.cuentaDestinoId = cuentaDestinoId;
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
