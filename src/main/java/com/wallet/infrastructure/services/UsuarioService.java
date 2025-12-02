package com.wallet.infrastructure.services;

import com.wallet.application.dtos.UsuarioDTO;
import com.wallet.application.dtos.requests.CrearUsuarioRequest;
import com.wallet.application.mappers.UsuarioMapper;
import com.wallet.application.usecases.CrearUsuarioUseCase;
import com.wallet.application.usecases.BuscarUsuarioUseCase;
import com.wallet.domain.entities.Usuario;
import com.wallet.infrastructure.factories.RepositoryFactory;
import com.wallet.infrastructure.logging.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio de Usuarios (Facade Pattern).
 * 
 * Orquesta los casos de uso relacionados con usuarios.
 * Proporciona una API de alto nivel para la capa de presentación.
 * Gestiona logging y conversión de DTOs.
 */
public class UsuarioService {
    
    private final CrearUsuarioUseCase crearUsuarioUseCase;
    private final BuscarUsuarioUseCase buscarUsuarioUseCase;
    
    public UsuarioService() {
        this.crearUsuarioUseCase = new CrearUsuarioUseCase(
            RepositoryFactory.getUsuarioRepository()
        );
        this.buscarUsuarioUseCase = new BuscarUsuarioUseCase(
            RepositoryFactory.getUsuarioRepository()
        );
    }
    
    /**
     * Crea un nuevo usuario.
     */
    public UsuarioDTO crearUsuario(CrearUsuarioRequest request) {
        Logger.info("Creando usuario: " + request.getEmail());
        
        try {
            UsuarioDTO usuario = crearUsuarioUseCase.ejecutar(request);
            Logger.info("Usuario creado exitosamente: " + usuario.getId());
            return usuario;
        } catch (Exception e) {
            Logger.error("Error al crear usuario", e);
            throw e;
        }
    }
    
    /**
     * Busca un usuario por ID.
     */
    public Optional<UsuarioDTO> buscarPorId(String usuarioId) {
        Logger.debug("Buscando usuario por ID: " + usuarioId);
        
        UsuarioDTO usuario = buscarUsuarioUseCase.ejecutarPorId(usuarioId);
        return Optional.ofNullable(usuario);
    }
    
    /**
     * Busca un usuario por email.
     */
    public Optional<UsuarioDTO> buscarPorEmail(String email) {
        Logger.debug("Buscando usuario por email: " + email);
        
        UsuarioDTO usuario = buscarUsuarioUseCase.ejecutarPorEmail(email);
        return Optional.ofNullable(usuario);
    }
    
    /**
     * Obtiene todos los usuarios activos.
     */
    public List<UsuarioDTO> obtenerUsuariosActivos() {
        Logger.debug("Obteniendo usuarios activos");
        
        return RepositoryFactory.getUsuarioRepository()
            .obtenerActivos()
            .stream()
            .map(UsuarioMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene todos los usuarios.
     */
    public List<UsuarioDTO> obtenerTodos() {
        Logger.debug("Obteniendo todos los usuarios");
        
        return RepositoryFactory.getUsuarioRepository()
            .obtenerTodos()
            .stream()
            .map(UsuarioMapper::toDTO)
            .collect(Collectors.toList());
    }
}
