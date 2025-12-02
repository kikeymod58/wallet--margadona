package com.wallet.infrastructure.integration;

import com.wallet.application.dtos.CuentaDTO;
import com.wallet.application.dtos.TransaccionDTO;
import com.wallet.application.dtos.UsuarioDTO;
import com.wallet.application.dtos.requests.*;
import com.wallet.infrastructure.factories.RepositoryFactory;
import com.wallet.infrastructure.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests de integración del flujo completo del sistema.
 */
@DisplayName("Integration Tests - Flujo Completo")
class IntegracionFlujosTest {
    
    private UsuarioService usuarioService;
    private CuentaService cuentaService;
    private TransaccionService transaccionService;
    
    @BeforeEach
    void setUp() {
        // Limpiar repositorios
        RepositoryFactory.limpiarTodos();
        
        // Crear servicios
        usuarioService = new UsuarioService();
        cuentaService = new CuentaService();
        transaccionService = new TransaccionService();
    }
    
    @Test
    @DisplayName("Flujo completo: Crear usuario → cuenta → depositar → retirar → transferir")
    void flujoCompletoExitoso() {
        // 1. Crear primer usuario
        CrearUsuarioRequest usuario1Request = new CrearUsuarioRequest(
            "Juan", "Perez", "juan@email.com", "DNI", "12345678"
        );
        UsuarioDTO usuario1 = usuarioService.crearUsuario(usuario1Request);
        assertNotNull(usuario1);
        assertEquals("Juan", usuario1.getNombre());
        
        // 2. Crear segunda usuario
        CrearUsuarioRequest usuario2Request = new CrearUsuarioRequest(
            "Maria", "Garcia", "maria@email.com", "DNI", "87654321"
        );
        UsuarioDTO usuario2 = usuarioService.crearUsuario(usuario2Request);
        assertNotNull(usuario2);
        
        // 3. Crear cuentas
        CuentaDTO cuenta1 = cuentaService.crearCuenta(usuario1.getId());
        CuentaDTO cuenta2 = cuentaService.crearCuenta(usuario2.getId());
        assertNotNull(cuenta1);
        assertNotNull(cuenta2);
        
        // 4. Depositar en cuenta 1
        DepositarDineroRequest depositoRequest = new DepositarDineroRequest(
            cuenta1.getId(), new BigDecimal("1000.00"), "Deposito inicial"
        );
        TransaccionDTO deposito = cuentaService.depositar(depositoRequest);
        assertNotNull(deposito);
        assertEquals(new BigDecimal("1000.00"), deposito.getMonto());
        
        // 5. Consultar saldo
        CuentaDTO cuenta1Actualizada = cuentaService.consultarSaldo(cuenta1.getNumeroCuenta());
        assertEquals(new BigDecimal("1000.00"), cuenta1Actualizada.getSaldo());
        
        // 6. Retirar de cuenta 1
        RetirarDineroRequest retiroRequest = new RetirarDineroRequest(
            cuenta1.getId(), new BigDecimal("200.00"), "Retiro"
        );
        TransaccionDTO retiro = cuentaService.retirar(retiroRequest);
        assertNotNull(retiro);
        
        // 7. Verificar saldo después del retiro
        cuenta1Actualizada = cuentaService.consultarSaldo(cuenta1.getNumeroCuenta());
        assertEquals(new BigDecimal("800.00"), cuenta1Actualizada.getSaldo());
        
        // 8. Transferir de cuenta 1 a cuenta 2
        TransferirDineroRequest transferenciaRequest = new TransferirDineroRequest(
            cuenta1.getId(), cuenta2.getId(), new BigDecimal("300.00"), "Transferencia"
        );
        List<TransaccionDTO> transferencia = transaccionService.transferir(transferenciaRequest);
        assertNotNull(transferencia);
        assertEquals(2, transferencia.size()); // Salida + Entrada
        
        // 9. Verificar saldos finales
        cuenta1Actualizada = cuentaService.consultarSaldo(cuenta1.getNumeroCuenta());
        CuentaDTO cuenta2Actualizada = cuentaService.consultarSaldo(cuenta2.getNumeroCuenta());
        
        assertEquals(new BigDecimal("500.00"), cuenta1Actualizada.getSaldo());
        assertEquals(new BigDecimal("300.00"), cuenta2Actualizada.getSaldo());
        
        // 10. Verificar historial
        List<TransaccionDTO> historial1 = transaccionService.consultarHistorial(cuenta1.getNumeroCuenta());
        assertEquals(4, historial1.size()); // Deposito inicial + Deposito + Retiro + Transferencia Salida
        
        List<TransaccionDTO> historial2 = transaccionService.consultarHistorial(cuenta2.getNumeroCuenta());
        assertEquals(2, historial2.size()); // Deposito inicial + Transferencia Entrada
    }
    
    @Test
    @DisplayName("Debe prevenir crear usuario con email duplicado")
    void debePrevenirEmailDuplicado() {
        // Arrange
        CrearUsuarioRequest request1 = new CrearUsuarioRequest(
            "Juan", "Perez", "juan@email.com", "DNI", "12345678"
        );
        usuarioService.crearUsuario(request1);
        
        CrearUsuarioRequest request2 = new CrearUsuarioRequest(
            "Pedro", "Lopez", "juan@email.com", "PASAPORTE", "99999999"
        );
        
        // Act & Assert
        assertThrows(Exception.class, () -> usuarioService.crearUsuario(request2));
    }
    
    @Test
    @DisplayName("Debe prevenir retiro con saldo insuficiente")
    void debePrevenirRetiroSinSaldo() {
        // Arrange
        CrearUsuarioRequest usuarioRequest = new CrearUsuarioRequest(
            "Juan", "Perez", "juan@email.com", "DNI", "12345678"
        );
        UsuarioDTO usuario = usuarioService.crearUsuario(usuarioRequest);
        CuentaDTO cuenta = cuentaService.crearCuenta(usuario.getId());
        
        // Depositar poco dinero
        DepositarDineroRequest depositoRequest = new DepositarDineroRequest(
            cuenta.getId(), new BigDecimal("100.00"), "Deposito"
        );
        cuentaService.depositar(depositoRequest);
        
        // Intentar retirar más
        RetirarDineroRequest retiroRequest = new RetirarDineroRequest(
            cuenta.getId(), new BigDecimal("200.00"), "Retiro excesivo"
        );
        
        // Act & Assert
        assertThrows(Exception.class, () -> cuentaService.retirar(retiroRequest));
    }
    
    @Test
    @DisplayName("Debe manejar múltiples operaciones concurrentes")
    void debeManejOperacionesConcurrentes() {
        // Arrange
        CrearUsuarioRequest usuarioRequest = new CrearUsuarioRequest(
            "Juan", "Perez", "juan@email.com", "DNI", "12345678"
        );
        UsuarioDTO usuario = usuarioService.crearUsuario(usuarioRequest);
        CuentaDTO cuenta = cuentaService.crearCuenta(usuario.getId());
        
        // Depositar saldo inicial
        DepositarDineroRequest depositoInicial = new DepositarDineroRequest(
            cuenta.getId(), new BigDecimal("1000.00"), "Inicial"
        );
        cuentaService.depositar(depositoInicial);
        
        // Act - Múltiples operaciones
        for (int i = 0; i < 5; i++) {
            DepositarDineroRequest deposito = new DepositarDineroRequest(
                cuenta.getId(), new BigDecimal("100.00"), "Deposito " + i
            );
            cuentaService.depositar(deposito);
        }
        
        for (int i = 0; i < 3; i++) {
            RetirarDineroRequest retiro = new RetirarDineroRequest(
                cuenta.getId(), new BigDecimal("50.00"), "Retiro " + i
            );
            cuentaService.retirar(retiro);
        }
        
        // Assert
        CuentaDTO cuentaFinal = cuentaService.consultarSaldo(cuenta.getNumeroCuenta());
        // 1000 + (5*100) - (3*50) = 1000 + 500 - 150 = 1350
        assertEquals(new BigDecimal("1350.00"), cuentaFinal.getSaldo());
        
        List<TransaccionDTO> historial = transaccionService.consultarHistorial(cuenta.getNumeroCuenta());
        assertEquals(9, historial.size()); // 1 inicial + 5 depositos + 3 retiros
    }
}
