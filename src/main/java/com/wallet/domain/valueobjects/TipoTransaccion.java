package com.wallet.domain.valueobjects;

/**
 * Enumeración que representa los tipos de transacciones posibles.
 * 
 * Principios aplicados:
 * - SRP: Solo define tipos de transacciones
 * - OCP: Fácil de extender con nuevos tipos
 */
public enum TipoTransaccion {
    
    DEPOSITO("Depósito", "Ingreso de fondos a la cuenta"),
    RETIRO("Retiro", "Extracción de fondos de la cuenta"),
    TRANSFERENCIA_ENVIADA("Transferencia Enviada", "Envío de fondos a otra cuenta"),
    TRANSFERENCIA_RECIBIDA("Transferencia Recibida", "Recepción de fondos de otra cuenta");
    
    private final String nombre;
    private final String descripcion;
    
    TipoTransaccion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * Indica si el tipo de transacción es un crédito (aumenta el saldo).
     * 
     * @return true si es un crédito
     */
    public boolean esCredito() {
        return this == DEPOSITO || this == TRANSFERENCIA_RECIBIDA;
    }
    
    /**
     * Indica si el tipo de transacción es un débito (disminuye el saldo).
     * 
     * @return true si es un débito
     */
    public boolean esDebito() {
        return this == RETIRO || this == TRANSFERENCIA_ENVIADA;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
