package com.wallet.domain.entities;

import com.wallet.domain.valueobjects.Dinero;
import com.wallet.domain.valueobjects.TipoTransaccion;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidad que representa una Transacción bancaria.
 * 
 * Principios aplicados:
 * - SRP: Responsable solo de representar una transacción
 * - Inmutabilidad: Una vez creada, no se puede modificar (Event Sourcing)
 * - Registro de auditoría: Mantiene toda la información para trazabilidad
 */
public class Transaccion {
    
    private final String id;
    private final TipoTransaccion tipo;
    private final Dinero monto;
    private final String cuentaOrigenId;
    private final String cuentaDestinoId; // Null para depósitos y retiros
    private final String descripcion;
    private final LocalDateTime fecha;
    private final Dinero saldoAnterior;
    private final Dinero saldoNuevo;
    
    /**
     * Constructor para crear una transacción (depósito o retiro).
     * 
     * @param tipo el tipo de transacción
     * @param monto el monto de la transacción
     * @param cuentaId el ID de la cuenta
     * @param descripcion descripción de la transacción
     * @param saldoAnterior saldo antes de la transacción
     * @param saldoNuevo saldo después de la transacción
     */
    public Transaccion(TipoTransaccion tipo, Dinero monto, String cuentaId, 
                      String descripcion, Dinero saldoAnterior, Dinero saldoNuevo) {
        this(tipo, monto, cuentaId, null, descripcion, saldoAnterior, saldoNuevo);
    }
    
    /**
     * Constructor completo para transacciones (incluyendo transferencias).
     * 
     * @param tipo el tipo de transacción
     * @param monto el monto de la transacción
     * @param cuentaOrigenId ID de la cuenta origen
     * @param cuentaDestinoId ID de la cuenta destino (null si no aplica)
     * @param descripcion descripción
     * @param saldoAnterior saldo anterior
     * @param saldoNuevo saldo nuevo
     */
    public Transaccion(TipoTransaccion tipo, Dinero monto, String cuentaOrigenId,
                      String cuentaDestinoId, String descripcion, 
                      Dinero saldoAnterior, Dinero saldoNuevo) {
        validar(tipo, monto, cuentaOrigenId, descripcion, saldoAnterior, saldoNuevo);
        
        this.id = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.monto = monto;
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.descripcion = descripcion != null ? descripcion.trim() : "";
        this.fecha = LocalDateTime.now();
        this.saldoAnterior = saldoAnterior;
        this.saldoNuevo = saldoNuevo;
    }
    
    /**
     * Constructor para reconstruir una transacción existente.
     */
    public Transaccion(String id, TipoTransaccion tipo, Dinero monto, String cuentaOrigenId,
                      String cuentaDestinoId, String descripcion, LocalDateTime fecha,
                      Dinero saldoAnterior, Dinero saldoNuevo) {
        this.id = id;
        this.tipo = tipo;
        this.monto = monto;
        this.cuentaOrigenId = cuentaOrigenId;
        this.cuentaDestinoId = cuentaDestinoId;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.saldoAnterior = saldoAnterior;
        this.saldoNuevo = saldoNuevo;
    }
    
    /**
     * Crea una transacción de depósito.
     */
    public static Transaccion deposito(Dinero monto, String cuentaId, String descripcion,
                                       Dinero saldoAnterior, Dinero saldoNuevo) {
        return new Transaccion(TipoTransaccion.DEPOSITO, monto, cuentaId, 
                              descripcion, saldoAnterior, saldoNuevo);
    }
    
    /**
     * Crea una transacción de retiro.
     */
    public static Transaccion retiro(Dinero monto, String cuentaId, String descripcion,
                                     Dinero saldoAnterior, Dinero saldoNuevo) {
        return new Transaccion(TipoTransaccion.RETIRO, monto, cuentaId, 
                              descripcion, saldoAnterior, saldoNuevo);
    }
    
    /**
     * Crea una transacción de transferencia enviada.
     */
    public static Transaccion transferenciaEnviada(Dinero monto, String cuentaOrigenId,
                                                  String cuentaDestinoId, String descripcion,
                                                  Dinero saldoAnterior, Dinero saldoNuevo) {
        return new Transaccion(TipoTransaccion.TRANSFERENCIA_ENVIADA, monto, 
                              cuentaOrigenId, cuentaDestinoId, descripcion, 
                              saldoAnterior, saldoNuevo);
    }
    
    /**
     * Crea una transacción de transferencia recibida.
     */
    public static Transaccion transferenciaRecibida(Dinero monto, String cuentaDestinoId,
                                                   String cuentaOrigenId, String descripcion,
                                                   Dinero saldoAnterior, Dinero saldoNuevo) {
        return new Transaccion(TipoTransaccion.TRANSFERENCIA_RECIBIDA, monto, 
                              cuentaDestinoId, cuentaOrigenId, descripcion, 
                              saldoAnterior, saldoNuevo);
    }
    
    private void validar(TipoTransaccion tipo, Dinero monto, String cuentaId,
                        String descripcion, Dinero saldoAnterior, Dinero saldoNuevo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de transacción no puede ser nulo");
        }
        if (monto == null || !monto.esPositivo()) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
        if (cuentaId == null || cuentaId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID de cuenta no puede estar vacío");
        }
        if (saldoAnterior == null) {
            throw new IllegalArgumentException("El saldo anterior no puede ser nulo");
        }
        if (saldoNuevo == null) {
            throw new IllegalArgumentException("El saldo nuevo no puede ser nulo");
        }
        if (descripcion != null && descripcion.length() > 200) {
            throw new IllegalArgumentException("La descripción no puede tener más de 200 caracteres");
        }
    }
    
    /**
     * Verifica si es una transferencia.
     */
    public boolean esTransferencia() {
        return tipo == TipoTransaccion.TRANSFERENCIA_ENVIADA || 
               tipo == TipoTransaccion.TRANSFERENCIA_RECIBIDA;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public TipoTransaccion getTipo() {
        return tipo;
    }
    
    public Dinero getMonto() {
        return monto;
    }
    
    public String getCuentaOrigenId() {
        return cuentaOrigenId;
    }
    
    public String getCuentaDestinoId() {
        return cuentaDestinoId;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    public Dinero getSaldoAnterior() {
        return saldoAnterior;
    }
    
    public Dinero getSaldoNuevo() {
        return saldoNuevo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaccion that = (Transaccion) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Transaccion{tipo=%s, monto=%s, fecha=%s}", 
            tipo, monto, fecha);
    }
}
