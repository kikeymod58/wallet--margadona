package com.wallet.domain.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object que representa un email válido.
 * Inmutable y con validaciones.
 * 
 * Principios aplicados:
 * - SRP: Solo responsable de validar y representar un email
 * - Inmutabilidad: Garantiza que no pueda ser modificado una vez creado
 * - Fail-fast: Valida en el constructor
 */
public final class Email {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    private final String valor;
    
    /**
     * Crea un nuevo Email validando su formato.
     * 
     * @param valor el email en formato string
     * @throws IllegalArgumentException si el email es inválido
     */
    public Email(String valor) {
        validar(valor);
        this.valor = valor.toLowerCase().trim();
    }
    
    private void validar(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío");
        }
        
        if (!EMAIL_PATTERN.matcher(valor.trim()).matches()) {
            throw new IllegalArgumentException("El formato del email es inválido: " + valor);
        }
        
        if (valor.length() > 100) {
            throw new IllegalArgumentException("El email es demasiado largo (máximo 100 caracteres)");
        }
    }
    
    public String getValor() {
        return valor;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(valor, email.valor);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
    
    @Override
    public String toString() {
        return valor;
    }
}
