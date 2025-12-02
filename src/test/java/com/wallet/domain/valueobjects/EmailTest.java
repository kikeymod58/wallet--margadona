package com.wallet.domain.valueobjects;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la clase Email.
 * 
 * Principios aplicados:
 * - Arrange-Act-Assert (AAA)
 * - Un test por comportamiento
 * - Nombres descriptivos
 */
class EmailTest {
    
    @Test
    void deberiaCrearEmailValido() {
        // Arrange & Act
        Email email = new Email("usuario@example.com");
        
        // Assert
        assertEquals("usuario@example.com", email.getValor());
    }
    
    @Test
    void deberiaConvertirEmailAMinusculas() {
        // Arrange & Act
        Email email = new Email("USUARIO@EXAMPLE.COM");
        
        // Assert
        assertEquals("usuario@example.com", email.getValor());
    }
    
    @Test
    void deberiaEliminarEspaciosEnBlanco() {
        // Arrange & Act
        Email email = new Email("  usuario@example.com  ");
        
        // Assert
        assertEquals("usuario@example.com", email.getValor());
    }
    
    @Test
    void deberiaLanzarExcepcionSiEmailEsNulo() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Email(null));
    }
    
    @Test
    void deberiaLanzarExcepcionSiEmailEstaVacio() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Email(""));
        assertThrows(IllegalArgumentException.class, () -> new Email("   "));
    }
    
    @Test
    void deberiaLanzarExcepcionSiEmailNoTieneArroba() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Email("usuarioexample.com"));
    }
    
    @Test
    void deberiaLanzarExcepcionSiEmailNoTieneDominio() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Email("usuario@"));
    }
    
    @Test
    void deberiaLanzarExcepcionSiEmailNoTieneExtension() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Email("usuario@example"));
    }
    
    @Test
    void deberiaLanzarExcepcionSiEmailEsMuyLargo() {
        // Arrange
        String emailLargo = "a".repeat(100) + "@example.com";
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Email(emailLargo));
    }
    
    @Test
    void deberiaSerIgualSiTienenMismoValor() {
        // Arrange
        Email email1 = new Email("usuario@example.com");
        Email email2 = new Email("usuario@example.com");
        
        // Act & Assert
        assertEquals(email1, email2);
        assertEquals(email1.hashCode(), email2.hashCode());
    }
    
    @Test
    void noDeberiaSerIgualSiTienenDiferenteValor() {
        // Arrange
        Email email1 = new Email("usuario1@example.com");
        Email email2 = new Email("usuario2@example.com");
        
        // Act & Assert
        assertNotEquals(email1, email2);
    }
    
    @Test
    void toStringDeberiaRetornarElValor() {
        // Arrange
        Email email = new Email("usuario@example.com");
        
        // Act & Assert
        assertEquals("usuario@example.com", email.toString());
    }
}
