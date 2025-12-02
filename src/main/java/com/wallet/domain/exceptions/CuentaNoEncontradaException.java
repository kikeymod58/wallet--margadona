package com.wallet.domain.exceptions;

/**
 * Excepción lanzada cuando no se encuentra una cuenta.
 */
public class CuentaNoEncontradaException extends RuntimeException {
    
    public CuentaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
    
    public static CuentaNoEncontradaException porId(String cuentaId) {
        return new CuentaNoEncontradaException(
            String.format("No se encontró la cuenta con ID: %s", cuentaId)
        );
    }
    
    public static CuentaNoEncontradaException porNumero(String numeroCuenta) {
        return new CuentaNoEncontradaException(
            String.format("No se encontró la cuenta con número: %s", numeroCuenta)
        );
    }
}
