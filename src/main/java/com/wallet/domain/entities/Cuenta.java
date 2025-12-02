package com.wallet.domain.entities;

import com.wallet.domain.valueobjects.Dinero;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidad que representa una Cuenta bancaria.
 * 
 * Principios aplicados:
 * - SRP: Responsable solo de la lógica de la cuenta
 * - Encapsulación: Operaciones bancarias encapsuladas
 * - Invariantes: Mantiene el saldo siempre válido
 */
public class Cuenta {
    
    private final String id;
    private final String numeroCuenta;
    private final String usuarioId;
    private Dinero saldo;
    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private boolean activa;
    
    /**
     * Constructor para crear una nueva cuenta.
     * 
     * @param usuarioId ID del usuario propietario
     */
    public Cuenta(String usuarioId) {
        this.id = UUID.randomUUID().toString();
        this.numeroCuenta = generarNumeroCuenta();
        this.usuarioId = validarUsuarioId(usuarioId);
        this.saldo = Dinero.CERO;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.activa = true;
    }
    
    /**
     * Constructor para reconstruir una cuenta existente.
     */
    public Cuenta(String id, String numeroCuenta, String usuarioId, Dinero saldo,
                  LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion, boolean activa) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.usuarioId = usuarioId;
        this.saldo = saldo;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.activa = activa;
    }
    
    /**
     * Realiza un depósito en la cuenta.
     * 
     * @param monto el monto a depositar
     * @throws IllegalArgumentException si el monto no es válido
     * @throws IllegalStateException si la cuenta no está activa
     */
    public void depositar(Dinero monto) {
        validarCuentaActiva();
        validarMontoPositivo(monto);
        
        this.saldo = this.saldo.sumar(monto);
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    /**
     * Realiza un retiro de la cuenta.
     * 
     * @param monto el monto a retirar
     * @throws IllegalArgumentException si el monto no es válido
     * @throws IllegalStateException si no hay saldo suficiente o la cuenta no está activa
     */
    public void retirar(Dinero monto) {
        validarCuentaActiva();
        validarMontoPositivo(monto);
        validarSaldoSuficiente(monto);
        
        this.saldo = this.saldo.restar(monto);
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    /**
     * Verifica si hay saldo suficiente para una operación.
     * 
     * @param monto el monto a verificar
     * @return true si hay saldo suficiente
     */
    public boolean tieneSaldoSuficiente(Dinero monto) {
        return this.saldo.esMayorOIgualQue(monto);
    }
    
    /**
     * Desactiva la cuenta.
     */
    public void desactivar() {
        this.activa = false;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    /**
     * Activa la cuenta.
     */
    public void activar() {
        this.activa = true;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    private String generarNumeroCuenta() {
        // Genera un número de cuenta de 10 dígitos
        long numero = System.currentTimeMillis() % 10000000000L;
        return String.format("%010d", numero);
    }
    
    private String validarUsuarioId(String usuarioId) {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de usuario no puede estar vacío");
        }
        return usuarioId;
    }
    
    private void validarCuentaActiva() {
        if (!this.activa) {
            throw new IllegalStateException("La cuenta no está activa");
        }
    }
    
    private void validarMontoPositivo(Dinero monto) {
        if (monto == null) {
            throw new IllegalArgumentException("El monto no puede ser nulo");
        }
        if (!monto.esPositivo()) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
    }
    
    private void validarSaldoSuficiente(Dinero monto) {
        if (!tieneSaldoSuficiente(monto)) {
            throw new IllegalStateException(
                String.format("Saldo insuficiente. Saldo actual: %s, Monto solicitado: %s", 
                    saldo, monto)
            );
        }
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public String getUsuarioId() {
        return usuarioId;
    }
    
    public Dinero getSaldo() {
        return saldo;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public boolean isActiva() {
        return activa;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(id, cuenta.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Cuenta{numero='%s', saldo=%s, activa=%s}", 
            numeroCuenta, saldo, activa);
    }
}
