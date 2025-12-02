package com.wallet.infrastructure.repositories;

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
 * Tests para UsuarioRepositoryInMemory.
 */
@DisplayName("UsuarioRepositoryInMemory Tests")
class UsuarioRepositoryInMemoryTest {
    
    private UsuarioRepositoryInMemory repository;
    private Usuario usuario;
    
    @BeforeEach
    void setUp() {
        repository = new UsuarioRepositoryInMemory();
        repository.limpiar();
        
        usuario = new Usuario(
            "Juan",
            "Perez",
            new Email("juan@email.com"),
            new DocumentoIdentidad("12345678", DocumentoIdentidad.TipoDocumento.DNI)
        );
    }
    
    @Test
    @DisplayName("Debe guardar usuario correctamente")
    void debeGuardarUsuario() {
        // Act
        Usuario guardado = repository.guardar(usuario);
        
        // Assert
        assertNotNull(guardado);
        assertEquals(usuario.getId(), guardado.getId());
        assertEquals(1, repository.contar());
    }
    
    @Test
    @DisplayName("Debe buscar usuario por ID")
    void debeBuscarPorId() {
        // Arrange
        repository.guardar(usuario);
        
        // Act
        Optional<Usuario> encontrado = repository.buscarPorId(usuario.getId());
        
        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(usuario.getId(), encontrado.get().getId());
    }
    
    @Test
    @DisplayName("Debe buscar usuario por email")
    void debeBuscarPorEmail() {
        // Arrange
        repository.guardar(usuario);
        
        // Act
        Optional<Usuario> encontrado = repository.buscarPorEmail(usuario.getEmail());
        
        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(usuario.getEmail().getValor(), encontrado.get().getEmail().getValor());
    }
    
    @Test
    @DisplayName("Debe verificar si existe email")
    void debeVerificarExistenciaEmail() {
        // Arrange
        repository.guardar(usuario);
        
        // Act
        boolean existe = repository.existePorEmail(usuario.getEmail());
        
        // Assert
        assertTrue(existe);
    }
    
    @Test
    @DisplayName("Debe verificar si existe documento")
    void debeVerificarExistenciaDocumento() {
        // Arrange
        repository.guardar(usuario);
        
        // Act
        boolean existe = repository.existePorDocumento(usuario.getDocumentoIdentidad());
        
        // Assert
        assertTrue(existe);
    }
    
    @Test
    @DisplayName("Debe listar usuarios activos")
    void debeListarUsuariosActivos() {
        // Arrange
        repository.guardar(usuario);
        Usuario usuario2 = new Usuario(
            "Maria",
            "Garcia",
            new Email("maria@email.com"),
            new DocumentoIdentidad("87654321", DocumentoIdentidad.TipoDocumento.DNI)
        );
        repository.guardar(usuario2);
        
        // Act
        List<Usuario> activos = repository.obtenerActivos();
        
        // Assert
        assertEquals(2, activos.size());
    }
    
    @Test
    @DisplayName("Debe obtener todos los usuarios")
    void debeObtenerTodos() {
        // Arrange
        repository.guardar(usuario);
        Usuario usuario2 = new Usuario(
            "Maria",
            "Garcia",
            new Email("maria@email.com"),
            new DocumentoIdentidad("87654321", DocumentoIdentidad.TipoDocumento.DNI)
        );
        repository.guardar(usuario2);
        
        // Act
        List<Usuario> todos = repository.obtenerTodos();
        
        // Assert
        assertEquals(2, todos.size());
    }
    
    @Test
    @DisplayName("Debe limpiar repositorio")
    void debeLimpiarRepositorio() {
        // Arrange
        repository.guardar(usuario);
        assertEquals(1, repository.contar());
        
        // Act
        repository.limpiar();
        
        // Assert
        assertEquals(0, repository.contar());
    }
}
