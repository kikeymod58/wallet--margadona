package com.wallet.application.usecases;

import com.wallet.application.dtos.CuentaDTO;
import com.wallet.domain.entities.Cuenta;
import com.wallet.domain.entities.Usuario;
import com.wallet.domain.exceptions.OperacionNoValidaException;
import com.wallet.domain.exceptions.UsuarioNoEncontradoException;
import com.wallet.domain.repositories.ICuentaRepository;
import com.wallet.domain.repositories.IUsuarioRepository;
import com.wallet.domain.valueobjects.DocumentoIdentidad;
import com.wallet.domain.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests para CrearCuentaUseCase.
 */
@DisplayName("CrearCuentaUseCase Tests")
class CrearCuentaUseCaseTest {
    
    private ICuentaRepository cuentaRepository;
    private IUsuarioRepository usuarioRepository;
    private CrearCuentaUseCase useCase;
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        cuentaRepository = mock(ICuentaRepository.class);
        usuarioRepository = mock(IUsuarioRepository.class);
        useCase = new CrearCuentaUseCase(cuentaRepository, usuarioRepository);
        
        usuario = new Usuario(
            "Juan",
            "Perez",
            new Email("juan@email.com"),
            new DocumentoIdentidad("12345678", DocumentoIdentidad.TipoDocumento.DNI)
        );
    }
    
    @Test
    @DisplayName("Debe crear cuenta exitosamente")
    void debeCrearCuentaExitosamente() {
        // Arrange
        when(usuarioRepository.buscarPorId(anyString())).thenReturn(Optional.of(usuario));
        when(cuentaRepository.buscarPorUsuarioId(anyString())).thenReturn(Collections.emptyList());
        when(cuentaRepository.guardar(any(Cuenta.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        CuentaDTO resultado = useCase.ejecutar(usuario.getId());
        
        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getNumeroCuenta());
        assertEquals(new BigDecimal("0.00"), resultado.getSaldo());
        assertEquals("CLP", resultado.getMoneda());
        assertTrue(resultado.isActiva());
        
        verify(usuarioRepository).buscarPorId(usuario.getId());
        verify(cuentaRepository).guardar(any(Cuenta.class));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si usuario no existe")
    void debeLanzarExcepcionSiUsuarioNoExiste() {
        // Arrange
        when(usuarioRepository.buscarPorId(anyString())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(UsuarioNoEncontradoException.class, () -> useCase.ejecutar("usuario-inexistente"));
        verify(cuentaRepository, never()).guardar(any(Cuenta.class));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si usuario ya tiene cuenta")
    void debeLanzarExcepcionSiUsuarioYaTieneCuenta() {
        // Arrange
        Cuenta cuentaExistente = new Cuenta(usuario.getId());
        when(usuarioRepository.buscarPorId(anyString())).thenReturn(Optional.of(usuario));
        when(cuentaRepository.buscarPorUsuarioId(anyString())).thenReturn(Collections.singletonList(cuentaExistente));
        
        // Act & Assert
        assertThrows(OperacionNoValidaException.class, () -> useCase.ejecutar(usuario.getId()));
        verify(cuentaRepository, never()).guardar(any(Cuenta.class));
    }
}
