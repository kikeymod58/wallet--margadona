package com.wallet.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.wallet.domain.valueobjects.DocumentoIdentidad;
import com.wallet.domain.valueobjects.Email;

/**
 * Entidad que representa un Usuario del sistema.
 * 
 * Principios aplicados:
 * - SRP: Responsable solo de la lógica del usuario
 * - Encapsulación: Los atributos son privados con getters apropiados
 * - Identidad: Identificado por un ID único
 */
public class Usuario {
    
    private final String id;
    private String nombre;
    private String apellido;
    private Email email;
    private DocumentoIdentidad documentoIdentidad;
    private final LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private boolean activo;
    
    /**
     * Constructor para crear un nuevo usuario.
     * 
     * @param nombre el nombre del usuario
     * @param apellido el apellido del usuario
     * @param email el email del usuario
     * @param documentoIdentidad el documento de identidad
     */
    public Usuario(String nombre, String apellido, Email email, DocumentoIdentidad documentoIdentidad) {
        this.id = UUID.randomUUID().toString();
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
        this.activo = true;
        
        setNombre(nombre);
        setApellido(apellido);
        setEmail(email);
        setDocumentoIdentidad(documentoIdentidad);
    }
    
    /**
     * Constructor para reconstruir un usuario existente (desde base de datos).
     * 
     * @param id el ID del usuario
     * @param nombre el nombre
     * @param apellido el apellido
     * @param email el email
     * @param documentoIdentidad el documento
     * @param fechaCreacion fecha de creación
     * @param fechaActualizacion fecha de actualización
     * @param activo estado del usuario
     */
    public Usuario(String id, String nombre, String apellido, Email email, 
                   DocumentoIdentidad documentoIdentidad, LocalDateTime fechaCreacion,
                   LocalDateTime fechaActualizacion, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.documentoIdentidad = documentoIdentidad;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.activo = activo;
    }
    
    /**
     * Actualiza los datos del usuario.
     * 
     * @param nombre nuevo nombre
     * @param apellido nuevo apellido
     * @param email nuevo email
     */
    public void actualizar(String nombre, String apellido, Email email) {
        setNombre(nombre);
        setApellido(apellido);
        setEmail(email);
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    /**
     * Desactiva el usuario.
     */
    public void desactivar() {
        this.activo = false;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    /**
     * Activa el usuario.
     */
    public void activar() {
        this.activo = true;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    /**
     * Obtiene el nombre completo del usuario.
     * 
     * @return nombre completo
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    private void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nombre.length() > 50) {
            throw new IllegalArgumentException("El nombre no puede tener más de 50 caracteres");
        }
        this.nombre = nombre.trim();
    }
    
    private void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío");
        }
        if (apellido.length() > 50) {
            throw new IllegalArgumentException("El apellido no puede tener más de 50 caracteres");
        }
        this.apellido = apellido.trim();
    }
    
    private void setEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("El email no puede ser nulo");
        }
        this.email = email;
    }
    
    private void setDocumentoIdentidad(DocumentoIdentidad documentoIdentidad) {
        if (documentoIdentidad == null) {
            throw new IllegalArgumentException("El documento de identidad no puede ser nulo");
        }
        this.documentoIdentidad = documentoIdentidad;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public DocumentoIdentidad getDocumentoIdentidad() {
        return documentoIdentidad;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Usuario{id='%s', nombre='%s', email='%s', activo=%s}", 
            id, getNombreCompleto(), email, activo);
    }
}
