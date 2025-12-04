package com.wallet.application.usecases;

import com.wallet.application.dtos.UsuarioDTO;
import com.wallet.application.mappers.UsuarioMapper;
import com.wallet.domain.entities.Usuario;
import com.wallet.domain.exceptions.UsuarioNoEncontradoException;
import com.wallet.domain.repositories.IUsuarioRepository;
import com.wallet.domain.valueobjects.Email;

/**
 * Caso de uso para buscar usuarios.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class BuscarUsuarioUseCase {
    
    private final IUsuarioRepository usuarioRepository;
    
    public BuscarUsuarioUseCase(IUsuarioRepository usuarioRepository) {
        if (usuarioRepository == null) {
            throw new IllegalArgumentException("El repositorio de usuarios no puede ser nulo");
        }
        this.usuarioRepository = usuarioRepository;
    }
    
    /**
     * Busca un usuario por su ID.
     * 
     * @param usuarioId ID del usuario
     * @return DTO del usuario encontrado
     * @throws UsuarioNoEncontradoException si el usuario no existe
     */
    public UsuarioDTO ejecutarPorId(String usuarioId) {
        if (usuarioId == null || usuarioId.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario es requerido");
        }
        
        Usuario usuario = usuarioRepository.buscarPorId(usuarioId)
            .orElseThrow(() -> UsuarioNoEncontradoException.porId(usuarioId));
        
        return UsuarioMapper.toDTO(usuario);
    }
    
    /**
     * Busca un usuario por su email.
     * 
     * @param email email del usuario
     * @return DTO del usuario encontrado
     * @throws UsuarioNoEncontradoException si el usuario no existe
     */
    public UsuarioDTO ejecutarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }
        
        Email emailVO = new Email(email);
        Usuario usuario = usuarioRepository.buscarPorEmail(emailVO)
            .orElseThrow(() -> UsuarioNoEncontradoException.porEmail(email));
        
        return UsuarioMapper.toDTO(usuario);
    }
}
