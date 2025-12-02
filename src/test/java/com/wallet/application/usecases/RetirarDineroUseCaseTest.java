package com.wallet.application.usecases;

import com.wallet.application.dtos.TransaccionDTO;
import com.wallet.application.dtos.requests.RetirarDineroRequest;
import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.entities.Transaccion;
import com.wallet.domain.entities.Usuario;
import com.wallet.domain.exceptions.CuentaNoEncontradaException;
import com.wallet.domain.exceptions.OperacionNoValidaException;
import com.wallet.domain.repositories.ICuentaRepository;
import com.wallet.domain.repositories.ITransaccionRepository;
import com.wallet.domain.valueobjects.Dinero;
import com.wallet.domain.valueobjects.DocumentoIdentidad;
import com.wallet.domain.valueobjects.DocumentoIdentidad.TipoDocumento;
import com.wallet.domain.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests para RetirarDineroUseCase.
 */
@DisplayName("RetirarDineroUseCase Tests")
class RetirarDineroUseCaseTest {
    
    private ICuentaRepository cuentaRepository;
    private ITransaccionRepository transaccionRepository;
    private RetirarDineroUseCase useCase;
    private Cuenta cuenta;
    
    @BeforeEach
    void setUp() {
        cuentaRepository = mock(ICuentaRepository.class);
        transaccionRepository = mock(ITransaccionRepository.class);
        useCase = new RetirarDineroUseCase(cuentaRepository, transaccionRepository);
        
        Usuario usuario = new Usuario(
            "Juan",
            "Perez",
            new Email("juan@email.com"),
            new DocumentoIdentidad("12345678", TipoDocumento.DNI)
        );
        cuenta = new Cuenta(usuario.getId());
        // Depositar para tener saldo
        cuenta.depositar(Dinero.de(new BigDecimal("1000.00")));
    }
    
    @Test
    @DisplayName("Debe retirar exitosamente")
    void debeRetirarExitosamente() {
        // Arrange
        BigDecimal montoRetiro = new BigDecimal("300.00");
        RetirarDineroRequest request = new RetirarDineroRequest(
            cuenta.getId(), montoRetiro, "Retiro de prueba"
        );
        
        when(cuentaRepository.buscarPorId(cuenta.getId())).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.guardar(any(Cuenta.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transaccionRepository.guardar(any(Transaccion.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        TransaccionDTO resultado = useCase.ejecutar(request);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(montoRetiro, resultado.getMonto());
        assertEquals("RETIRO", resultado.getTipo());
        
        verify(cuentaRepository).guardar(any(Cuenta.class));
        verify(transaccionRepository).guardar(any(Transaccion.class));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si saldo insuficiente")
    void debeLanzarExcepcionSiSaldoInsuficiente() {
        // Arrange
        RetirarDineroRequest request = new RetirarDineroRequest(
            cuenta.getId(), new BigDecimal("2000.00"), "Retiro mayor al saldo"
        );
        when(cuentaRepository.buscarPorId(cuenta.getId())).thenReturn(Optional.of(cuenta));
        
        // Act & Assert
        assertThrows(IllegalStateException.class, () -> useCase.ejecutar(request));
        verify(transaccionRepository, never()).guardar(any(Transaccion.class));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si cuenta no existe")
    void debeLanzarExcepcionSiCuentaNoExiste() {
        // Arrange
        RetirarDineroRequest request = new RetirarDineroRequest(
            "cuenta-inexistente", new BigDecimal("100.00"), "Retiro"
        );
        when(cuentaRepository.buscarPorId(anyString())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(CuentaNoEncontradaException.class, () -> useCase.ejecutar(request));
    }
}
