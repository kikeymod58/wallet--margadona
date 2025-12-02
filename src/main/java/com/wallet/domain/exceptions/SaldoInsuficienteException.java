package com.wallet.domain.exceptions;

/**
 * Excepción lanzada cuando no hay saldo suficiente para una operación.
 */
public class SaldoInsuficienteException extends RuntimeException {
    
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
    
    public SaldoInsuficienteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
