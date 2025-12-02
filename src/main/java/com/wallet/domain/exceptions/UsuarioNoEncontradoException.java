package com.wallet.domain.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un usuario.
 */
public class UsuarioNoEncontradoException extends RuntimeException {
    
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
    public static UsuarioNoEncontradoException porId(String usuarioId) {
        return new UsuarioNoEncontradoException(
            String.format("No se encontró el usuario con ID: %s", usuarioId)
        );
    }
    
    public static UsuarioNoEncontradoException porEmail(String email) {
        return new UsuarioNoEncontradoException(
            String.format("No se encontró el usuario con email: %s", email)
        );
    }
}
