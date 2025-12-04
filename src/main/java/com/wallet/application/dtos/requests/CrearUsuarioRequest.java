package com.wallet.application.dtos.requests;

/**
 * Request DTO para crear un nuevo usuario.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class CrearUsuarioRequest {
    
    private String nombre;
    private String apellido;
    private String email;
    private String tipoDocumento;
    private String numeroDocumento;
    
    public CrearUsuarioRequest() {
    }
    
    public CrearUsuarioRequest(String nombre, String apellido, String email,
                               String tipoDocumento, String numeroDocumento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTipoDocumento() {
        return tipoDocumento;
    }
    
    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public String getNumeroDocumento() {
        return numeroDocumento;
    }
    
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
}
