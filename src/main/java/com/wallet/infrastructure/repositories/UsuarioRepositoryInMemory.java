package com.wallet.infrastructure.repositories;

import com.wallet.domain.entities.Usuario;
import com.wallet.domain.repositories.IUsuarioRepository;
import com.wallet.domain.valueobjects.DocumentoIdentidad;
import com.wallet.domain.valueobjects.Email;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del repositorio de usuarios.
 * Utiliza ConcurrentHashMap para ser thread-safe.
 * 
 * @author Olimpo Team
 * @version 1.0.0
 */
public class UsuarioRepositoryInMemory implements IUsuarioRepository {
    
    private final Map<String, Usuario> usuarios;
    
    public UsuarioRepositoryInMemory() {
        this.usuarios = new ConcurrentHashMap<>();
    }
    
    @Override
    public Usuario guardar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }
    
    @Override
    public Optional<Usuario> buscarPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(usuarios.get(id));
    }
    
    @Override
    public Optional<Usuario> buscarPorEmail(Email email) {
        if (email == null) {
            return Optional.empty();
        }
        return usuarios.values().stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst();
    }
    
    @Override
    public Optional<Usuario> buscarPorDocumento(String numeroDocumento) {
        if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
            return Optional.empty();
        }
        return usuarios.values().stream()
            .filter(u -> u.getDocumentoIdentidad().getNumero().equals(numeroDocumento))
            .findFirst();
    }
    
    @Override
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios.values());
    }
    
    @Override
    public List<Usuario> obtenerActivos() {
        return usuarios.values().stream()
            .filter(Usuario::isActivo)
            .collect(Collectors.toList());
    }
    
    @Override
    public boolean eliminar(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return usuarios.remove(id) != null;
    }
    
    @Override
    public boolean existePorEmail(Email email) {
        if (email == null) {
            return false;
        }
        return usuarios.values().stream()
            .anyMatch(u -> u.getEmail().equals(email));
    }
    
    @Override
    public boolean existePorDocumento(DocumentoIdentidad documento) {
        if (documento == null) {
            return false;
        }
        return usuarios.values().stream()
            .anyMatch(u -> u.getDocumentoIdentidad().equals(documento));
    }
    
    /**
     * Limpia todos los usuarios (útil para testing).
     */
    public void limpiar() {
        usuarios.clear();
    }
    
    /**
     * Retorna el número de usuarios almacenados.
     */
    public int contar() {
        return usuarios.size();
    }
}
