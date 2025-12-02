package com.wallet.application.usecases;

import com.wallet.application.dtos.UsuarioDTO;
import com.wallet.application.dtos.requests.CrearUsuarioRequest;
import com.wallet.domain.entities.Usuario;
import com.wallet.domain.exceptions.OperacionNoValidaException;
import com.wallet.domain.repositories.IUsuarioRepository;
import com.wallet.domain.valueobjects.DocumentoIdentidad;
import com.wallet.domain.valueobjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests para CrearUsuarioUseCase.
 */
@DisplayName("CrearUsuarioUseCase Tests")
class CrearUsuarioUseCaseTest {
    
    private IUsuarioRepository usuarioRepository;
    private CrearUsuarioUseCase useCase;
    
    @BeforeEach
    void setUp() {
        usuarioRepository = mock(IUsuarioRepository.class);
        useCase = new CrearUsuarioUseCase(usuarioRepository);
    }
    
    @Test
    @DisplayName("Debe crear usuario exitosamente")
    void debeCrearUsuarioExitosamente() {
        // Arrange
        CrearUsuarioRequest request = new CrearUsuarioRequest(
            "Juan", "Perez", "juan@email.com", "DNI", "12345678"
        );
        
        when(usuarioRepository.existePorEmail(any(Email.class))).thenReturn(false);
        when(usuarioRepository.existePorDocumento(any(DocumentoIdentidad.class))).thenReturn(false);
        when(usuarioRepository.guardar(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        UsuarioDTO resultado = useCase.ejecutar(request);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Perez", resultado.getApellido());
        assertEquals("juan@email.com", resultado.getEmail());
        assertTrue(resultado.isActivo());
        
        verify(usuarioRepository).existePorEmail(any(Email.class));
        verify(usuarioRepository).existePorDocumento(any(DocumentoIdentidad.class));
        verify(usuarioRepository).guardar(any(Usuario.class));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si email ya existe")
    void debeLanzarExcepcionSiEmailExiste() {
        // Arrange
        CrearUsuarioRequest request = new CrearUsuarioRequest(
            "Juan", "Perez", "juan@email.com", "DNI", "12345678"
        );
        
        when(usuarioRepository.existePorEmail(any(Email.class))).thenReturn(true);
        
        // Act & Assert
        assertThrows(OperacionNoValidaException.class, () -> useCase.ejecutar(request));
        verify(usuarioRepository).existePorEmail(any(Email.class));
        verify(usuarioRepository, never()).guardar(any(Usuario.class));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si documento ya existe")
    void debeLanzarExcepcionSiDocumentoExiste() {
        // Arrange
        CrearUsuarioRequest request = new CrearUsuarioRequest(
            "Juan", "Perez", "juan@email.com", "DNI", "12345678"
        );
        
        when(usuarioRepository.existePorEmail(any(Email.class))).thenReturn(false);
        when(usuarioRepository.existePorDocumento(any(DocumentoIdentidad.class))).thenReturn(true);
        
        // Act & Assert
        assertThrows(OperacionNoValidaException.class, () -> useCase.ejecutar(request));
        verify(usuarioRepository).existePorDocumento(any(DocumentoIdentidad.class));
        verify(usuarioRepository, never()).guardar(any(Usuario.class));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si request es nulo")
    void debeLanzarExcepcionSiRequestNulo() {
        assertThrows(IllegalArgumentException.class, () -> useCase.ejecutar(null));
    }
    
    @Test
    @DisplayName("Debe lanzar excepción si nombre está vacío")
    void debeLanzarExcepcionSiNombreVacio() {
        CrearUsuarioRequest request = new CrearUsuarioRequest(
            "", "Perez", "juan@email.com", "DNI", "12345678"
        );
        assertThrows(IllegalArgumentException.class, () -> useCase.ejecutar(request));
    }
}
