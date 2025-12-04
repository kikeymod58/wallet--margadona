package com.wallet.domain.valueobjects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitarios para la clase Dinero.
 */
class DineroTest {
    
    @Test
    @DisplayName("Debería crear dinero con cantidad y moneda default")
    void deberiaCrearDineroConCantidadYMonedaDefault() {
        // Arrange & Act
        Dinero dinero = new Dinero(BigDecimal.valueOf(100));
        
        // Assert
        assertEquals(BigDecimal.valueOf(100.00).setScale(2), dinero.getCantidad());
        assertEquals("CLP", dinero.getMoneda());
    }
    
    @Test
    @DisplayName("Debería crear dinero con cantidad y moneda")
    void deberiaCrearDineroConCantidadYMoneda() {
        // Arrange & Act
        Dinero dinero = new Dinero(BigDecimal.valueOf(50), "USD");
        
        // Assert
        assertEquals(BigDecimal.valueOf(50.00).setScale(2), dinero.getCantidad());
        assertEquals("USD", dinero.getMoneda());
    }
    
    @Test
    @DisplayName("Debería crear dinero desde double")
    void deberiaCrearDineroDesdeDouble() {
        // Arrange & Act
        Dinero dinero = Dinero.de(123.45);
        
        // Assert
        assertEquals(BigDecimal.valueOf(123.45).setScale(2), dinero.getCantidad());
    }
    
    @Test
    @DisplayName("Debería crear dinero desde string")
    void deberiaCrearDineroDesdeString() {
        // Arrange & Act
        Dinero dinero = Dinero.de("99.99");
        
        // Assert
        assertEquals(new BigDecimal("99.99").setScale(2), dinero.getCantidad());
    }
    
    @Test
    @DisplayName("Debería redondear a centavos")
    void deberiaRedondearACentavos() {
        // Arrange & Act
        Dinero dinero = Dinero.de(10.123);
        
        // Assert
        assertEquals(BigDecimal.valueOf(10.12).setScale(2), dinero.getCantidad());
    }
    
    @Test
    @DisplayName("Debería sumar dos cantidades")
    void deberiaSumarDosCantidades() {
        // Arrange
        Dinero dinero1 = Dinero.de(100);
        Dinero dinero2 = Dinero.de(50);
        
        // Act
        Dinero resultado = dinero1.sumar(dinero2);
        
        // Assert
        assertEquals(Dinero.de(150), resultado);
    }
    
    @Test
    @DisplayName("Debería restar dos cantidades")
    void deberiaRestarDosCantidades() {
        // Arrange
        Dinero dinero1 = Dinero.de(100);
        Dinero dinero2 = Dinero.de(30);
        
        // Act
        Dinero resultado = dinero1.restar(dinero2);
        
        // Assert
        assertEquals(Dinero.de(70), resultado);
    }
    
    @Test
    @DisplayName("Debería multiplicar por un factor")
    void deberiaMultiplicarPorFactor() {
        // Arrange
        Dinero dinero = Dinero.de(50);
        
        // Act
        Dinero resultado = dinero.multiplicar(2.5);
        
        // Assert
        assertEquals(Dinero.de(125), resultado);
    }
    
    @Test
    @DisplayName("Debería comparar mayor que")
    void deberiaCompararMayorQue() {
        // Arrange
        Dinero mayor = Dinero.de(100);
        Dinero menor = Dinero.de(50);
        
        // Act & Assert
        assertTrue(mayor.esMayorQue(menor));
        assertFalse(menor.esMayorQue(mayor));
    }
    
    @Test
    @DisplayName("Debería comparar menor que")
    void deberiaCompararMenorQue() {
        // Arrange
        Dinero mayor = Dinero.de(100);
        Dinero menor = Dinero.de(50);
        
        // Act & Assert
        assertTrue(menor.esMenorQue(mayor));
        assertFalse(mayor.esMenorQue(menor));
    }
    
    @Test
    @DisplayName("Debería verificar si es positivo")
    void deberiaVerificarSiEsPositivo() {
        // Arrange
        Dinero positivo = Dinero.de(100);
        Dinero negativo = Dinero.de(-50);
        Dinero cero = Dinero.de(0);
        
        // Act & Assert
        assertTrue(positivo.esPositivo());
        assertFalse(negativo.esPositivo());
        assertFalse(cero.esPositivo());
    }
    
    @Test
    @DisplayName("Debería verificar si es negativo")
    void deberiaVerificarSiEsNegativo() {
        // Arrange
        Dinero positivo = Dinero.de(100);
        Dinero negativo = Dinero.de(-50);
        Dinero cero = Dinero.de(0);
        
        // Act & Assert
        assertFalse(positivo.esNegativo());
        assertTrue(negativo.esNegativo());
        assertFalse(cero.esNegativo());
    }
    
    @Test
    @DisplayName("Debería verificar si es cero")
    void deberiaVerificarSiEsCero() {
        // Arrange
        Dinero cero = Dinero.de(0);
        Dinero noCero = Dinero.de(1);
        
        // Act & Assert
        assertTrue(cero.esCero());
        assertFalse(noCero.esCero());
    }
    
    @Test
    @DisplayName("Debería lanzar excepción al operar con diferentes monedas")
    void deberiaLanzarExcepcionAlOperarDiferentesMonedas() {
        // Arrange
        Dinero clp= new Dinero(BigDecimal.valueOf(100), "CLP");
        Dinero usd = new Dinero(BigDecimal.valueOf(50), "USD");
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> clp.sumar(usd));
        assertThrows(IllegalArgumentException.class, () -> clp.restar(usd));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción si la cantidad es nula")
    void deberiaLanzarExcepcionSiCantidadEsNula() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Dinero(null));
    }
    
    @Test
    @DisplayName("Debería lanzar excepción si la moneda es inválida")
    void deberiaLanzarExcepcionSiMonedaEsInvalida() {
        // Arrange, Act & Assert
        assertThrows(IllegalArgumentException.class, () -> 
            new Dinero(BigDecimal.TEN, ""));
        assertThrows(IllegalArgumentException.class, () -> 
            new Dinero(BigDecimal.TEN, "US")); // Menos de 3 caracteres
        assertThrows(IllegalArgumentException.class, () -> 
            new Dinero(BigDecimal.TEN, "USDD")); // Más de 3 caracteres
    }
    
    @Test
    @DisplayName("Debería ser igual si tienen mismo valor")
    void deberiaSerIgualSiTienenMismoValor() {
        // Arrange
        Dinero dinero1 = Dinero.de(100.00);
        Dinero dinero2 = Dinero.de(100.00);
        
        // Act & Assert
        assertEquals(dinero1, dinero2);
        assertEquals(dinero1.hashCode(), dinero2.hashCode());
    }
    
    @Test
    @DisplayName("Constante CERO debería existir")
    void constanteCeroDeberiaExistir() {
        // Arrange & Act
        Dinero cero = Dinero.CERO;
        
        // Assert
        assertTrue(cero.esCero());
        assertEquals("CLP", cero.getMoneda());
    }
}
