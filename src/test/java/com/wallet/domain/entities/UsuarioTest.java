package com.wallet.domain.entities;

import com.wallet.domain.valueobjects.Email;
import com.wallet.domain.valueobjects.DocumentoIdentidad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la entidad Usuario.
 */
class UsuarioTest {
    
    @Test
    void deberiaCrearUsuarioConDatosValidos() {
        // Arrange
        Email email = new Email("juan@example.com");
        DocumentoIdentidad documento = new DocumentoIdentidad("12345678", 
            DocumentoIdentidad.TipoDocumento.DNI);
        
        // Act
        Usuario usuario = new Usuario("Juan", "Pérez", email, documento);
        
        // Assert
        assertNotNull(usuario.getId());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Pérez", usuario.getApellido());
        assertEquals(email, usuario.getEmail());
        assertEquals(documento, usuario.getDocumentoIdentidad());
        assertTrue(usuario.isActivo());
        assertNotNull(usuario.getFechaCreacion());
    }
    
    @Test
    void deberiaObtenerNombreCompleto() {
        // Arrange
        Email email = new Email("juan@example.com");
        DocumentoIdentidad documento = new DocumentoIdentidad("12345678", 
            DocumentoIdentidad.TipoDocumento.DNI);
        Usuario usuario = new Usuario("Juan", "Pérez", email, documento);
        
        // Act
        String nombreCompleto = usuario.getNombreCompleto();
        
        // Assert
        assertEquals("Juan Pérez", nombreCompleto);
    }
    
    @Test
    void deberiaActualizarDatos() {
        // Arrange
        Email emailOriginal = new Email("juan@example.com");
        Email emailNuevo = new Email("juan.perez@example.com");
        DocumentoIdentidad documento = new DocumentoIdentidad("12345678", 
            DocumentoIdentidad.TipoDocumento.DNI);
        Usuario usuario = new Usuario("Juan", "Pérez", emailOriginal, documento);
        
        // Act
        usuario.actualizar("Juan Carlos", "Pérez García", emailNuevo);
        
        // Assert
        assertEquals("Juan Carlos", usuario.getNombre());
        assertEquals("Pérez García", usuario.getApellido());
        assertEquals(emailNuevo, usuario.getEmail());
    }
    
    @Test
    void deberiaDesactivarUsuario() {
        // Arrange
        Email email = new Email("juan@example.com");
        DocumentoIdentidad documento = new DocumentoIdentidad("12345678", 
            DocumentoIdentidad.TipoDocumento.DNI);
        Usuario usuario = new Usuario("Juan", "Pérez", email, documento);
        
        // Act
        usuario.desactivar();
        
        // Assert
        assertFalse(usuario.isActivo());
    }
    
    @Test
    void deberiaActivarUsuario() {
        // Arrange
        Email email = new Email("juan@example.com");
        DocumentoIdentidad documento = new DocumentoIdentidad("12345678", 
            DocumentoIdentidad.TipoDocumento.DNI);
        Usuario usuario = new Usuario("Juan", "Pérez", email, documento);
        usuario.desactivar();
        
        // Act
        usuario.activar();
        
        // Assert
        assertTrue(usuario.isActivo());
    }
    
    @Test
    void deberiaLanzarExcepcionSiNombreEsVacio() {
        // Arrange
        Email email = new Email("juan@example.com");
        DocumentoIdentidad documento = new DocumentoIdentidad("12345678", 
            DocumentoIdentidad.TipoDocumento.DNI);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            new Usuario("", "Pérez", email, documento));
    }
    
    @Test
    void deberiaLanzarExcepcionSiApellidoEsVacio() {
        // Arrange
        Email email = new Email("juan@example.com");
        DocumentoIdentidad documento = new DocumentoIdentidad("12345678", 
            DocumentoIdentidad.TipoDocumento.DNI);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            new Usuario("Juan", "", email, documento));
    }
    
    @Test
    void deberiaLanzarExcepcionSiEmailEsNulo() {
        // Arrange
        DocumentoIdentidad documento = new DocumentoIdentidad("12345678", 
            DocumentoIdentidad.TipoDocumento.DNI);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            new Usuario("Juan", "Pérez", null, documento));
    }
    
    @Test
    void deberiaLanzarExcepcionSiDocumentoEsNulo() {
        // Arrange
        Email email = new Email("juan@example.com");
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            new Usuario("Juan", "Pérez", email, null));
    }
    
    @Test
    void deberiaSerIgualSiTienenMismoId() {
        // Arrange
        Email email = new Email("juan@example.com");
        DocumentoIdentidad documento = new DocumentoIdentidad("12345678", 
            DocumentoIdentidad.TipoDocumento.DNI);
        Usuario usuario1 = new Usuario("Juan", "Pérez", email, documento);
        Usuario usuario2 = new Usuario(usuario1.getId(), "Juan", "Pérez", 
            email, documento, usuario1.getFechaCreacion(), 
            usuario1.getFechaActualizacion(), true);
        
        // Act & Assert
        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }
}
