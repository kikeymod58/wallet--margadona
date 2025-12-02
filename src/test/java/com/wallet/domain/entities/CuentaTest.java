package com.wallet.domain.entities;

import com.wallet.domain.valueobjects.Dinero;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la entidad Cuenta.
 */
class CuentaTest {
    
    @Test
    void deberiaCrearCuentaConSaldoCero() {
        // Arrange & Act
        Cuenta cuenta = new Cuenta("usuario-123");
        
        // Assert
        assertNotNull(cuenta.getId());
        assertNotNull(cuenta.getNumeroCuenta());
        assertEquals("usuario-123", cuenta.getUsuarioId());
        assertTrue(cuenta.getSaldo().esCero());
        assertTrue(cuenta.isActiva());
    }
    
    @Test
    void deberiaDepositarDinero() {
        // Arrange
        Cuenta cuenta = new Cuenta("usuario-123");
        Dinero monto = Dinero.de(100);
        
        // Act
        cuenta.depositar(monto);
        
        // Assert
        assertEquals(Dinero.de(100), cuenta.getSaldo());
    }
    
    @Test
    void deberiaRetirarDinero() {
        // Arrange
        Cuenta cuenta = new Cuenta("usuario-123");
        cuenta.depositar(Dinero.de(100));
        
        // Act
        cuenta.retirar(Dinero.de(30));
        
        // Assert
        assertEquals(Dinero.de(70), cuenta.getSaldo());
    }
    
    @Test
    void deberiaVerificarSaldoSuficiente() {
        // Arrange
        Cuenta cuenta = new Cuenta("usuario-123");
        cuenta.depositar(Dinero.de(100));
        
        // Act & Assert
        assertTrue(cuenta.tieneSaldoSuficiente(Dinero.de(50)));
        assertTrue(cuenta.tieneSaldoSuficiente(Dinero.de(100)));
        assertFalse(cuenta.tieneSaldoSuficiente(Dinero.de(101)));
    }
    
    @Test
    void deberiaLanzarExcepcionAlRetirarConSaldoInsuficiente() {
        // Arrange
        Cuenta cuenta = new Cuenta("usuario-123");
        cuenta.depositar(Dinero.de(50));
        
        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
            cuenta.retirar(Dinero.de(100)));
    }
    
    @Test
    void deberiaLanzarExcepcionAlDepositarMontoNegativo() {
        // Arrange
        Cuenta cuenta = new Cuenta("usuario-123");
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            cuenta.depositar(Dinero.de(-10)));
    }
    
    @Test
    void deberiaLanzarExcepcionAlRetirarMontoCero() {
        // Arrange
        Cuenta cuenta = new Cuenta("usuario-123");
        cuenta.depositar(Dinero.de(100));
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            cuenta.retirar(Dinero.de(0)));
    }
    
    @Test
    void deberiaDesactivarCuenta() {
        // Arrange
        Cuenta cuenta = new Cuenta("usuario-123");
        
        // Act
        cuenta.desactivar();
        
        // Assert
        assertFalse(cuenta.isActiva());
    }
    
    @Test
    void noDeberiaPermitirDepositoEnCuentaInactiva() {
        // Arrange
        Cuenta cuenta = new Cuenta("usuario-123");
        cuenta.desactivar();
        
        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
            cuenta.depositar(Dinero.de(100)));
    }
    
    @Test
    void noDeberiaPermitirRetiroEnCuentaInactiva() {
        // Arrange
        Cuenta cuenta = new Cuenta("usuario-123");
        cuenta.depositar(Dinero.de(100));
        cuenta.desactivar();
        
        // Act & Assert
        assertThrows(IllegalStateException.class, () ->
            cuenta.retirar(Dinero.de(50)));
    }
    
    @Test
    void deberiaActivarCuenta() {
        // Arrange
        Cuenta cuenta = new Cuenta("usuario-123");
        cuenta.desactivar();
        
        // Act
        cuenta.activar();
        
        // Assert
        assertTrue(cuenta.isActiva());
    }
    
    @Test
    void deberiaLanzarExcepcionSiUsuarioIdEsNulo() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            new Cuenta(null));
    }
}
