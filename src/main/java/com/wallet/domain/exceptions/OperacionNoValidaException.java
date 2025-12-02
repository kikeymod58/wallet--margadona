package com.wallet.domain.exceptions;

/**
 * Excepción lanzada cuando la operación no es válida.
 */
public class OperacionNoValidaException extends RuntimeException {
    
    public OperacionNoValidaException(String mensaje) {
        super(mensaje);
    }
    
    public static OperacionNoValidaException porque(String razon) {
        return new OperacionNoValidaException(razon);
    }
    
    public OperacionNoValidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
