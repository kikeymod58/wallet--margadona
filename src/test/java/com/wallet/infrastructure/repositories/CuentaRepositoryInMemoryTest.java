package com.wallet.infrastructure.repositories;

import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.entities.Usuario;
import com.wallet.domain.valueobjects.DocumentoIdentidad;
import com.wallet.domain.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests para CuentaRepositoryInMemory.
 */
@DisplayName("CuentaRepositoryInMemory Tests")
class CuentaRepositoryInMemoryTest {
    
    private CuentaRepositoryInMemory repository;
    private Cuenta cuenta;
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        repository = new CuentaRepositoryInMemory();
        repository.limpiar();
        
        usuario = new Usuario(
            "Juan",
            "Perez",
            new Email("juan@email.com"),
            new DocumentoIdentidad("12345678", DocumentoIdentidad.TipoDocumento.DNI)
        );
        cuenta = new Cuenta(usuario.getId());
    }
    
    @Test
    @DisplayName("Debe guardar cuenta correctamente")
    void debeGuardarCuenta() {
        // Act
        Cuenta guardada = repository.guardar(cuenta);
        
        // Assert
        assertNotNull(guardada);
        assertEquals(cuenta.getId(), guardada.getId());
        assertEquals(1, repository.contar());
    }
    
    @Test
    @DisplayName("Debe buscar cuenta por ID")
    void debeBuscarPorId() {
        // Arrange
        repository.guardar(cuenta);
        
        // Act
        Optional<Cuenta> encontrada = repository.buscarPorId(cuenta.getId());
        
        // Assert
        assertTrue(encontrada.isPresent());
        assertEquals(cuenta.getId(), encontrada.get().getId());
    }
    
    @Test
    @DisplayName("Debe buscar cuenta por número")
    void debeBuscarPorNumeroCuenta() {
        // Arrange
        repository.guardar(cuenta);
        
        // Act
        Optional<Cuenta> encontrada = repository.buscarPorNumeroCuenta(cuenta.getNumeroCuenta());
        
        // Assert
        assertTrue(encontrada.isPresent());
        assertEquals(cuenta.getNumeroCuenta(), encontrada.get().getNumeroCuenta());
    }
    
    @Test
    @DisplayName("Debe buscar cuentas por usuario")
    void debeBuscarPorUsuarioId() {
        // Arrange
        repository.guardar(cuenta);
        
        // Act
        List<Cuenta> cuentas = repository.buscarPorUsuarioId(usuario.getId());
        
        // Assert
        assertEquals(1, cuentas.size());
        assertEquals(cuenta.getId(), cuentas.get(0).getId());
    }
    
    @Test
    @DisplayName("Debe verificar si existe número de cuenta")
    void debeVerificarExistenciaNumeroCuenta() {
        // Arrange
        repository.guardar(cuenta);
        
        // Act
        boolean existe = repository.existeNumeroCuenta(cuenta.getNumeroCuenta());
        
        // Assert
        assertTrue(existe);
    }
    
    @Test
    @DisplayName("Debe obtener cuentas activas por usuario")
    void debeObtenerActivasPorUsuario() {
        // Arrange
        repository.guardar(cuenta);
        
        // Act
        List<Cuenta> activas = repository.obtenerActivasPorUsuario(usuario.getId());
        
        // Assert
        assertEquals(1, activas.size());
        assertTrue(activas.get(0).isActiva());
    }
    
    @Test
    @DisplayName("Debe obtener todas las cuentas")
    void debeObtenerTodas() {
        // Arrange
        repository.guardar(cuenta);
        Usuario usuario2 = new Usuario(
            "Maria",
            "Garcia",
            new Email("maria@email.com"),
            new DocumentoIdentidad("87654321", DocumentoIdentidad.TipoDocumento.DNI)
        );
        Cuenta cuenta2 = new Cuenta(usuario2.getId());
        repository.guardar(cuenta2);
        
        // Act
        List<Cuenta> todas = repository.obtenerTodas();
        
        // Assert
        assertEquals(2, todas.size());
    }
}
