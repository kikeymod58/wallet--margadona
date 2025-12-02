package com.wallet.application.usecases;

import com.wallet.application.dtos.TransaccionDTO;
import com.wallet.application.dtos.requests.DepositarDineroRequest;
import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.entities.Transaccion;
import com.wallet.domain.entities.Usuario;
import com.wallet.domain.exceptions.CuentaNoEncontradaException;
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
 * Tests para DepositarDineroUseCase.
 */
@DisplayName("DepositarDineroUseCase Tests")
class DepositarDineroUseCaseTest {
    
    private ICuentaRepository cuentaRepository;
    private ITransaccionRepository transaccionRepository;
    private DepositarDineroUseCase useCase;
    private Cuenta cuenta;
    
    @BeforeEach
    void setUp() {
        cuentaRepository = mock(ICuentaRepository.class);
        transaccionRepository = mock(ITransaccionRepository.class);
        useCase = new DepositarDineroUseCase(cuentaRepository, transaccionRepository);
        
        Usuario usuario = new Usuario(
            "Juan",
            "Perez",
            new Email("juan@email.com"),
            new DocumentoIdentidad("12345678", TipoDocumento.DNI)
        );
        cuenta = new Cuenta(usuario.getId());
    }
    
    @Test
    @DisplayName("Debe depositar exitosamente")
    void debeDepositarExitosamente() {
        // Arrange
        BigDecimal montoDeposito = new BigDecimal("500.00");
        DepositarDineroRequest request = new DepositarDineroRequest(
            cuenta.getId(), montoDeposito, "Deposito de prueba"
        );
        
        when(cuentaRepository.buscarPorId(cuenta.getId())).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.guardar(any(Cuenta.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transaccionRepository.guardar(any(Transaccion.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        TransaccionDTO resultado = useCase.ejecutar(request);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(montoDeposito, resultado.getMonto());
        assertEquals("DEPOSITO", resultado.getTipo());
        
        verify(cuentaRepository).guardar(any(Cuenta.class));
        verify(transaccionRepository).guardar(any(Transaccion.class));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si cuenta no existe")
    void debeLanzarExcepcionSiCuentaNoExiste() {
        // Arrange
        DepositarDineroRequest request = new DepositarDineroRequest(
            "cuenta-inexistente", new BigDecimal("500.00"), "Deposito"
        );
        when(cuentaRepository.buscarPorId(anyString())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(CuentaNoEncontradaException.class, () -> useCase.ejecutar(request));
        verify(transaccionRepository, never()).guardar(any(Transaccion.class));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si request es nulo")
    void debeLanzarExcepcionSiRequestNulo() {
        assertThrows(IllegalArgumentException.class, () -> useCase.ejecutar(null));
    }
}
