package com.wallet.domain.valueobjects;

import java.util.Objects;

/**
 * Value Object que representa un documento de identidad.
 * Inmutable y con validaciones.
 * 
 * Principios aplicados:
 * - SRP: Solo responsable de validar y representar un documento
 * - Inmutabilidad: No puede ser modificado
 */
public final class DocumentoIdentidad {
    
    private final String numero;
    private final TipoDocumento tipo;
    
    public enum TipoDocumento {
        DNI("DNI", 8),
        PASAPORTE("Pasaporte", 12),
        CEDULA("Cédula", 10);
        
        private final String nombre;
        private final int longitudMaxima;
        
        TipoDocumento(String nombre, int longitudMaxima) {
            this.nombre = nombre;
            this.longitudMaxima = longitudMaxima;
        }
        
        public String getNombre() {
            return nombre;
        }
        
        public int getLongitudMaxima() {
            return longitudMaxima;
        }
    }
    
    /**
     * Crea un nuevo DocumentoIdentidad.
     * 
     * @param numero el número del documento
     * @param tipo el tipo de documento
     * @throws IllegalArgumentException si los datos son inválidos
     */
    public DocumentoIdentidad(String numero, TipoDocumento tipo) {
        validar(numero, tipo);
        this.numero = numero.trim();
        this.tipo = tipo;
    }
    
    private void validar(String numero, TipoDocumento tipo) {
        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de documento no puede estar vacío");
        }
        
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de documento no puede ser nulo");
        }
        
        String numeroLimpio = numero.trim();
        
        if (numeroLimpio.length() > tipo.getLongitudMaxima()) {
            throw new IllegalArgumentException(
                String.format("El %s no puede tener más de %d caracteres", 
                    tipo.getNombre(), tipo.getLongitudMaxima())
            );
        }
        
        // Validar que contenga solo números y letras (pasaportes pueden tener letras)
        if (!numeroLimpio.matches("^[A-Za-z0-9]+$")) {
            throw new IllegalArgumentException("El documento solo puede contener letras y números");
        }
    }
    
    public String getNumero() {
        return numero;
    }
    
    public TipoDocumento getTipo() {
        return tipo;
    }
    
    public String getNumeroCompleto() {
        return tipo.getNombre() + ": " + numero;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentoIdentidad that = (DocumentoIdentidad) o;
        return Objects.equals(numero, that.numero) && tipo == that.tipo;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(numero, tipo);
    }
    
    @Override
    public String toString() {
        return getNumeroCompleto();
    }
}
